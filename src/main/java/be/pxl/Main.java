package be.pxl;

import be.pxl.business.CmSignException;
import be.pxl.data.model.Invitee;
import be.pxl.data.model.PdfFile;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Invitee invitee = new Invitee("Maxim", "maxim.coppieters@student.pxl.be");

        InputStream pdfFileStream = new FileInputStream(PathsUtility.getPdfPath().toFile());
        PdfFile pdfFile = new PdfFile("Test", pdfFileStream);

        CmSignApi cmSignApi = new CmSignApi();
        Logger logger = LogManager.getLogger(Main.class.getName());

        try {
            cmSignApi.sendInvitationForPdfFilePath(pdfFile, invitee);
        } catch(CmSignException e) {
            logger.error("Couldn't send invitation email for document "
                    + pdfFile.getFileName() + ". Reason: " + e.getMessage());
        }
    }
}
