package be.pxl.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsUtility {

    public static URI API_ROOT_PATH;

    static {
        try {
            API_ROOT_PATH = new URI("https://api.cmdisp.com/sign/v1/");

            // API_ROOT_PATH = new URI("https://api.sandbox.cmdisp.com/sign/v1/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Path getPdfPath() {
        return Paths.get(System.getProperty("user.dir"))
                .resolve("resource")
                .resolve("generics_presentatie.pdf");
    }

    public static Path getCredentialsPath() {
        return Paths.get(System.getProperty("user.dir"))
                .resolve("resource")
                .resolve("credentials");
    }
}
