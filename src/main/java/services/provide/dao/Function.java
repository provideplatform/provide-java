/*
 * Copyright 2017-2022 Provide Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services.provide.dao;

public class Function {

    private String name = null;
    private Field[] inputs = null;
    private Field[] outputs = null;
    private boolean isPayable = false;

    public Function(String name, Field[] inputs, Field[] outputs, boolean isPayable)
    {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
        this.isPayable = isPayable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field[] getInputs() {
        return inputs;
    }

    public void setInputs(Field[] inputs) {
        this.inputs = inputs;
    }

    public Field[] getOutputs() {
        return outputs;
    }

    public void setOutputs(Field[] outputs) {
        this.outputs = outputs;
    }

    public boolean isPayable() {
        return isPayable;
    }

    public void setPayable(boolean isPayable) {
        this.isPayable = isPayable;
    }

    
}