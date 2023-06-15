
package ch.bbw.pr.sospri;
import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private MemberService memberService;
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String lastName = oAuth2User.getAttribute("family_name");
        String firstName = oAuth2User.getAttribute("given_name");
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        Member existingMember = memberService.getByUserName(username);
        if (existingMember == null) {
            Member member = new Member();
            member.setPrename(firstName);
            member.setLastname(lastName);
            member.setUsername(username);
            member.setPassword("");
            member.setAuthority("member");
            member.setGoogleId(oAuth2User.getName());
            memberService.addMember(member);
        }
        updateAuthentication(token, oAuth2User, existingMember);
        response.sendRedirect("/get-channel");
    }

    private void updateAuthentication(OAuth2AuthenticationToken token, OAuth2User oAuth2User, Member existingMember) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (existingMember != null && existingMember.getAuthority() != null) {
            authorities.add(new SimpleGrantedAuthority(existingMember.getAuthority()));
        } else {
            authorities.add(new SimpleGrantedAuthority("member"));
        }
        Authentication newAuthentication = new OAuth2AuthenticationToken(oAuth2User, authorities, token.getAuthorizedClientRegistrationId());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}