package be.pxl.business;

import be.pxl.data.model.Invitee;
import be.pxl.util.PathsUtility;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class InviteHandler extends ApiHandler {
    private static final String API_INVITE_ENDPOINT_HEAD = "dossiers";
    private static final String API_INVITE_ENDPOINT_TAIL = "invites";
    private InviteSender inviteSender;

    public InviteHandler(Credentials credentials) {
        inviteSender = new InviteSender(credentials);
    }

    public void sendEmailInvite(List<Invitee> invitees, String dossierId)
            throws IOException, URISyntaxException {
        URL cmSignInvitePostUrl = generateApiUrlForDossierId(dossierId);
        inviteSender.setCmApiPostUrl(cmSignInvitePostUrl);
        HttpResponse inviteResponse = inviteSender.upload(invitees);
        String inviteResponseBody = HttpUtility.getHttpBodyOf(inviteResponse);

        checkAndLogResponse(inviteResponse, inviteResponseBody);
    }

    private URL generateApiUrlForDossierId(String dossierId) throws MalformedURLException {
        String inviteEndPoint = String.format("%s/%s/%s",
                API_INVITE_ENDPOINT_HEAD, dossierId, API_INVITE_ENDPOINT_TAIL);

        return PathsUtility.API_ROOT_PATH
                .resolve(inviteEndPoint)
                .toURL();
    }

    @Override
    protected void checkAndLogResponse(HttpResponse pdfUploadResponse, String responseJson) {
        Logger logger = Logger.getLogger(DossierHandler.class.getName());
        if (HttpUtility.apiCallWasSuccessful(pdfUploadResponse)) {
            logger.info("Invited clients to sign documents, response was " +
                    HttpUtility.formulateResponse(responseJson));
        } else {
            throw new CmSignException("Failed to invite clients - "
                    + HttpUtility.formulateResponse(responseJson));
        }
    }
}
