package services.provide.dao;

public class Contract
{
    private String id;
    private String name;
    private String address;
    private Function[] functions;

    public Contract(String id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public Function[] getFunctions() {
        return functions;
    }

    public void setFunctions(Function[] functions) {
        this.functions = functions;
    }

    
}