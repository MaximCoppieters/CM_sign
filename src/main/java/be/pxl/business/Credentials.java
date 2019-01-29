package be.pxl.business;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

public class Credentials implements Serializable {
    private String cmKeyId;
    private String cmSecretKey;
    private String cmJsonWebtoken;

    public Credentials(String cmKeyId, String cmSecretKey) {
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

    public String getCmKeyId() {
        return cmKeyId;
    }

    public String getCmSecretKey() {
        return cmSecretKey;
    }

    public String getCmJsonWebtoken() {
        return cmJsonWebtoken;
    }
}
