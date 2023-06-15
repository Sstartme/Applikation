package ch.bbw.pr.sospri;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * A chat-member
 *
 * @author Peter Rutschmann
 * @version 06.04.2023
 */


@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {
    private String site;
    private String secret;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        System.out.println("secret " + secret);
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}


