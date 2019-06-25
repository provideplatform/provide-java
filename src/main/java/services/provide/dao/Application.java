package services.provide.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
    private String network_id = null;
    private String name = null;

    public static Application init(String json)
    {
        String name = null;
        String network_id = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            name = jsonNode.get("name").toString();
            network_id = jsonNode.get("network_id").toString();
        } catch(Exception e) {
            System.out.println(e);
        }

        return new Application(name, network_id);
    }

    private Application(String name, String network_id)
    {
        this.name = name;
        this.network_id = network_id;
    }

    public String getNetworkId() {
        return this.network_id;
    }

    public String getApplicationName() {
        return this.name;
    }
}