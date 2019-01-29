package be.pxl.data.domain;

import java.nio.file.Path;
import java.util.Date;

public class Document {
    private int id;
    private String name;
    private String hash;
    private Date uploadTime;

    public Document(int id, String name, String hash, Date uploadTime) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.uploadTime = uploadTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public Date getUploadTime() {
        return uploadTime;
    }
}
