package ch.bbw.pr.sospri.message;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A chat message
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
public class Message {
   @Id
   @GeneratedValue(generator = "generatorMessage", strategy = GenerationType.SEQUENCE)
   @SequenceGenerator(name = "generatorMessage", initialValue = 10)
   private Long id;

   @NotEmpty(message = "content may not be empty")
   @Size(min = 2, max = 512, message = "Die Länge der Message muss 2 bis 512 Zeichen sein.")
   private String content;

   @NotEmpty (message = "author may not be empty" )
   @Size(min = 5, max = 20, message = "Die Länge des Autors 2 bis 20 Zeichen sein.")
   private String author;

   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date origin;
}
