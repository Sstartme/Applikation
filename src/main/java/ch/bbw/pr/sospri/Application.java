package ch.bbw.pr.sospri;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class Application {

   public static void main(String[] args) {

      SpringApplication.run(Application.class, args);

     /* Logger logger = LoggerFactory.getLogger(Application.class);
      logger.error("Something bad happened.");
      String json = "{ \"item\": \"Orange soda\", \"price\": 100.00 }";
      logger.info("Log message with structured logging " + json);
   }
*/
   }
}
