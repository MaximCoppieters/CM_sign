package be.pxl.business;

import org.apache.http.HttpResponse;

/**
 * Handlers are high level abstractions of objects that do lower level operations
 * such as HTTP requests and database access
 * Handlers should log and check the responses of API requests.
 * This is to improve safety and ease of debugging
 */
public interface Handler {
     public void checkAndLogResponse
            (HttpResponse pdfUploadResponse, String responseJson);
}
