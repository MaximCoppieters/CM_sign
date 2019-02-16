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

public abstract class PostRequestUnit<T> extends RequestUnit<T> {
    public PostRequestUnit(URL requestUrl) {
        super(requestUrl);
    }

    abstract protected HttpEntity createPostRequestBody(T bodyContent) throws UnsupportedEncodingException;

    public CloseableHttpResponse post(T body) throws URISyntaxException, IOException {
        assert requestUrl != null;

        CloseableHttpClient cmRestClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(requestUrl.toURI());
        setRequestHeaders(postRequest);

        HttpEntity pdfPostRequestBody = createPostRequestBody(body);
        postRequest.setEntity(pdfPostRequestBody);

        return cmRestClient.execute(postRequest);
    }
}
