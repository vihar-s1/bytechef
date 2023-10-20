/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Modifications copyright (C) 2021 <your company/name>
 */

package com.integri.atlas.engine.coordinator.task.dispatcher;

import com.integri.atlas.engine.coordinator.message.broker.Exchanges;
import com.integri.atlas.engine.core.message.broker.MessageBroker;
import com.integri.atlas.engine.core.task.ControlTask;
import com.integri.atlas.engine.core.task.Task;
import com.integri.atlas.engine.core.task.dispatcher.TaskDispatcher;
import com.integri.atlas.engine.core.task.dispatcher.TaskDispatcherResolver;
import java.util.Objects;

/**
 *
 * @author Arik Cohen
 * @since Apr 11, 2017
 */
public class ControlTaskDispatcher implements TaskDispatcher, TaskDispatcherResolver {

    private final MessageBroker messageBroker;

    public ControlTaskDispatcher(MessageBroker aMessageBroker) {
        messageBroker = Objects.requireNonNull(aMessageBroker);
    }

    @Override
    public void dispatch(Task aTask) {
        messageBroker.send(Exchanges.CONTROL + "/" + Exchanges.CONTROL, aTask);
    }

    @Override
    public TaskDispatcher resolve(Task aTask) {
        if (aTask instanceof ControlTask) {
            return this;
        }
        return null;
    }
}
