package services.provide.helper;

import org.springframework.http.HttpStatus;

/**
 * Created by tbennett on 11/8/16.
 */
public class RestUtil {
    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}