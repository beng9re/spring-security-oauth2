package nextstep.security.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubOauthRequest(
        @JsonProperty("client_id")
        String clientId,

        @JsonProperty("client_secret")
        String clientSecret,

        String code,
        @JsonProperty("redirect_uri")
        String redirectUri
) {
}
