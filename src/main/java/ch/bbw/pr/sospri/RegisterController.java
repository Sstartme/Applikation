package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Controller
public class RegisterController {
    Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    MemberService memberservice;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    ReCaptchaValidationService reCaptchaValidationService;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Valid @ModelAttribute("registerMember") RegisterMember registerMember, BindingResult bindingResult,
                                           @RequestParam(name = "g-recaptcha-response") String captcha, Model model) {
        //captcha
        if (!reCaptchaValidationService.validateCaptcha(captcha)) {
            logger.debug("Captcha fails");
            logger.error("Captcha failure: Captcha was not correct!");
            return "register";
        }
        logger.debug("Captcha is correct!");

        // Validate confirmation and username
        if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
            bindingResult.rejectValue("confirmation", "error.registerMember", "Password and confirmation do not match.");
        }
        String username = registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase();
        Member existingMember = memberservice.getByUserName(username);
        if (existingMember != null) {
            bindingResult.rejectValue("prename", "error.registerMember", "This username already exists.");
        }
        // If there are any validation errors, show the form again with error messages
        if (bindingResult.hasErrors()) {
            return "register";
        }
        // Create a new member and save it to the database
        Member newMember = new Member();
        newMember.setPrename(registerMember.getPrename());
        newMember.setLastname(registerMember.getLastname());
        newMember.setUsername(username);
        newMember.setPassword(encryptionService.bCryptPasswordEncoder(registerMember.getPassword()));
        memberservice.addMember(newMember);
        // Show a confirmation page
        model.addAttribute("username", username);
        return "registerconfirmed";
    }
}