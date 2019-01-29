package be.pxl.business;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class CredentialsSerializer {

    private Path serializationPath;

    public CredentialsSerializer(Path serializationPath) {
        this.serializationPath = serializationPath;
    }

    public Credentials getCredentials() {
        Credentials apiCredentials;
        if (Files.exists(serializationPath)) {
            apiCredentials = readCredentials();
        } else {
            apiCredentials = askForCredentials();
            writeCredentials(apiCredentials);
        }
        return apiCredentials;
    }

    private Credentials askForCredentials() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the key ID of the api?");
        String apiKeyId = scanner.nextLine();

        System.out.println("What is the secret key of the api?");
        String apiSecretKey = scanner.nextLine();

        return new Credentials(apiKeyId, apiSecretKey);
    }

    private void writeCredentials(Credentials apiCredentials) {
        try (ObjectOutputStream credentialsWriter =
                new ObjectOutputStream(new FileOutputStream(serializationPath.toFile()))) {

            credentialsWriter.writeObject(apiCredentials);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private Credentials readCredentials() {
        try (ObjectInputStream credentialsReader =
                new ObjectInputStream(new FileInputStream(serializationPath.toFile()))) {
            Credentials apiCredentials = (Credentials) credentialsReader.readObject();

            return apiCredentials;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to read credentials from file, please enter them again.");
            askForCredentials();
        }
        return null; // should never happen
    }
}
