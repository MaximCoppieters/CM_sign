package be.pxl.util;

import be.pxl.data.model.*;
import be.pxl.util.Mapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DossierMapper extends Mapper {

    public Dossier appendResponseJson(Dossier dossier, String dossierResponseJsonString)
            throws IOException {
        JsonNode dossierResponseJson = objectMapper.readTree(dossierResponseJsonString);

        dossier.setId(dossierResponseJson.get("id").textValue());

        return dossier;
    }

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
        ArrayNode filesNode = objectMapper.createArrayNode();
        for (Document document : dossier.getFiles()) {
            ObjectNode fileNode = objectMapper.createObjectNode();
            fileNode.put("id", document.getId());
            filesNode.add(fileNode);
        }

        return filesNode;
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
        inviteeJson.put("reference", invitee.getId());

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

        if (field.getType().equals("signature")) {
            fieldJson.put("tag", field.getTag());
        } else {
            fieldJson.put("range", field.getRange());
            ObjectNode signDimensionsJson = createDimensionsJsonNode(field.getSignDimensions());
            fieldJson.set("dimensions", signDimensionsJson);
        }

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
        sizeJson.put("x", origin.x);
        sizeJson.put("y", origin.y);
        return sizeJson;
    }

    private ObjectNode createSizeJsonNode(int width, int height) {
        ObjectNode sizeJsonNode = objectMapper.createObjectNode();
        sizeJsonNode.put("width", width);
        sizeJsonNode.put("height", height);
        return sizeJsonNode;
    }
}
