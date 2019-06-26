package services.provide.client;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import services.provide.helper.CustomResponseErrorHandler;
import services.provide.helper.ProvideServicesException;

public class ApiClient {
    private static final String DEFAULT_HOST = "api.provide.services";
    private static final String DEFAULT_SCHEME = "https";
    private String baseUrl;
    private String token;
    private String path = "api/v1/";
    private RestTemplate rest;
    private ObjectMapper mapper;
    private String subject;

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

        this.rest = new RestTemplate();
        this.rest.setErrorHandler(new CustomResponseErrorHandler());

        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ArrayList<String> get(String uri, MultiValueMap<String, String> params) throws ProvideServicesException
    {
        return this.call(HttpMethod.GET, uri, params, null);
    }

    public ArrayList<String> post(String uri, String params) throws ProvideServicesException {
        return this.call(HttpMethod.POST, uri, params, null);
    }

    public ArrayList<String> put(String uri, MultiValueMap<String, String> params) throws ProvideServicesException {
        return this.call(HttpMethod.PUT, uri, params, null);
    }

    public ArrayList<String> delete(String uri, MultiValueMap<String, String> params) throws ProvideServicesException {
        return this.call(HttpMethod.DELETE, uri, params, null);
    }

    private String getApplicationId() {
        
        try {
            Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
                @Override
                public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                    subject = claims.getSubject();
                    return "".getBytes();
                }
            }).parseClaimsJws(this.token);
        } catch(Exception e) {}

        String[] subParts = subject.split(":");
        if (subParts.length != 2)
            return null;
        
        if (!subParts[0].equals("application"))
            return null;
            
        return subParts[1];
    }

    /**
     * Currently coded this method to mimic the provide-groovy. Once a better review of all the Provide API's the generic ArrayList<String>
     * will be modified to return a more specific Object to make getting the details of the response easier.
     * @param method
     * @param uri
     * @param params
     * @param headers
     * @return
     */
    private ArrayList<String> call(HttpMethod method, String uri, String params, HttpHeaders headers) throws ProvideServicesException
    {
        HttpEntity<String> requestEntity;
        ResponseEntity<String> responseEntity;
        ArrayList<String> respBody = new ArrayList<>();
        HttpHeaders httpHeaders = null;
        HttpMethod httpMethod = null;
        UriComponents uriComponents = null;
        Writer jsonWriter = new StringWriter();
        try {
            if (method == null)
                throw new Exception("method cannot be null");
            httpHeaders = (headers == null ? this.defaultHeaders() : headers);
            
            httpMethod = method;
            if (httpMethod == HttpMethod.GET) {
                uriComponents = UriComponentsBuilder.fromHttpUrl(this.baseUrl+uri+"/"+params).build(true);
                requestEntity = new HttpEntity<String>("", httpHeaders);
            } else {
                uriComponents = UriComponentsBuilder.fromHttpUrl(this.baseUrl+uri).build(true);
                mapper.writeValue(jsonWriter, params);
                jsonWriter.flush();
                requestEntity = new HttpEntity<String>(jsonWriter.toString(), httpHeaders);
            }
            responseEntity = rest.exchange(uriComponents.toUri(), httpMethod, requestEntity, String.class);

            respBody.add(responseEntity.getStatusCode().toString());
            respBody.add(responseEntity.getBody());

        } catch(Exception e) {
            throw new ProvideServicesException("ERROR: Failed to call provide API; " + e);
        } 

        return respBody;
    }

    /**
     * Sets the default HttpHeaders for calls
     * @return
     */
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