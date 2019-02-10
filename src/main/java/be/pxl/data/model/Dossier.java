package be.pxl.data.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Dossier {
    private String id;
    private String name;
    private List<Document> files;
    private List<Invitee> invitees;

    public Dossier() { }

    public Dossier(String name, List<Document> files, List<Invitee> invitees) {
        this.name = name;
        this.files = files;
        this.invitees = invitees;
    }

    public String getName() {
        return name;
    }

    public List<Document> getFiles() {
        return files;
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
