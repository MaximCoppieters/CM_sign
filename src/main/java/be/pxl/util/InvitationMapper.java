package be.pxl.util;

import be.pxl.data.model.Invitee;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.stream.Collectors;

public class InvitationMapper extends Mapper {
    private final int INVITE_DURATION_SECONDS = 2592000;

    public ArrayNode toInvitationsJson(List<Invitee> invitees) {
        List<ObjectNode> invitations = invitees.stream()
                .map(this::toInvitationJson)
                .collect(Collectors.toList());

        return objectMapper.convertValue(invitations, ArrayNode.class);
    }

    private ObjectNode toInvitationJson(Invitee invitee) {
        return objectMapper.createObjectNode()
                .put("inviteeId", invitee.getId())
                .put("email", true)
                .put("expiresIn", INVITE_DURATION_SECONDS);
    }
}
