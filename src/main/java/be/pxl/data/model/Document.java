package be.pxl.data.model;

import java.time.LocalDateTime;

/**
 * The document as it was generated from our PDF file by CM's API. The id is used to refer to it using CM's API.
 * Once generated, we no longer need the original file on our file system.
 */
public class Document {
    private String id;
    private String name;
    private String hash;
    private LocalDateTime uploadDateTime;

    public Document() { }

    public Document(String id, String name, String hash, LocalDateTime uploadDateTime) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.uploadDateTime = uploadDateTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public LocalDateTime getUploadTime() {
        return uploadDateTime;
    }
}
