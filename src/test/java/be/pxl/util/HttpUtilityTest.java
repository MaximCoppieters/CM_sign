package be.pxl.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class HttpUtilityTest {
    private HttpResponse createMockResponseWithStatus(int statusCode) {
        return new BasicHttpResponse(new BasicStatusLine(
                        new ProtocolVersion("HTTP", 2, 1),
                        statusCode, "error"));
    }

    @Test
    public void test_httpCallWasSuccesful_returnsFalseWhenResponseStatusCodeWasError() {
        HttpResponse badResponse = createMockResponseWithStatus(HttpStatus.SC_FORBIDDEN);

        boolean wasSuccessful = HttpUtility.apiCallWasSuccessful(badResponse);

        assertFalse(wasSuccessful);
    }

    @Test
    public void test_httpCallWasSuccesful_returnsTrueWhenResponseStatusCodeWasCreated() {
        HttpResponse succesResponse = createMockResponseWithStatus(HttpStatus.SC_CREATED);

        boolean wasSuccessful = HttpUtility.apiCallWasSuccessful(succesResponse);

        assertTrue(wasSuccessful);
    }

    @Test
    public void test_httpCallWasSuccesful_returnsTrueWhenResponseStatusCodeWasAccepted() {
        HttpResponse successResponse = createMockResponseWithStatus(HttpStatus.SC_ACCEPTED);

        boolean wasSuccessful = HttpUtility.apiCallWasSuccessful(successResponse);

        assertTrue(wasSuccessful);
    }

    @Test
    public void test_httpCallWasSuccesful_returnsTrueWhenResponseStatusCodeWasOk() {
        HttpResponse successResponse = createMockResponseWithStatus(HttpStatus.SC_OK);

        boolean wasSuccessful = HttpUtility.apiCallWasSuccessful(successResponse);

        assertThat(wasSuccessful, IsEqual.equalTo(true));
    }
}