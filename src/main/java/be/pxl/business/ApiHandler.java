package be.pxl.business;

import org.apache.http.HttpResponse;

public abstract class ApiHandler {
    protected abstract void checkAndLogResponse
            (HttpResponse pdfUploadResponse, String responseJson);
}
