package be.pxl;

import be.pxl.business.*;
import be.pxl.data.model.*;
import be.pxl.util.PathsUtility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Use this method if multiple invitees are to sign the document.
     */
    public void sendInvitationsForPdfFilePath(PdfFile pdfFile, List<Invitee> invitees) throws CmSignException {
        Document uploadedDocument = tryCreateDocument(pdfFile);

        DocumentField signingField = new DocumentField("signature", uploadedDocument.getId(), "{signature1}");

        for (Invitee invitee : invitees) {
            if (invitee.getFields() == null || invitee.getFields().isEmpty()) {
                invitee.addField(signingField);
            }
        }

        List<Document> documents = new ArrayList<>();
        documents.add(uploadedDocument);

        Dossier dossier = new Dossier(pdfFile.getFileName(), documents, invitees);
        tryCreateDossier(dossier);

        trySendInvite(invitees, dossier.getId());
    }

    /**
     * Use this method if only a single invitee has to sign the document
     */
    public void sendInvitationForPdfFilePath(PdfFile pdfFile, Invitee invitee) throws CmSignException {
        sendInvitationsForPdfFilePath(pdfFile, Arrays.asList(invitee));
    }

    private Document tryCreateDocument(PdfFile pdfFile) {
        String errorMessage;
        try {
            return documentHandler.createDocumentFrom(pdfFile);
        } catch (IOException e) {
            errorMessage = String.format("Couldn't send pdfFile %s, the file/binary is in the wrong format",
                    PathsUtility.getPdfPath().toString());
        } catch (URISyntaxException e) {
            errorMessage = String.format("The URI to which the document was being uploaded was incorrect",
                    PathsUtility.getPdfPath().toString());
        }

        throw new CmSignException(errorMessage);
    }

    private void tryCreateDossier(Dossier dossier) {
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
