package be.pxl.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Mapper {
    protected final ObjectMapper objectMapper;

    public Mapper() {
        this.objectMapper = new ObjectMapper();
    }
}
