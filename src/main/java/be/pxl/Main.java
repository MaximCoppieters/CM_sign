package be.pxl;

import be.pxl.business.*;
import be.pxl.data.model.*;
import be.pxl.util.PathsUtility;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CredentialsSerializer credentialsSerializer = new CredentialsSerializer(PathsUtility.getCredentialsPath());
        Credentials cmApiCredentials = credentialsSerializer.getCredentials();
        DocumentHandler documentHandler = new DocumentHandler(cmApiCredentials);

        Document uploadedDocument = documentHandler.uploadDocument(PathsUtility.getPdfPath());
        //Document uploadedDocument = getHardCodedDocument();

        Invitee invitee = new Invitee("6c38955e-7324-41e7-97dd-0bcf55e275e2", "Maxim", "maxim.coppieters@student.pxl.be");
        SignDimensions signDimensions =
                new SignDimensions(new Point(0,0),0,0);
        DocumentField signingField =
                new DocumentField("initials", uploadedDocument.getId(), "1-3", signDimensions, "{sign1}");
        invitee.addField(signingField);

        List<Invitee> invitees = new ArrayList<>();
        invitees.add(invitee);

        List<Document> documents = new ArrayList<>();
        documents.add(uploadedDocument);
        Dossier dossier = new Dossier("Contract 1", documents, invitees);

        DossierHandler dossierHandler = new DossierHandler(cmApiCredentials);
        try {
            dossierHandler.uploadDossier(dossier);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        InviteHandler inviteHandler = new InviteHandler(cmApiCredentials);

        try {
            inviteHandler.sendEmailInvite(invitee, dossier.getId());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void writeDocument(Document document) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("document"))) {
            os.writeObject(document);
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
