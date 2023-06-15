package ch.bbw.pr.sospri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginLogoutController {

    @GetMapping("/login")
    public String login(){
        log.trace("TRACE: Login request receivede");
        log.debug("DEBUG: Login request received");
        log.info("INFO: Login request received");
        log.error("ERROR: Login request failed");
        log.warn("Warn: Login request requested.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        log.trace("TRACE: Login request receivede");
        log.debug("DEBUG: Logout request received");
        log.info("INFO: Logout request received");
        log.error("ERROR: Logout  failed");
        log.warn("Warn: Logout requested.");


        return "logout";
    }

    @PostMapping("/password/reset")
    public String resetPassword(){
        log.trace("TRACE: Password reset receivede");
        log.debug("DEBUG: Password reset request received");
        log.info("INFO: Password request received");
        log.error("ERROR: Password reset failed");
        log.warn("Warn: Logout requested.");
        return "resetpassword";
    }

    @GetMapping("/login/google")
    public String logingoogle() {
        return "redirect:/oauth2/authorization/google";
    }
    @GetMapping("/login/oauth2/code/google")
    public String oauth2Callback(@AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String accessToken = principal.getAttribute(OAuth2ParameterNames.ACCESS_TOKEN);
        // Do something with the user information and access token
        return "redirect:/";
    }

}
