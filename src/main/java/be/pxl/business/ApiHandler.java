package be.pxl.business;

import org.apache.http.HttpResponse;

/**
 * Handlers should log and check the responses of API requests.
 * This is to improve safety and ease of debugging
 */
public interface ApiHandler {
     public void checkAndLogResponse
            (HttpResponse pdfUploadResponse, String responseJson);
}
