package be.pxl;

import be.pxl.business.*;
import be.pxl.data.model.*;
import be.pxl.util.PathsUtility;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstraction of the API CM offers in order to automate and digitalise the signing of PDF documents.
 * Under the hood the API will do the following things:
 * <ul>
 *     <li>Take the path of a PDF file, and a number of Invitees (POJO user object with name and email) </li>
 *     <li>Upload the file, creating a document on CM's service</li>
 *     <li>Take the ID of the created document and assign invitees to them,
 *     use this data to create a dossier on CM.
 *     </li>
 *     </li>
 *     <li>Take the generated ID of the generated dossier and </li>
 * </ul>
 */
public class CmSignApi {
    private DocumentHandler documentHandler;
    private DossierHandler dossierHandler;
    private InviteHandler inviteHandler;

    public CmSignApi() {
        documentHandler = new DocumentHandler();
        dossierHandler = new DossierHandler();
        inviteHandler = new InviteHandler();
    }

    public void sendInvitationEmailsForDocuments(Path pdfFilePath, List<Invitee> invitees) throws CmSignException {
        Document uploadedDocument = tryUploadDocument(pdfFilePath);

        SignDimensions signDimensions =
                new SignDimensions(new Point(0,0),0,0);
        DocumentField signingField =
                new DocumentField("signature", uploadedDocument.getId(), "{signature1}");

        for (Invitee invitee : invitees) {
            invitee.addField(signingField);
        }

        List<Document> documents = new ArrayList<>();
        documents.add(uploadedDocument);

        Dossier dossier = new Dossier(pdfFilePath.getFileName().toString(), documents, invitees);
        tryUploadDossier(dossier);

        trySendInvite(invitees, dossier.getId());
    }

    private Document tryUploadDocument(Path pdfFilePath) {
        String errorMessage;
        try {
            return documentHandler.uploadDocument(pdfFilePath);
        } catch (IOException e) {
            errorMessage = String.format("Couldn't find the document to upload at path %s",
                    PathsUtility.getPdfPath().toString());
        } catch (URISyntaxException e) {
            errorMessage = String.format("The URI to which the document was being uploaded was incorrect",
                    PathsUtility.getPdfPath().toString());
        }

        throw new CmSignException(errorMessage);
    }

    private void tryUploadDossier(Dossier dossier) {
        dossierHandler.uploadDossier(dossier);
    }

    private void trySendInvite(List<Invitee> invitees, String dossierId) {
        try {
            inviteHandler.sendEmailInvite(invitees, dossierId);
        } catch (IOException | URISyntaxException e) {
            throw new CmSignException(
                    String.format("Failed to send invites of dossier %s", dossierId));
        }
    }
}
