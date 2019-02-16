package be.pxl.util;

import be.pxl.business.CmSignException;
import be.pxl.data.model.DocumentField;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentFieldMapperTest {
    private DocumentFieldMapper documentFieldMapper = new DocumentFieldMapper();

    @Test(expected = CmSignException.class)
    public void test_toJsonNode_throwsCmSignExceptionWhenDocumentFieldIsNull() {
        documentFieldMapper.toJsonNode(null);
    }

    @Test
    public void test_toJsonNode_signatureBasedDocumentFieldJsonHasRelevantFields() {
        DocumentField documentField = new DocumentField
                ("signature", "8d817359-7eb8-4b6e-9030-17ac286b7dc0", "{signature1}");

        JsonNode documentFieldJson = documentFieldMapper.toJsonNode(documentField);

        assertEquals("signature", documentFieldJson.get("type").asText());
        assertEquals("8d817359-7eb8-4b6e-9030-17ac286b7dc0", documentFieldJson.get("file").asText());
        assertEquals("{signature1}", documentFieldJson.get("tag").asText());
    }

}