import org.springframework.http.HttpHeaders;

public class ApiClient {
    private static final String DEFAULT_HOST = "api.provide.services";
    private static final String DEFAULT_SCHEME = "https";
    private String baseUrl;
    private String token;
    private String path = "api/v1/";

    public static ApiClient init(String schema, String host, String token)
    {
        return new ApiClient(schema, host, token);
    }

    private ApiClient(String schema, String host, String token)
    {
        if (schema == null)
            schema = DEFAULT_SCHEME;
        
        if (host == null)
            host = DEFAULT_HOST;

        this.baseUrl = schema+"://"+host+"/"+this.path;
        this.token = token;
    }


   

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = null;
        String userAgent = System.getenv("API_USER_AGENT");
        if (userAgent == null)
           userAgent = "provide-java client";

        if (userAgent != null && this.token != null)
        {
            headers = new HttpHeaders();
            headers.add("User-Agent", userAgent);
            headers.add("Authorization", "bearer " + this.token);
        }
        return headers;
    }
}