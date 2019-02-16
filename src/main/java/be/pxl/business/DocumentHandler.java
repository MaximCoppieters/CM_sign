package be.pxl.business;

import be.pxl.data.model.Document;
import be.pxl.data.model.PdfFile;
import be.pxl.util.DocumentMapper;
import be.pxl.util.HttpUtility;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Responsable for all actions concerned with documents
 * Documents are PDF files that have been posted to the CM api and thus given an ID, hash and upload date
 * Uses composition of a PdfUploader object to do low level REST calls
 */
public class DocumentHandler implements Handler {
    private static final String API_UPLOAD_ENDPOINT = "upload";
    private PdfUploader pdfUploader;

    public DocumentHandler() {
         URI apiPdfUploadUrl = PathsUtility.API_ROOT_PATH.resolve(API_UPLOAD_ENDPOINT);
    try {
        pdfUploader = new PdfUploader(apiPdfUploadUrl.toURL());
        } catch (MalformedURLException e) {
            throw new CmSignException(
                    String.format("Couldn't form CM API Document Creation URI from base %s and endpoint %s"
                            ,PathsUtility.API_ROOT_PATH.toString(), API_UPLOAD_ENDPOINT));
        }
    }

    public Document createDocumentFrom(PdfFile pdfFile) throws IOException, URISyntaxException {
        HttpResponse pdfUploadResponse = pdfUploader.post(pdfFile);
        String documentJsonString = HttpUtility.getHttpBodyOf(pdfUploadResponse);

        checkAndLogResponse(pdfUploadResponse, documentJsonString);

        DocumentMapper documentMapper = new DocumentMapper();
        Document document = documentMapper.fromJson(documentJsonString);

        return document;
    }

    @Override
    public void checkAndLogResponse(HttpResponse pdfUploadResponse, String responseJson) {
        Logger logger = LogManager.getLogger(DocumentHandler.class.getName());
        if (HttpUtility.apiCallWasSuccessful(pdfUploadResponse)) {
            logger.debug("Successfully uploaded document"
                    + HttpUtility.formulateResponse(responseJson));
        } else {
            throw new CmSignException("Failed to create document - "
                    + HttpUtility.formulateResponse(responseJson));
        }
    }
}
