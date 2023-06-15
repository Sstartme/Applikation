package ch.bbw.pr.sospri.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

/**
 * To regist a new Member
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Getter
@Setter
@ToString
public class RegisterMember {

   @NotEmpty(message = "prename is required" )
   @Size(min = 2, max = 50, message = "prename must be between 2 and 50 characters")
   private String prename;

   @NotEmpty(message = "lastname is required" )
   @Size(min = 2, max = 50, message = "lastname must be between 2 and 50 characters")
   private String lastname;

   @NotEmpty(message = "password is required" )
  /* @Pattern.List({
           @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter"),
           @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter"),
           @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
           @Pattern(regexp = ".*[@#$%^&+=].*", message = "Password must contain at least one special character (@#$%^&+=)"),
           @Pattern(regexp = "^.{8,}$", message = "Password must be at least 8 characters long"),
           @Pattern(regexp = "^.{0,20}$", message = "Password must be at most 20 characters long"),
           @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,20}$", message = "Password must contain a special character")
   })*/
  @Size(min = 8, max = 50, message = "password must be between 8 and 50 characters")
   private String password;

   @NotEmpty(message = "confirmation is required" )
   @EqualsAndHashCode.Exclude
   private String confirmation;
   //Passwort und Confirmation wird geprüft ob sie gleich sind.

   @AssertTrue(message = "password and confirmation must match")
   public boolean isPasswordConfirmed() {
      return password != null && password.equals(confirmation);
   }

   public String getUsername() {
      return (prename + "." + lastname).toLowerCase();
   }

}
