package be.pxl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePaths {
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
    public static void printStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
