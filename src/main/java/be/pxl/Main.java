package be.pxl;

import be.pxl.business.Credentials;
import be.pxl.business.CredentialsSerializer;
import be.pxl.business.PdfUploader;
import be.pxl.util.FilePaths;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class Main {

    private static final String apiRootUrl = "https://api.sandbox.cmdisp.com/sign/v1/upload";
/*    private static final String apiRootUrl = "http://localhost:8000";*/

    public static void main(String[] args) throws FileNotFoundException {
        Path pdfFilePath = FilePaths.getPdfPath();

        CredentialsSerializer credentialsSerializer = new CredentialsSerializer(FilePaths.getCredentialsPath());

        try {
            Credentials cmApiCredentials = credentialsSerializer.getCredentials();
            URL cmApiRootUrl = new URL(apiRootUrl);

            PdfUploader pdfUploader = new PdfUploader(cmApiCredentials, cmApiRootUrl);
            HttpEntity documentJson = pdfUploader.postPdf(pdfFilePath.toFile());


        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
