/*
 * Copyright 2023-present ByteChef Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.hermes.component.definition;

import com.bytechef.atlas.coordinator.event.TaskProgressedApplicationEvent;
import com.bytechef.data.storage.service.DataStorageService;
import com.bytechef.file.storage.service.FileStorageService;
import com.bytechef.hermes.component.exception.ComponentExecutionException;
import com.bytechef.hermes.component.registry.domain.ComponentConnection;
import com.bytechef.hermes.execution.constants.FileEntryConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author Ivica Cardic
 */
public class ActionContextImpl extends ContextImpl implements ActionContext {

    private final Data data;
    private final Event event;
    private final File file;

    @SuppressFBWarnings("EI")
    public ActionContextImpl(
        String componentName, int componentVersion, String actionName, Long instanceId, Integer type,
        String workflowId, Long jobId, ComponentConnection connection, DataStorageService dataStorageService,
        ApplicationEventPublisher eventPublisher, FileStorageService fileStorageService,
        HttpClientExecutor httpClientExecutor) {

        super(componentName, actionName, connection, httpClientExecutor);

        this.data = type == null ? new NoOpDataImpl() : new DataImpl(
            componentName, componentVersion, actionName, instanceId, type, workflowId, jobId,
            dataStorageService);
        this.event = jobId == null ? progress -> {} : new EventImpl(eventPublisher, jobId);
        this.file = new FileImpl(fileStorageService);
    }

    @Override
    public <R> R data(ContextFunction<Data, R> dataFunction) {
        try {
            return dataFunction.apply(data);
        } catch (Exception e) {
            throw new ComponentExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public void event(Consumer<Event> eventConsumer) {
        eventConsumer.accept(event);
    }

    @Override
    public <R> R file(ContextFunction<File, R> fileFunction) {
        try {
            return fileFunction.apply(file);
        } catch (Exception e) {
            throw new ComponentExecutionException(e.getMessage(), e);
        }
    }

    private record DataImpl(
        String componentName, Integer componentVersion, String actionName, Long instanceId, int type, String workflowId,
        Long jobId, DataStorageService dataStorageService) implements Data {

        @Override
        public <T> Optional<T> fetchValue(Scope scope, String key) {
            return dataStorageService.fetch(
                componentName, actionName, scope.getId(), getScopeId(scope), key, type);
        }

        @Override
        public <T> T getValue(Scope scope, String key) {
            return dataStorageService.get(
                componentName, actionName, scope.getId(), getScopeId(scope), key, type);
        }

        @Override
        public void setValue(Scope scope, String key, Object value) {
            dataStorageService.put(
                componentName, actionName, scope.getId(), getScopeId(scope), key, type, value);
        }

        private String getScopeId(Scope scope) {
            return switch (scope) {
                case ACCOUNT -> null;
                case CURRENT_EXECUTION -> jobId + "";
                case INSTANCE -> instanceId + "";
                case WORKFLOW -> workflowId;
            };
        }
    }

    private record EventImpl(ApplicationEventPublisher eventPublisher, long taskExecutionId) implements Event {

        @Override
        public void publishActionProgressEvent(int progress) {
            eventPublisher.publishEvent(new TaskProgressedApplicationEvent(taskExecutionId, progress));
        }
    }

    private record FileImpl(FileStorageService fileStorageService) implements File {

        @Override
        public InputStream getStream(FileEntry fileEntry) {
            return fileStorageService.getFileStream(
                FileEntryConstants.FILES_DIR, ((ContextFileEntryImpl) fileEntry).getFileEntry());
        }

        @Override
        public String readToString(FileEntry fileEntry) {
            return fileStorageService.readFileToString(
                FileEntryConstants.FILES_DIR, ((ContextFileEntryImpl) fileEntry).getFileEntry());
        }

        @Override
        public FileEntry storeContent(String fileName, String data) {
            return new ContextFileEntryImpl(
                fileStorageService.storeFileContent(FileEntryConstants.FILES_DIR, fileName, data));
        }

        @Override
        public FileEntry storeContent(String fileName, InputStream inputStream) {
            try {
                return new ContextFileEntryImpl(
                    fileStorageService.storeFileContent(FileEntryConstants.FILES_DIR, fileName, inputStream));
            } catch (Exception exception) {
                throw new ComponentExecutionException("Unable to store file " + fileName, exception);
            }
        }
    }

    private record NoOpDataImpl() implements Data {

        @Override
        public <T> Optional<T> fetchValue(Scope scope, String key) {
            return Optional.empty();
        }

        @Override
        public <T> T getValue(Scope scope, String key) {
            return null;
        }

        @Override
        public void setValue(Scope scope, String key, Object data) {

        }
    }
}
