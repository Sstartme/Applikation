package ch.bbw.pr.sospri;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ChannelsController
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Controller
public class ChannelsController {
   @Autowired
   MessageService messageservice;
   @Autowired
   MemberService memberservice;

   @GetMapping("/get-channel")
   public String getRequestChannel(Model model) {
      System.out.println("getRequestChannel");
      model.addAttribute("messages", messageservice.getAll());

      Message message = new Message();
      message.setContent("Der zweite Pfeil trifft immer.");
      System.out.println("message: " + message);
      model.addAttribute("message", message);
      return "channel";
   }

   @PostMapping("/add-message")
   public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult, Authentication authentication) {
      System.out.println("postRequestChannel(): message: " + message.toString());
      if (bindingResult.hasErrors()) {
         System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
         model.addAttribute("messages", messageservice.getAll());
         return "channel";
      }
      // Hack solange es kein authenticated member hat

      message.setAuthor(authentication.getName());
      message.setOrigin(new Date());
      System.out.println("message: " + message);
      messageservice.add(message);

      return "redirect:/get-channel";
   }
   @GetMapping("/edit-message")
   public String editMessage(@RequestParam(name = "id", required = true) long id, Model model) {
      Message message = messageservice.getById(id);
      System.out.println("editMessage get: " + message);
      model.addAttribute("message", message);
      return "editmessage";
   }

   @PostMapping("/edit-message")
   public String editMessage(Message message, Model model) {
      System.out.println("editMessage post: edit message" + message);
      Message value = messageservice.getById(message.getId());
      value.setContent(message.getContent());
      messageservice.update(message.getId(), value);
      return "redirect:/get-channel";
   }

   @GetMapping("/delete-message")
   public String deleteMessage(@RequestParam(name = "id", required = true) long id, Model model) {
      System.out.println("deleteMessage: " + id);
      messageservice.deleteById(id);
      return "redirect:/get-channel";
   }
}
