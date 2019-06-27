package services.provide.microservices;

import services.provide.client.ApiClient;
import services.provide.dao.Application;

public class Ident {
    private static final String DEFAULT_HOST = "ident.provide.services";
    private ApiClient client = null;
    public static Ident init(String token)
    {
        return new Ident(token);
    }

    private Ident(String token)
    {
        String schema = System.getenv("IDENT_API_SCHEME");
        String host = System.getenv("IDENT_API_HOST");
        host = (host == null ? DEFAULT_HOST : host);

        this.client = ApiClient.init(schema, host, token);
    }

    public Application createApplication(String params)
    {

    }

    public Application updateApplication(String params)
    {

    }

    public Application[] fetchApplications() {

    }

    public Application fetchApplicationDetails(String app_id)
    {

    }

    public Token[] fetchApplicationTokens(String app_id)
    {

    }

    public boolean authenticate(String params) {

    }

    public Token[] fetchTokens() {

    }

    public Token fetchTokenDetails(String token_id)
    {

    }

    public void deleteToken(String token_id)
    {

    }

    public User createUser(User user)
    {

    }

    public User[] fetchUsers() {

    }

    public User fetchUserDetails(String userId)
    {

    }

    public User updateUser(User user)
    {
        
    }
}