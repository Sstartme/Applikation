package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**

 Service to reset user's password
 */
@Service
@Transactional
public class PasswordResetService {
    @Autowired
    public MemberRepository memberRepository;

    public void resetPassword(String username, String oldPassword) throws InvalidPasswordException {
// Find user by username
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidPasswordException("Invalid username"));

// Validate old password
        if (!member.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException("Invalid old password");
        }

// Set new password
        String newPassword = "";
        member.setPassword(newPassword);
        memberRepository.save(member);
    }
}





