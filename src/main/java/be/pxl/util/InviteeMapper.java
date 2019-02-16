package be.pxl.util;

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
        ObjectNode inviteeJson = objectMapper.createObjectNode();

        inviteeJson.put("name", invitee.getName());
        inviteeJson.put("email", invitee.getEmail());
        inviteeJson.put("reference", invitee.getId());
        inviteeJson.put("locale", invitee.getLanguage().getLocale());

        ArrayNode fieldsJson = createFieldJsonArray(invitee.getFields());
        inviteeJson.set("fields", fieldsJson);

        return inviteeJson;
    }

    private ArrayNode createFieldJsonArray(List<DocumentField> fields) {
        List<JsonNode> fieldJsons = fields.stream()
                .map(documentFieldMapper::toJsonNode)
                .collect(Collectors.toList());

        return objectMapper.convertValue(fieldJsons, ArrayNode.class);
    }
}
