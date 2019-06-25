package services.provide.helper;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by tbennett on 11/8/16.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.print("Response Error: " + response.getStatusCode() + " " + response.getStatusText());
    }
}