package be.pxl.business;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpUtility {
    public static String getHttpBodyOf(HttpResponse response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.getEntity().writeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }

    public static boolean apiCallWasSuccessful(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED;
    }

    public static String formulateResponse(String responseJson) {
        return "Response was " + responseJson;
    }
}
