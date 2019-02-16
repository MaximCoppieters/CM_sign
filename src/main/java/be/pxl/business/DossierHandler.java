package be.pxl.business;

import be.pxl.data.model.Dossier;
import be.pxl.util.DossierMapper;
import be.pxl.util.HttpUtility;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Responsable for all actions concerned with Dossiers.
 * Dossiers aggregate documents and invitees assigned to sign them
 * Uses composition of a DossierUploader object to do low level REST calls
 */
public class DossierHandler implements Handler {
    private static final String API_DOSSIER_ENDPOINT = "dossiers";
    private DossierUploader dossierUploader;

    public DossierHandler() {
        createDossierUploader();
    }

    private void createDossierUploader() {
        try {
            URL cmApiDossierUploadUrl = PathsUtility.API_ROOT_PATH
                    .resolve(API_DOSSIER_ENDPOINT)
                    .toURL();

            dossierUploader = new DossierUploader(cmApiDossierUploadUrl);
        } catch(MalformedURLException e) {
            throw new CmSignException("Dossier post URL was invalid");
        }
    }

    public void uploadDossier(Dossier dossier) {
        DossierMapper dossierMapper = new DossierMapper();

        String dossierJson = dossierMapper.toJson(dossier);

        Logger logger = LogManager.getLogger(DossierHandler.class.getName());
        try {
            logger.debug("Creating Dossier - Request body: " + dossierJson);
            HttpResponse dossierPostResponse = dossierUploader.post(dossierJson);
            String dossierResponseJson = HttpUtility.getHttpBodyOf(dossierPostResponse);

            checkAndLogResponse(dossierPostResponse, dossierResponseJson);

            //add id to dossier
            dossierMapper.appendResponseJson(dossier, dossierResponseJson);
        } catch (IOException ioe) {
            logger.error("Couldn't send post request to post dossier " + dossier.getName() + ioe.getMessage());
        } catch (URISyntaxException e) {
            logger.error("The API endpoint is invalid " + e.getMessage());
        }
    }

    @Override
    public void checkAndLogResponse(HttpResponse pdfUploadResponse, String responseJson) {
        Logger logger = LogManager.getLogger(DossierHandler.class.getName());
        if (HttpUtility.apiCallWasSuccessful(pdfUploadResponse)) {
            logger.debug("Created dossier through API, "
                    + HttpUtility.formulateResponse(responseJson));
        } else {
            throw new CmSignException("Failed to create dossier - "
                    + HttpUtility.formulateResponse(responseJson));
        }
    }
}
