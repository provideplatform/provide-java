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

import java.util.Arrays;

public class ABI {
    private boolean constant;
    private ABIInput[] inputs;
    private String name;
    private ABIOutput[] outputs;
    private boolean payable;
    private String stateMutability;
    private String type;

    public ABI() {
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public ABIInput[] getInputs() {
        return inputs;
    }

    public void setInputs(ABIInput[] inputs) {
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ABIOutput[] getOutputs() {
        return outputs;
    }

    public void setOutputs(ABIOutput[] outputs) {
        this.outputs = outputs;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public String getStateMutability() {
        return stateMutability;
    }

    public void setStateMutability(String stateMutability) {
        this.stateMutability = stateMutability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ABI [constant=" + constant + ", inputs=" + Arrays.toString(inputs) + ", name=" + name + ", outputs="
                + Arrays.toString(outputs) + ", payable=" + payable + ", stateMutability=" + stateMutability + ", type="
                + type + "]";
    }

    
}