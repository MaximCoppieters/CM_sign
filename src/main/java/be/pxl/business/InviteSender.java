package be.pxl.business;

import be.pxl.data.model.Invitee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

public class InviteSender extends PostRequestUnit<List<Invitee>> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public InviteSender(Credentials credentials) {
        super(credentials);
    }

    public InviteSender(Credentials credentials, URL cmApiPostUrl) {
        super(credentials, cmApiPostUrl);
    }

    @Override
    protected void setPostRequestHeaders(HttpPost invitePostRequest) {
        invitePostRequest.addHeader("Content-Type", "application/json");
        invitePostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    @Override
    protected HttpEntity createPostRequestBody(List<Invitee> invitees) throws UnsupportedEncodingException {
        ArrayNode invitePostRequestBody = objectMapper.createArrayNode();

        for (Invitee invitee : invitees) {
            ObjectNode inviteJson = objectMapper.createObjectNode();

            final int INVITE_DURATION_SECONDS = 2592000;
            inviteJson.put("inviteeId", invitee.getId());
            inviteJson.put("email", true);
            inviteJson.put("expiresIn", INVITE_DURATION_SECONDS);

            invitePostRequestBody.add(inviteJson);
        }
        return new StringEntity(invitePostRequestBody.asText());
    }
}
