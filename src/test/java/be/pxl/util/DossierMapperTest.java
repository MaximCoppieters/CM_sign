package be.pxl.util;

import be.pxl.TestUtil;
import be.pxl.business.CmSignException;
import be.pxl.data.model.Document;
import be.pxl.data.model.DocumentField;
import be.pxl.data.model.Dossier;
import be.pxl.data.model.Invitee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DossierMapperTest {
    private Dossier testDossier = initializeTestDossier();
    private DossierMapper dossierMapper = new DossierMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    private Dossier initializeTestDossier() {
        List<Document> documents = Arrays
                .asList(new Document("documentId", "documentName", "documentHash", LocalDateTime.now()));
        List<Invitee> invitees = Arrays.asList(new Invitee("Test", "Hallo"));

        DocumentField signingField = new DocumentField("signature", "documentId", "{signature1}");
        invitees.forEach(invitee -> invitee.addField(signingField));

        return new Dossier("TestDossier", documents, invitees);
    }

    @Test(expected = CmSignException.class)
    public void test_toJson_throwsCmSignExceptionIfDossierIsNull() {
        dossierMapper.toJson(null);
    }

    @Test
    public void test_toJson_containsDossierName() {
        String dossierJson = dossierMapper.toJson(testDossier);

        String nameField = TestUtil.formJsonField("name", testDossier.getName());

        assertThat(dossierJson, containsString(nameField));
    }

    @Test
    public void test_toJson_filesValueIsNotNull() throws IOException {
        String dossierJson = dossierMapper.toJson(testDossier);

        JsonNode files = objectMapper.readTree(dossierJson).get("files");

        assertTrue(files instanceof ArrayNode);
    }

    @Test
    public void test_toJson_inviteesValueIsNotNull() throws IOException {
        String dossierJson = dossierMapper.toJson(testDossier);

        JsonNode files = objectMapper.readTree(dossierJson).get("invitees");

        assertTrue(files instanceof ArrayNode);
    }
}