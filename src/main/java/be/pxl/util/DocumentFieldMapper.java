package be.pxl.util;

import be.pxl.business.CmSignException;
import be.pxl.data.model.DocumentField;
import be.pxl.data.model.SignDimensions;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;

public class DocumentFieldMapper extends Mapper {
    public JsonNode toJsonNode(DocumentField field) {
        throwIfInvalid(field);

        ObjectNode fieldJson = objectMapper.createObjectNode();

        fieldJson.put("type", field.getType());
        fieldJson.put("file", field.getDocumentId());

        if (hasSignatureTag(field)) {
            fieldJson.put("tag", field.getTag());
        } else {
            fieldJson.put("range", field.getRange());
            ObjectNode signDimensionsJson = createDimensionsJsonNode(field.getSignDimensions());
            fieldJson.set("dimensions", signDimensionsJson);
        }

        return fieldJson;
    }

    private boolean hasSignatureTag(DocumentField field) {
        return field.getType().equals("signature");
    }

    private void throwIfInvalid(DocumentField field) {
        if (field == null) {
            throw new CmSignException("DocumentField passed to DocumentFieldMapper was null");
        }
    }

    private ObjectNode createDimensionsJsonNode(SignDimensions signDimensions) {
        ObjectNode dimensionsJson = objectMapper.createObjectNode();

        ObjectNode originJson = createOriginJsonNode(signDimensions.getOrigin());
        dimensionsJson.set("origin", originJson);

        ObjectNode sizeJson = createSizeJsonNode(signDimensions.getWidth(), signDimensions.getHeight());
        dimensionsJson.set("size", sizeJson);

        return dimensionsJson;
    }

    private ObjectNode createOriginJsonNode(Point origin) {
        ObjectNode sizeJson = objectMapper.createObjectNode();
        sizeJson.put("x", origin.x);
        sizeJson.put("y", origin.y);
        return sizeJson;
    }

    private ObjectNode createSizeJsonNode(int width, int height) {
        ObjectNode sizeJsonNode = objectMapper.createObjectNode();
        sizeJsonNode.put("width", width);
        sizeJsonNode.put("height", height);
        return sizeJsonNode;
    }
}
