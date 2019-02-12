package be.pxl;

import be.pxl.business.CmSignException;
import be.pxl.data.model.Invitee;
import be.pxl.util.PathsUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Invitee invitee = new Invitee("6c38955e-7324-41e7-97dd-0bcf55e275e2", "Maxim", "maxim.coppieters@student.pxl.be");

        List<Invitee> invitees = new ArrayList<>();
        invitees.add(invitee);

        Path pdfFilePath = PathsUtility.getPdfPath();

        CmSignApi cmSignApi = new CmSignApi();
        Logger logger = LogManager.getLogger(Main.class.getName());
        try {
            cmSignApi.sendInvitationEmailsForDocuments(pdfFilePath, invitees);
        } catch(CmSignException e) {
            logger.error("Couldn't send invitation email for document "
                    + pdfFilePath.getFileName().toString() + ". Reason: " + e.getMessage());
        }
    }
}
