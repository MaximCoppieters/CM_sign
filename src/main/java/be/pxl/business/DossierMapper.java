package be.pxl.business;

import be.pxl.data.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DossierMapper extends Mapper {

    public String toJson(Dossier dossier) {
        ObjectNode dossierJson = objectMapper.createObjectNode();

        dossierJson.put("name", dossier.getName());
        JsonNode filesJson = createFilesJson(dossier);
        dossierJson.set("files", filesJson);

        JsonNode invitessJson = createInviteeJsonArray(dossier.getInvitees());
        dossierJson.set("invitees", invitessJson);

        return dossierJson.toString();
    }

    private JsonNode createFilesJson(Dossier dossier) {
        Map<String, String> documentIds = dossier.getFiles()
                        .stream()
                        .map(Document::getId)
                        .collect(Collectors.toMap(d -> "id", Function.identity()));
        return objectMapper.convertValue(documentIds, JsonNode.class);
    }

    private ArrayNode createInviteeJsonArray(List<Invitee> invitees) {
        List<JsonNode> inviteesJson = invitees.stream()
                .map(this::createInviteeJsonNode)
                .collect(Collectors.toList());

        return objectMapper.convertValue(inviteesJson, ArrayNode.class);
    }

    private JsonNode createInviteeJsonNode(Invitee invitee) {
        ObjectNode inviteeJson = objectMapper.createObjectNode();

        inviteeJson.put("name", invitee.getName());
        inviteeJson.put("email", invitee.getEmail());
        inviteeJson.put("reference", invitee.getReference());

        ArrayNode fieldsJson = createFieldJsonArray(invitee.getFields());
        inviteeJson.set("fields", fieldsJson);

        return inviteeJson;
    }

    private ArrayNode createFieldJsonArray(List<DocumentField> fields) {
        List<JsonNode> fieldJsons = fields.stream()
                        .map(this::createFieldJsonNode)
                        .collect(Collectors.toList());

        return objectMapper.convertValue(fieldJsons, ArrayNode.class);
    }

    private JsonNode createFieldJsonNode(DocumentField field) {
        ObjectNode fieldJson = objectMapper.createObjectNode();

        fieldJson.put("type", field.getType());
        fieldJson.put("file", field.getDocumentId());
        fieldJson.put("range", field.getRange());

        ObjectNode signDimensionsJson = createDimensionsJsonNode(field.getSignDimensions());
        fieldJson.set("dimensions", signDimensionsJson);

        return fieldJson;
    }

    private ObjectNode createDimensionsJsonNode(SignDimensions signDimensions) {
        ObjectNode dimensionsJson = objectMapper.createObjectNode();

        ObjectNode originJson = createOriginJsonNode(signDimensions.getOrigin());
        dimensionsJson.set("origin", originJson);

        ObjectNode sizeJson = createSizeJsonNode(signDimensions.getWidth(), signDimensions.getHeight());
        dimensionsJson.set("size", sizeJson);

        return dimensionsJson;
    }

    private ObjectNode createOriginJsonNode(Point origin) {
        ObjectNode sizeJson = objectMapper.createObjectNode();
        sizeJson.put("width", origin.x);
        sizeJson.put("height", origin.y);
        return sizeJson;
    }

    private ObjectNode createSizeJsonNode(int width, int height) {
        ObjectNode sizeJsonNode = objectMapper.createObjectNode();
        sizeJsonNode.put("width", width);
        sizeJsonNode.put("height", height);
        return sizeJsonNode;
    }
}
