package be.pxl.business;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.net.URL;


public class DocumentUploader extends PostRequestUnit<File> {
    private static final String REQUEST_PDF_BOUNDARY = "request-boundary";

    public DocumentUploader(URL cmApiPostUrl) {
        super(cmApiPostUrl);
    }

    protected void setPostRequestHeaders(HttpPost pdfPostRequest) {
        pdfPostRequest.addHeader("Content-Type", "multipart/form-data; boundary=" + REQUEST_PDF_BOUNDARY);
        pdfPostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    protected HttpEntity createPostRequestBody(File pdfFile) {
        return MultipartEntityBuilder.create()
                .addPart("file", new FileBody(pdfFile))
                .setBoundary(REQUEST_PDF_BOUNDARY)
                .build();
    }
}
