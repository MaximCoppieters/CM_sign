package be.pxl.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class holds methods that are commonly used in HTTP requests and responses
 */
public class HttpUtility {
    public static String getHttpBodyOf(HttpResponse response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.getEntity().writeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }

    public static boolean apiCallWasSuccessful(HttpResponse response) {
        switch (response.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
            case HttpStatus.SC_CREATED:
            case HttpStatus.SC_ACCEPTED: return true;
            default: return false;
        }
    }

    public static String formulateResponse(String responseJson) {
        return "Response was " + responseJson;
    }
}
