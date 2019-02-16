package be.pxl.data.model;

import java.util.List;

/**
 *  A dossier is a collection of documents with their assigned invitees along with the signing information
 *  The location on which the documents within a dossier need to be signed happens based on coordinates or a tag.
 *  With coordinates, an x and y coordinate ranging from 0.0 to 1.0 (start to end of document) is passed
 *  With tags, a String is specified to look for within the document, the invitee can sign at the location
 *  of this tag within the document. The tag text is allowed to be invisible (same color as background)
 */
public class Dossier {
    private String id;
    private String name;
    private List<Document> documents;
    private List<Invitee> invitees;

    public Dossier() { }

    public Dossier(String name, List<Document> documents, List<Invitee> invitees) {
        this.name = name;
        this.documents = documents;
        this.invitees = invitees;
    }

    public String getName() {
        return name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public List<Invitee> getInvitees() {
        return invitees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
