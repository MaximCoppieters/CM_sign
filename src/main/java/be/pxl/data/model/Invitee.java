package be.pxl.data.model;

import java.util.ArrayList;
import java.util.List;

public class Invitee {
    private String reference;
    private String name;
    private String email;
    private List<DocumentField> fields;

    public Invitee() {
        fields = new ArrayList<>();
    }

    public Invitee(String reference, String name, String email) {
        fields = new ArrayList<>();
        this.reference = reference;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addField(DocumentField field) {
        fields.add(field);
    }

    public List<DocumentField> getFields() {
        return fields;
    }
}
