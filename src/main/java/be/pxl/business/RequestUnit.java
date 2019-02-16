package be.pxl.business;

import org.apache.http.client.methods.HttpRequestBase;

import java.net.URL;


/**
 * Classes that inherit RequestUnit gain HTTP functionality in the form of header
 * authentication (eg. JSON Web Token) and the ability to set an URL endpoint.
 */
public abstract class RequestUnit {
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
