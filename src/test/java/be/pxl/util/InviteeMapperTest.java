package be.pxl.util;

import be.pxl.business.CmSignException;
import be.pxl.data.model.DocumentField;
import be.pxl.data.model.Invitee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class InviteeMapperTest {

    private String inviteeName = "Maxim";
    private String inviteeEmail = "maxim.coppieters@student.pxl.be";
    private String inviteeReference = "6c38955e-7324-41e7-97dd-0bcf55e275e2";

    private InviteeMapper inviteeMapper = new InviteeMapper();
    private Invitee testInvitee = new Invitee
            (inviteeReference,inviteeName, inviteeEmail);

    private DocumentField documentField = new DocumentField
            ("signature", "8d817359-7eb8-4b6e-9030-17ac286b7dc0", "{signature1}");

    @Test(expected = CmSignException.class)
    public void test_toJsonNode_throwsCmSignExceptionIfInviteeIsNull() {
        inviteeMapper.toJsonNode(null);
    }

    @Test(expected = CmSignException.class)
    public void test_toJsonNode_throwsCmSignExceptionIfDocumentFieldIsAbsent() {
        inviteeMapper.toJsonNode(testInvitee);
    }

    @Test
    public void test_toJsonNode_containsRequiredApiFields() {
        testInvitee.addField(documentField);
        JsonNode inviteeJson = inviteeMapper.toJsonNode(testInvitee);

        String name = inviteeJson.get("name").textValue();
        String email = inviteeJson.get("email").textValue();
        String reference = inviteeJson.get("reference").textValue();
        JsonNode fields = inviteeJson.get("fields");

        assertEquals(name, inviteeName);
        assertEquals(email, inviteeEmail);
        assertEquals(reference, inviteeReference);
        assertTrue(fields instanceof ArrayNode);
    }

    @Test
    public void test_toJsonNode_fieldsParameterCanContainMultipleObjects() {
        testInvitee.addField(documentField);
        testInvitee.addField(documentField);
        JsonNode inviteeJson = inviteeMapper.toJsonNode(testInvitee);

        JsonNode fields = inviteeJson.get("fields");

        assertEquals(fields.size(), 2);
    }
}
