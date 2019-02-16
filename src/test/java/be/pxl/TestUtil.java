package be.pxl;

public class TestUtil {
    public static String formJsonField(String parameter, String value) {
        return "\""+ parameter + "\":\"" + value + "\"";
    }

    public static String formJsonFieldWithComma(String parameter, String value) {
        return formJsonField(parameter, value) + ",";
    }
}
