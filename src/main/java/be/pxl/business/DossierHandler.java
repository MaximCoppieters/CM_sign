package be.pxl.business;

import be.pxl.data.model.Dossier;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class DossierHandler {
    private static final DateTimeFormatter dossierDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final String API_DOSSIER_ENDPOINT = "dossiers";
    private Credentials cmApiCredentials;

    public DossierHandler(Credentials cmApiCredentials) {
        this.cmApiCredentials = cmApiCredentials;
    }

    public void uploadDossier(Dossier dossier) throws IOException, URISyntaxException {
        DossierMapper dossierMapper = new DossierMapper();

        String dossierJson = dossierMapper.toJson(dossier);
        System.out.println(dossierJson);

        URL cmApiDossierUploadUrl = PathsUtility.API_ROOT_PATH.resolve(API_DOSSIER_ENDPOINT).toURL();
        DossierUploader dossierUploader = new DossierUploader(cmApiCredentials, cmApiDossierUploadUrl);

        HttpResponse dossierPostResponse = dossierUploader.upload(dossierJson);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dossierPostResponse.getEntity().writeTo(byteArrayOutputStream);
        String dossierResponseJson = byteArrayOutputStream.toString();

        dossierMapper.appendResponseJson(dossier, dossierResponseJson);
    }
}
