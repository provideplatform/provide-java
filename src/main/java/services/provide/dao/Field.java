package services.provide.dao;

public class Field {
    private String name = null;
    private String type = null;

    public Field(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return this.name;
    }

    public String getType()
    {
        return this.type;
    }
}