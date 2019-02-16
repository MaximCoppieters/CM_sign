package be.pxl.business;

import be.pxl.data.model.Invitee;
import be.pxl.util.InvitationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

/**
 * InviteSender objects are concerned with low level HTTP POST requests to
 * CM Sign API, in order to send invitations to invitees
 */
public class InviteSender extends PostRequestUnit<List<Invitee>> {
    private InvitationMapper invitationMapper = new InvitationMapper();

    public InviteSender(URL requestUrl) {
        super(requestUrl);
    }

    public InviteSender() {
        super(null);
    }

    @Override
    protected void setRequestHeaders(HttpRequestBase invitePostRequest) {
        invitePostRequest.addHeader("Content-Type", "application/json");
        invitePostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    @Override
    protected HttpEntity createPostRequestBody(List<Invitee> invitees) throws UnsupportedEncodingException {
        return new StringEntity(invitationMapper.toInvitationsJson(invitees).toString());
    }

}
