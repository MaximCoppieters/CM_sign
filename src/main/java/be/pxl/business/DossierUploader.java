package be.pxl.business;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URL;

public class DossierUploader extends PostRequestUnit<String> {
    public DossierUploader(Credentials credentials, URL cmApiPostUrl) {
        super(credentials, cmApiPostUrl);
    }

    @Override
    protected void setPostRequestHeaders(HttpPost dossierPostRequest) {
        dossierPostRequest.addHeader("Content-Type", "multipart/form-data");
        dossierPostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    @Override
    protected HttpEntity createPostRequestBody(String dossierJson) throws UnsupportedEncodingException {
        return new StringEntity(dossierJson);
    }
}
