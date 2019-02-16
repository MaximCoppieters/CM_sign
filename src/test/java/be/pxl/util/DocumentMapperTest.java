package be.pxl.util;

import be.pxl.TestUtil;
import be.pxl.business.CmSignException;
import be.pxl.data.model.Document;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DocumentMapperTest {
    private DocumentMapper documentMapper = new DocumentMapper();

    private String documentId = "8d817359-7eb8-4b6e-9030-17ac286b7dc0";
    private String documentName = "Purchase contract 2018 v12.pdf";
    private String documentHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private String documentUploadedTimeString = "2018-11-02T12:51:56.638Z";
    private String contentTypeString = "application/pdf";

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private LocalDateTime documentTimeUploaded = LocalDateTime.parse(documentUploadedTimeString, dateTimeFormatter);

    private Document initializeTestDocument() {
        return new Document(documentId, documentName, documentHash, documentTimeUploaded);
    }

    @Test(expected = CmSignException.class)
    public void test_fromJson_passingInvalidJsonStringThrowsCmSignException() {
        String jsonWithMissingCloseBracket = "{ " + TestUtil.formJsonField("id", documentId);

        documentMapper.fromJson(jsonWithMissingCloseBracket);
    }

    @Test
    public void test_fromJson_mappedDocumentHasRelevantFields() {
        String documentJson = "{" +
                TestUtil.formJsonFieldWithComma("id", documentId) +
                TestUtil.formJsonFieldWithComma("name", documentName) +
                TestUtil.formJsonFieldWithComma("hash", documentHash) +
                TestUtil.formJsonFieldWithComma("uploadDateTime", documentUploadedTimeString) +
                TestUtil.formJsonField("contentType", contentTypeString) +
                "}";


        Document document = documentMapper.fromJson(documentJson);

        assertEquals(document.getId(), documentId);
        assertEquals(document.getName(), documentName);
        assertEquals(document.getHash(), documentHash);
        assertEquals(document.getUploadTime(), documentTimeUploaded);
    }

}