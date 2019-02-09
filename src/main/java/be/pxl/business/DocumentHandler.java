package be.pxl.business;

import be.pxl.data.model.Document;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class DocumentHandler {
    private static final String API_UPLOAD_ENDPOINT = "upload";
    private Credentials cmApiCredentials;

    public DocumentHandler(Credentials cmApiCredentials) {
        this.cmApiCredentials = cmApiCredentials;
    }

    public Document uploadDocument(Path documentPath) {
        URI apiPdfUploadUrl = PathsUtility.API_ROOT_PATH.resolve(API_UPLOAD_ENDPOINT);

        try {
            DocumentUploader pdfUploader = new DocumentUploader(cmApiCredentials, apiPdfUploadUrl.toURL());
            HttpResponse pdfUploadResponse = pdfUploader.upload(documentPath.toFile());

            pdfUploadResponse.getEntity().getContent();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfUploadResponse.getEntity().writeTo(byteArrayOutputStream);
            String documentJsonString = byteArrayOutputStream.toString();

            DocumentMapper documentMapper = new DocumentMapper();
            Document document = documentMapper.fromJson(documentJsonString);

            return document;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
