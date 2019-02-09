package be.pxl.data.model;

public class DocumentField {
    private String type;
    private String documentId;
    private String range;
    private SignDimensions signDimensions;
    private String tag;

    public DocumentField() {}

    public DocumentField(String type, String documentId, String range, SignDimensions signDimensions, String tag) {
        this.type = type;
        this.documentId = documentId;
        this.range = range;
        this.signDimensions = signDimensions;
        this.tag = tag;
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
