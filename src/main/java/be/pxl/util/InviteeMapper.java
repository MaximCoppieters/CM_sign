package be.pxl.util;

import be.pxl.business.CmSignException;
import be.pxl.data.model.DocumentField;
import be.pxl.data.model.Invitee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.stream.Collectors;

public class InviteeMapper extends Mapper {
    private DocumentFieldMapper documentFieldMapper;

    public InviteeMapper() {
        documentFieldMapper = new DocumentFieldMapper();
    }

    public JsonNode toJsonNode(Invitee invitee) {
        throwIfNullOrDocumentFieldIsAbsent(invitee);

        ObjectNode inviteeJson = objectMapper.createObjectNode();

        inviteeJson.put("name", invitee.getName());
        inviteeJson.put("email", invitee.getEmail());
        inviteeJson.put("reference", invitee.getId());
        inviteeJson.put("locale", invitee.getLanguage().getLocale());

        ArrayNode fieldsJson = createFieldJsonArray(invitee.getFields());
        inviteeJson.set("fields", fieldsJson);

        return inviteeJson;
    }

    private void throwIfNullOrDocumentFieldIsAbsent(Invitee invitee) {
        if (invitee == null) throw new CmSignException("Invitee passed to InviteeMapper was null");
        // Check for sign field early, to avoid API exceptions
        if (invitee.getFields() == null || invitee.getFields().isEmpty()) {
            throw new CmSignException(
                    String.format("Invitee %s wasn't assigned a document field (signature location)",
                            invitee.getName()));
        }
    }

    private ArrayNode createFieldJsonArray(List<DocumentField> fields) {
        List<JsonNode> fieldJsons = fields.stream()
                .map(documentFieldMapper::toJsonNode)
                .collect(Collectors.toList());

        return objectMapper.convertValue(fieldJsons, ArrayNode.class);
    }
}
