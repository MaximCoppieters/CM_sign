package be.pxl;

import be.pxl.business.*;
import be.pxl.data.model.*;
import be.pxl.util.PathsUtility;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CmSignApi {
    private DocumentHandler documentHandler;
    private DossierHandler dossierHandler;
    private InviteHandler inviteHandler;

    public CmSignApi() {
        CredentialsSerializer credentialsSerializer = new CredentialsSerializer(PathsUtility.getCredentialsPath());
        Credentials cmApiCredentials = credentialsSerializer.getCredentials();
        documentHandler = new DocumentHandler(cmApiCredentials);
        dossierHandler = new DossierHandler(cmApiCredentials);
        inviteHandler = new InviteHandler(cmApiCredentials);
    }

    public void sendInvitationEmailsForDocuments(Path pdfFilePath, List<Invitee> invitees) {
        Document uploadedDocument = tryUploadDocument(pdfFilePath);

        SignDimensions signDimensions =
                new SignDimensions(new Point(0,0),0,0);
        DocumentField signingField =
                new DocumentField("initials", uploadedDocument.getId(), "1-3", signDimensions, "{sign1}");

        for (Invitee invitee : invitees) {
            invitee.addField(signingField);
        }

        List<Document> documents = new ArrayList<>();
        documents.add(uploadedDocument);

        Dossier dossier = new Dossier("Contract 1", documents, invitees);
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

        throw new RuntimeException(errorMessage);
    }

    private void tryUploadDossier(Dossier dossier) {
        try {
            dossierHandler.uploadDossier(dossier);
        } catch (IOException ioe) {
            throw new CmSignException(
                    String.format("Failed to upload dossier %s to CM Sign API",
                            dossier.getId()));
        } catch (URISyntaxException e) {
            throw new CmSignException(
                    String.format("Failed to upload dossier: URI endpoint was invalid",
                            dossier.getId()));
        }
    }

    private void trySendInvite(List<Invitee> invitees, String dossierId) {
        try {
            inviteHandler.sendEmailInvite(invitees, dossierId);
        } catch (IOException | URISyntaxException e) {
            throw new CmSignException(
                    String.format("Failed to send invites of dossier %s", dossierId));
        }
    }

    public static Document getHardCodedDocument() {
        String id = "3bcb6740-607a-4618-966e-a561b6353580";
        String name = "generics_presentatie";
        String hash = "4b1da4a47cb7bdfd06082d6b3255425b5b2735b3423d30a5f4cc01c32be7436e";
        LocalDateTime uploadDateTime = LocalDateTime.of(2019,1,30,2,9,0);

        return new Document(id, name, hash, uploadDateTime);
    }
}
