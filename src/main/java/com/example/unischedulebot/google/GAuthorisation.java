package com.example.unischedulebot.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.Getter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Getter
public class GAuthorisation {
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public Credential getCredentials(List<String> SCOPES) throws IOException {
        return GoogleCredential
                .fromStream(Objects.requireNonNull(GAuthorisation.class.getResourceAsStream("/credentials.json")))
                .createScoped(SCOPES);
    }
}
