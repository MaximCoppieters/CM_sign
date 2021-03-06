package be.pxl.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is responsable for giving us access to specific paths within the project.
 */
public class PathsUtility {
    public static URI API_ROOT_PATH;

    static {
        try {
            // Uncomment for Production API
/*            API_ROOT_PATH = new URI("https://api.cmdisp.com/sign/v1/");*/

            // Uncomment for Sandbox Api
            API_ROOT_PATH = new URI("https://api.sandbox.cmdisp.com/sign/v1/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Path getPdfPath() {
        return Paths.get(System.getProperty("user.dir"))
                .resolve("resource")
                .resolve("dummy.pdf");
    }

    public static Path getCredentialsPath() {
        return Paths.get(System.getProperty("user.dir"))
                .resolve("resource")
                .resolve("credentials");
    }
}
