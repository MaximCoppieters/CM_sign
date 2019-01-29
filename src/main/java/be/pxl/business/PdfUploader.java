package be.pxl.business;

import be.pxl.util.FilePaths;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;


public class PdfUploader {
    private URL cmApiRootUrl;
    private Credentials credentials;

    private static final String REQUEST_PDF_BOUNDARY = "request-boundary";

    public PdfUploader(Credentials credentials, URL apiUrl) {
        this.credentials = credentials;
        cmApiRootUrl = apiUrl;
    }

    public HttpEntity postPdf(File pdfFile) throws IOException, URISyntaxException {
        CloseableHttpClient cmRestClient = HttpClients.createDefault();

        HttpPost pdfPostRequest = new HttpPost(cmApiRootUrl.toURI());
        setPdfPostRequestHeaders(pdfPostRequest);

        HttpEntity pdfPostRequestBody = createPdfPostRequestBody(pdfFile);
        pdfPostRequest.setEntity(pdfPostRequestBody);

        CloseableHttpResponse pdfPostResponse = cmRestClient.execute(pdfPostRequest);

        return pdfPostResponse.getEntity();
    }

    private void setPdfPostRequestHeaders(HttpPost pdfPostRequest) {
        pdfPostRequest.addHeader("Content-Type", "multipart/form-data; boundary=" + REQUEST_PDF_BOUNDARY);
        pdfPostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty());
    }

    private HttpEntity createPdfPostRequestBody(File pdfFile) {
        return MultipartEntityBuilder.create()
                .addPart("file", new FileBody(pdfFile))
                .setBoundary(REQUEST_PDF_BOUNDARY)
                .build();
    }

    private String generateAuthorizationHeaderProperty() {
        return String.format("Bearer %s", credentials.getCmJsonWebtoken());
    }
}
