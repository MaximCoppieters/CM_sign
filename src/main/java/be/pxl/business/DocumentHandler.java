package be.pxl.business;

import be.pxl.data.model.Document;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class DocumentHandler extends ApiHandler {
    private static final String API_UPLOAD_ENDPOINT = "upload";
    private Credentials cmApiCredentials;

    public DocumentHandler(Credentials cmApiCredentials) {
        this.cmApiCredentials = cmApiCredentials;
    }

    public Document uploadDocument(Path documentPath) throws IOException, URISyntaxException {
        URI apiPdfUploadUrl = PathsUtility.API_ROOT_PATH.resolve(API_UPLOAD_ENDPOINT);

        DocumentUploader pdfUploader = new DocumentUploader(cmApiCredentials, apiPdfUploadUrl.toURL());
        HttpResponse pdfUploadResponse = pdfUploader.upload(documentPath.toFile());
        String documentJsonString = HttpUtility.getHttpBodyOf(pdfUploadResponse);

        checkAndLogResponse(pdfUploadResponse, documentJsonString);

        DocumentMapper documentMapper = new DocumentMapper();
        Document document = documentMapper.fromJson(documentJsonString);

        return document;
    }

    @Override
    protected void checkAndLogResponse(HttpResponse pdfUploadResponse, String responseJson) {
        Logger logger = Logger.getLogger(DocumentHandler.class.getName());
        if (HttpUtility.apiCallWasSuccessful(pdfUploadResponse)) {
            logger.info("Successfully uploaded document"
                    + HttpUtility.formulateResponse(responseJson));
        } else {
            throw new CmSignException("Failed to create - "
                    + HttpUtility.formulateResponse(responseJson));
        }
    }
}
