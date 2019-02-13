package be.pxl.util;

import be.pxl.data.model.Document;
import be.pxl.util.Mapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Objects of this class map Documents to JSON, for use with the API
 */
public class DocumentMapper extends Mapper {
    private DateTimeFormatter uploadDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    public Document fromJson(String documentJsonString) throws IOException {
        JsonNode documentJson = objectMapper.readTree(documentJsonString);

        String id = documentJson.get("id").asText();
        String name = documentJson.get("name").asText();
        String hash = documentJson.get("hash").asText();
        LocalDateTime uploadDateTime = LocalDateTime
                .parse(documentJson.get("uploadDateTime").asText(), uploadDateTimeFormatter);

        return new Document(id, name, hash, uploadDateTime);
    }
}
