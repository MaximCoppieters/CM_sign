package be.pxl.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An invitee is a person that can be invited to sign a document. The invitation happens using the person's
 * email address. The id is used to refer to the invitee on CM's API.
 * May have to be rewritten to use builder pattern.
 */
public class Invitee {
    /**
     * Id: Field that is used to assign a field to a particular invitee
     * optional if the all invitees use the same field.
     */
    private String id;
    private String name;
    private String email;
    private List<DocumentField> fields;
    /**
     * If no language is set, invitations will be sent in English
     */
    private InviteeLanguage language;

    public Invitee() {
        fields = new ArrayList<>();
    }

    /**
     * Use this constructor if the all invitees use the same sign location
     * And if the language of invitations sent should be in Dutch
     */
    public Invitee(String name, String email) {
        this("", name, email);
    }

    /**
     * Use this constructor if the all invitees use a different sign location
     * And if the language of invitations sent should be in Dutch
     */
    public Invitee(String id, String name, String email) {
        this(InviteeLanguage.DUTCH, id, name, email);
    }

    /**
     * Use this constructor if all the invitees use the same sign location
     * And if the language of invitations sent should be different from Dutch
     */
    public Invitee(InviteeLanguage language, String name, String email) {
        this(language, null, name, email);
    }

    /**
     * Use this constructor if the invitees use a different sign location
     * And if the language of invitations sent should be different from Dutch
     */
    public Invitee(InviteeLanguage language, String id, String name, String email) {
        fields = new ArrayList<>();
        this.language = language;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Todo: validate email address?
    public void setEmail(String email) {
        this.email = email;
    }

    public void addField(DocumentField field) {
        fields.add(field);
    }

    public InviteeLanguage getLanguage() {
        return language;
    }

    public List<DocumentField> getFields() {
        return fields;
    }
}
