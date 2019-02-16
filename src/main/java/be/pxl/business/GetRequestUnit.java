package be.pxl.business;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Classes that inherit from this class gain HTTP GET request functionality.
 */
public abstract class GetRequestUnit extends RequestUnit<String> {
    public CloseableHttpResponse get(URL getUrl) throws URISyntaxException, IOException {
        CloseableHttpClient cmRestClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(requestUrl.toURI());
        setRequestHeaders(getRequest);

        return cmRestClient.execute(getRequest);
    }
}
