package be.pxl.business;

import be.pxl.data.model.PdfFile;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.net.URL;


public class PdfUploader extends PostRequestUnit<PdfFile> {
    private static final String REQUEST_PDF_BOUNDARY = "request-boundary";
    private static final String requestFileParameter = "file";

    public PdfUploader(URL cmApiPostUrl) {
        super(cmApiPostUrl);
    }

    protected void setPostRequestHeaders(HttpPost pdfPostRequest) {
        pdfPostRequest.addHeader("Content-Type", "multipart/form-data; boundary=" + REQUEST_PDF_BOUNDARY);
        pdfPostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    @Override
    protected HttpEntity createPostRequestBody(PdfFile pdfFile) {
        MultipartEntityBuilder postRequestBuilder = MultipartEntityBuilder.create();
        addPdfToRequestBody(postRequestBuilder, pdfFile);

        return postRequestBuilder
                .setBoundary(REQUEST_PDF_BOUNDARY)
                .build();
    }

    private void addPdfToRequestBody(MultipartEntityBuilder postRequestBuilder, PdfFile pdfFile) {
        if (pdfFile.isLocalFile()) {
            postRequestBuilder.addPart(requestFileParameter, new FileBody(pdfFile.getPdfFilePath().toFile()));
        } else {
            // The Octet stream content type is used for pdf binaries
            postRequestBuilder.addBinaryBody(requestFileParameter, pdfFile.getPdfBinaryStream(),
                    ContentType.APPLICATION_OCTET_STREAM, pdfFile.getFileName());
        }
    }
}
