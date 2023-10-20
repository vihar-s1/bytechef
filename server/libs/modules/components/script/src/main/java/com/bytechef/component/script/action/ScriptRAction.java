
/*
 * Copyright 2021 <your company/name>.
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

package com.bytechef.component.script.action;

import com.bytechef.component.script.constant.ScriptConstants;
import com.bytechef.hermes.component.Context;
import com.bytechef.hermes.component.InputParameters;
import com.bytechef.hermes.component.definition.ActionDefinition;
import com.bytechef.hermes.definition.Property;

import static com.bytechef.component.script.constant.ScriptConstants.INPUT;
import static com.bytechef.component.script.constant.ScriptConstants.R;
import static com.bytechef.component.script.constant.ScriptConstants.SCRIPT;
import static com.bytechef.hermes.component.definition.ComponentDSL.action;

import static com.bytechef.hermes.definition.DefinitionDSL.object;
import static com.bytechef.hermes.definition.DefinitionDSL.oneOf;
import static com.bytechef.hermes.definition.DefinitionDSL.string;

/**
 * @author Matija Petanjek
 * @author Ivica Cardic
 */
public class ScriptRAction {

    public static final ActionDefinition ACTION_DEFINITION = action(R)
        .title("R")
        .description("Executes custom R code.")
        .properties(
            object(INPUT)
                .label("Input")
                .description("Initialize parameter values used in the custom code.")
                .additionalProperties(oneOf()),
            string(SCRIPT)
                .label("R code")
                .description("Add your R custom logic here.")
                .controlType(Property.ControlType.CODE_EDITOR))
        .outputSchema(oneOf())
        .execute(ScriptRAction::executeR);

    protected static Object executeR(Context context, InputParameters inputParameters) {
        return ScriptConstants.POLYGLOT_ENGINE.execute("R", inputParameters);
    }
}
