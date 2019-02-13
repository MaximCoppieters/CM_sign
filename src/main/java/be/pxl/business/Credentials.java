package be.pxl.business;

import be.pxl.util.PathsUtility;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.Scanner;

/**
 * The CM sign API requires the usage of a JSON Web Token header to authenticate
 * and track billing. An object of this class holds the information required.
 * As the credentials shouldn't change while running the application, the
 * class is implemented using the Singleton pattern, so that the same object
 * is always returned.
 */
public class Credentials implements Serializable {
    private String cmKeyId;
    private String cmSecretKey;
    private String cmJsonWebtoken;

    private static Credentials instance;

    {
        CredentialsSerializer credentialsSerializer = new CredentialsSerializer(PathsUtility.getCredentialsPath());
        instance = credentialsSerializer.getCredentials();
    }

    private Credentials(String cmKeyId, String cmSecretKey) {
        this.cmKeyId = cmKeyId;
        this.cmSecretKey = cmSecretKey;

        cmJsonWebtoken = generateJsonWebToken();
    }

    private String generateJsonWebToken() {
        JwtBuilder jwtBuilder = Jwts.builder();

        Date issueDate = generateIssueDate();

        return jwtBuilder.setHeaderParam("kid", cmKeyId)
                .setIssuedAt(issueDate)
                .setExpiration(generateExpirationDate(issueDate))
                .setNotBefore(generateNotBeforeDate(issueDate))
                .signWith(SignatureAlgorithm.HS256, cmSecretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    private Date generateExpirationDate(Date issueDate) {
        return Date.from(issueDate.toInstant().plus(Period.ofDays(30)));
    }

    private Date generateIssueDate() {
        return Date.from(Instant.now());
    }

    private Date generateNotBeforeDate(Date issueDate) {
        return Date.from(issueDate.toInstant());
    }

    public static Credentials getInstance() {
        return instance;
    }

    public String getCmJsonWebtoken() {
        return cmJsonWebtoken;
    }

    private class CredentialsSerializer {

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
                LogManager.getLogger(CredentialsSerializer.class.getName())
                        .error("Couldn't create credentials file");
            }
        }

        private Credentials readCredentials() {
            try (ObjectInputStream credentialsReader =
                         new ObjectInputStream(new FileInputStream(serializationPath.toFile()))) {
                Credentials apiCredentials = (Credentials) credentialsReader.readObject();

                return apiCredentials;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to read CM Sign API Credentials from file, please enter them again.");
                return askForCredentials();
            }
        }
    }
}
