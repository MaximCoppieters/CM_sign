package be.pxl.business;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class GetRequestUnit extends RequestUnit<String> {
    public GetRequestUnit(URL requestUrl) {
        super(requestUrl);
    }

    public CloseableHttpResponse get() throws URISyntaxException, IOException {
        CloseableHttpClient cmRestClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(requestUrl.toURI());
        setRequestHeaders(getRequest);

        return cmRestClient.execute(getRequest);
    }
}
