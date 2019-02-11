package be.pxl.business;

import be.pxl.data.model.Dossier;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class DossierHandler extends ApiHandler {
    // private static final DateTimeFormatter dossierDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final String API_DOSSIER_ENDPOINT = "dossiers";
    private Credentials cmApiCredentials;
    private DossierUploader dossierUploader;

    public DossierHandler(Credentials cmApiCredentials) {
        this.cmApiCredentials = cmApiCredentials;
        createDossierUploader();
    }

    private void createDossierUploader() {
        try {
            URL cmApiDossierUploadUrl = PathsUtility.API_ROOT_PATH
                    .resolve(API_DOSSIER_ENDPOINT)
                    .toURL();

            dossierUploader = new DossierUploader(cmApiCredentials, cmApiDossierUploadUrl);
        } catch(MalformedURLException e) {
            throw new CmSignException("Dossier upload URL was invalid");
        }
    }

    public void uploadDossier(Dossier dossier) {
        DossierMapper dossierMapper = new DossierMapper();

        String dossierJson = dossierMapper.toJson(dossier);

        Logger logger = Logger.getLogger(DossierHandler.class.getName());
        try {
            HttpResponse dossierPostResponse = dossierUploader.upload(dossierJson);
            String dossierResponseJson = HttpUtility.getHttpBodyOf(dossierPostResponse);

            checkAndLogResponse(dossierPostResponse, dossierResponseJson);

            //add id to dossier
            dossierMapper.appendResponseJson(dossier, dossierResponseJson);
        } catch (IOException ioe) {
            logger.severe("Couldn't send post request to upload dossier " + dossier.getName() + ioe.getMessage());
        } catch (URISyntaxException e) {
            logger.severe("The API endpoint is invalid " + e.getMessage());
        }
    }

    @Override
    protected void checkAndLogResponse(HttpResponse pdfUploadResponse, String responseJson) {
        Logger logger = Logger.getLogger(DossierHandler.class.getName());
        if (HttpUtility.apiCallWasSuccessful(pdfUploadResponse)) {
            logger.info("Created dossier through API, Response was "
                    + HttpUtility.formulateResponse(responseJson));
        } else {
            throw new CmSignException("Failed to create dossier - "
                    + HttpUtility.formulateResponse(responseJson));
        }
    }
}
