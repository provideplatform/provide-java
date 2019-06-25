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