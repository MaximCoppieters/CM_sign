package be.pxl.business;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * DossierUploader objects are low level objects concerned with the creation
 * of Dossiers on CM's API. This is done using POST requests.
 */
public class DossierUploader extends PostRequestUnit<String> {
    public DossierUploader(URL cmApiPostUrl) {
        super(cmApiPostUrl);
    }

    @Override
    protected void setRequestHeaders(HttpRequestBase dossierPostRequest) {
        dossierPostRequest.addHeader("Content-Type", "application/json");
        dossierPostRequest.addHeader("Authorization", generateAuthorizationHeaderProperty(credentials));
    }

    @Override
    protected HttpEntity createPostRequestBody(String dossierJson) throws UnsupportedEncodingException {
        return new StringEntity(dossierJson);
    }
}
