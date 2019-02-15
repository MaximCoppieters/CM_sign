package be.pxl.data.model;

public class DocumentField {
    private String type;
    private String documentId;
    private String range;
    private SignDimensions signDimensions;
    private String tag;

    /**
     * Use this Constructor to create a Sign field using a tag (word within document that indicates location)
     */
    public DocumentField(String type, String documentId, String tag) {
        this.type = type;
        this.documentId = documentId;
        this.tag = tag;
    }

    /**
     * Use this constructor to create a Sign field using dimensions
     */
    public DocumentField(String type, String documentId, String range, SignDimensions signDimensions) {
        this.type = type;
        this.documentId = documentId;
        this.range = range;
        this.signDimensions = signDimensions;
    }

    public String getType() {
        return type;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getRange() {
        return range;
    }

    public SignDimensions getSignDimensions() {
        return signDimensions;
    }

    public String getTag() {
        return tag;
    }
}
