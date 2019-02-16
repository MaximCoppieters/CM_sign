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

/**
 * Objects of this class map Dossiers to JSON, for use with the API
 */
public class DossierMapper extends Mapper {

    private InviteeMapper inviteeMapper;

    public DossierMapper() {
        inviteeMapper = new InviteeMapper();
    }

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
                .map(inviteeMapper::toJsonNode)
                .collect(Collectors.toList());

        return objectMapper.convertValue(inviteesJson, ArrayNode.class);
    }

}
