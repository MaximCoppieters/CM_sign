package be.pxl.business;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class PostRequestUnit<T> {
    protected Credentials credentials;
    protected URL cmApiPostUrl;

    public PostRequestUnit(Credentials credentials) {
        this.credentials = credentials;
    }

    public PostRequestUnit(Credentials credentials, URL cmApiPostUrl) {
        this.credentials = credentials;
        this.cmApiPostUrl = cmApiPostUrl;
    }

    public CloseableHttpResponse upload(T body) throws URISyntaxException, IOException {
        if (cmApiPostUrl == null) {
            throw new NullPointerException("Couldn't post request because URL wasn't specified");
        }

        System.out.println(cmApiPostUrl);
        CloseableHttpClient cmRestClient = HttpClients.createDefault();
        HttpPost pdfPostRequest = new HttpPost(cmApiPostUrl.toURI());
        setPostRequestHeaders(pdfPostRequest);

        HttpEntity pdfPostRequestBody = createPostRequestBody(body);
        pdfPostRequest.setEntity(pdfPostRequestBody);

        return cmRestClient.execute(pdfPostRequest);
    }

    abstract protected void setPostRequestHeaders(HttpPost post);

    abstract protected HttpEntity createPostRequestBody(T bodyContent) throws UnsupportedEncodingException;

    public String generateAuthorizationHeaderProperty(Credentials credentials) {
        return String.format("Bearer %s", credentials.getCmJsonWebtoken());
    }

    public void setCmApiPostUrl(URL cmApiPostUrl) {
        this.cmApiPostUrl = cmApiPostUrl;
    }
}
