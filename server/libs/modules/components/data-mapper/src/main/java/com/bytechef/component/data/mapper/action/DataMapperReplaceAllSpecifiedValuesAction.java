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

package com.bytechef.component.data.mapper.action;

import static com.bytechef.component.data.mapper.constant.DataMapperConstants.FROM;
import static com.bytechef.component.data.mapper.constant.DataMapperConstants.INPUT;
import static com.bytechef.component.data.mapper.constant.DataMapperConstants.INPUT_TYPE;
import static com.bytechef.component.data.mapper.constant.DataMapperConstants.MAPPINGS;
import static com.bytechef.component.data.mapper.constant.DataMapperConstants.TO;
import static com.bytechef.component.data.mapper.constant.DataMapperConstants.TYPE;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.FROM_DESCRIPTION;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.LABEL_FROM;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.LABEL_TO;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.MAPPINGS_DESCRIPTION;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.MAPPINGS_LABEL;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.TO_DESCRIPTION;
import static com.bytechef.component.data.mapper.util.DataMapperUtils.getDisplayCondition;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.array;
import static com.bytechef.component.definition.ComponentDsl.bool;
import static com.bytechef.component.definition.ComponentDsl.date;
import static com.bytechef.component.definition.ComponentDsl.dateTime;
import static com.bytechef.component.definition.ComponentDsl.integer;
import static com.bytechef.component.definition.ComponentDsl.number;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.ComponentDsl.time;

import com.bytechef.component.data.mapper.model.ObjectMapping;
import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Parameters;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivica Cardic
 * @author Marko Kriskovic
 */
public class DataMapperReplaceAllSpecifiedValuesAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("replaceAllSpecifiedValues")
        .title("Replace All Specified Values")
        .description("Goes through all object parameters and replaces all specified input parameter values.")
        .properties(
            integer(INPUT_TYPE)
                .label("Input Type")
                .description("The input type.")
                .options(
                    option("Object", 1),
                    option("Array", 2))
                .required(true),
            object(INPUT)
                .label("Input")
                .description("An object containing one or more properties.")
                .displayCondition("inputType == 1")
                .required(true),
            array(INPUT)
                .label("Input")
                .description("An array containing one or more objects.")
                .displayCondition("inputType == 2")
                .items(object())
                .required(true),
            integer(TYPE)
                .label("Value Type")
                .description("The value type of 'from' and 'to' property values.")
                .required(true)
                .options(
                    option("Array", 1),
                    option("Boolean", 2),
                    option("Date", 3),
                    option("Date Time", 4),
                    option("Integer", 5),
                    option("Number", 7),
                    option("Object", 8),
                    option("String", 9),
                    option("Time", 10))
                .required(true),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("1"))
                .items(
                    object().properties(
                        array(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        array(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("2"))
                .items(
                    object().properties(
                        bool(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        bool(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("3"))
                .items(
                    object().properties(
                        date(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),

                        date(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("4"))
                .items(
                    object().properties(
                        dateTime(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        dateTime(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("5"))
                .items(
                    object().properties(
                        integer(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        integer(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("7"))
                .items(
                    object().properties(
                        number(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        number(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("8"))
                .items(
                    object().properties(
                        object(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        object(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("9"))
                .items(
                    object().properties(
                        string(FROM)
                            .label(LABEL_FROM)
                            .description("Part of the string value you want to change, defined by regex.")
                            .required(true),
                        string(TO)
                            .label(LABEL_TO)
                            .description("The value you want to change the defined part to, defined by regex.")
                            .required(true))),
            array(MAPPINGS)
                .label(MAPPINGS_LABEL)
                .description(MAPPINGS_DESCRIPTION)
                .displayCondition(getDisplayCondition("10"))
                .items(
                    object().properties(
                        time(FROM)
                            .label(LABEL_FROM)
                            .description(FROM_DESCRIPTION)
                            .required(true),
                        time(TO)
                            .label(LABEL_TO)
                            .description(TO_DESCRIPTION)
                            .required(true))))
        .output()
        .perform(DataMapperReplaceAllSpecifiedValuesAction::perform);

    private DataMapperReplaceAllSpecifiedValuesAction() {
    }

    @SuppressWarnings("unchecked")
    protected static Object perform(
        Parameters inputParameters, Parameters connectionParameters, ActionContext context) {

        List<ObjectMapping> mappings = inputParameters.getList(MAPPINGS, ObjectMapping.class, List.of());

        Map<Object, Object> mappingMap = mappings.stream()
            .collect(HashMap::new, (map, value) -> map.put(value.getFrom(), value.getTo()), HashMap::putAll);

        Integer inputType = inputParameters.getInteger(INPUT_TYPE);
        int mappingType = inputParameters.getRequiredInteger(TYPE);

        if (inputType != null && inputType.equals(1)) {
            Map<String, Object> input = inputParameters.getMap(INPUT, Object.class, Map.of());

            return fillOutput(mappingType, input, mappingMap);
        } else {
            List<Object> input = inputParameters.getList(INPUT, Object.class, List.of());
            List<Map<String, Object>> output = new LinkedList<>();

            for (Object object : input) {
                output.add(fillOutput(mappingType, (Map<String, Object>) object, mappingMap));
            }

            return output;
        }
    }

    private static Map<String, Object> fillOutput(
        int mappingType, Map<String, Object> inputMap, Map<Object, Object> mappingMap) {

        Map<String, Object> outputMap = new HashMap<>(inputMap);

        if (mappingType == 9) {
            for (Map.Entry<Object, Object> entry : mappingMap.entrySet()) {
                Set<Map.Entry<String, Object>> entries = outputMap.entrySet();

                for (Map.Entry<String, Object> outputEntry : entries) {
                    Object inputValue = inputMap.get(outputEntry.getKey());
                    Object key = entry.getKey();
                    Object value = entry.getValue();

                    Pattern pattern = Pattern.compile(key.toString());
                    Matcher matcher = pattern.matcher(inputValue.toString());

                    outputEntry.setValue(matcher.replaceAll(value.toString()));
                }
            }
        } else {
            for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
                if (mappingMap.containsKey(entry.getValue())) {
                    outputMap.put(entry.getKey(), mappingMap.get(entry.getValue()));
                }
            }
        }

        return outputMap;
    }
}
