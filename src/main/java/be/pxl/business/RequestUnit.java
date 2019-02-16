package be.pxl.business;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class RequestUnit<T> {
    protected Credentials credentials;
    protected URL requestUrl;

    public RequestUnit() {
        this(null);
    }

    public RequestUnit(URL requestUrl) {
        this.credentials = Credentials.getInstance();
        this.requestUrl = requestUrl;
    }

    protected String generateAuthorizationHeaderProperty(Credentials credentials) {
        return String.format("Bearer %s", credentials.getCmJsonWebtoken());
    }

    abstract protected void setRequestHeaders(HttpRequestBase requestBase);

    public void setRequestUrl(URL requestUrl) {
        this.requestUrl = requestUrl;
    }
}
