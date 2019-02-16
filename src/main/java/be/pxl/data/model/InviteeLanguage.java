package be.pxl.data.model;

/**
 * Decides in which language invitation emails are sent from CM.
 * Every API language option is available as enum value.
 */
public enum InviteeLanguage {
    DUTCH("nl-NL"),
    ENGLISH("en-US"),
    GERMAN("de-DE"),
    FRENCH("fr-FR");

    private String locale;

    private InviteeLanguage(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }
}
