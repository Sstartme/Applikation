package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    MemberService memberservice;

   /* @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("user");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}5678").roles("user","admin");

    }
    */

   @Bean
   public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberservice);
        return provider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.authenticationProvider(daoAuthenticationProvider());
    }
    protected void configure(HttpSecurity http) throws Exception{
        System.out.println("Using default configure(HttpSecurity)."+
                "If subclassed this will potentially override subclass configure(HttpSecurity).");

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                //Zugriff aud DB
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/get-channel").hasAnyAuthority("admin","ROLE_MEMBER","supervisor", "member")
                .antMatchers("get-register").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .and()
                .exceptionHandling().accessDeniedPage("/403.html")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
                .and().httpBasic()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**") //h2 runs in a frame, which chrome blocks per default, so we need to disable csrf protection by ignoring the h2-console
                .and()
                .headers().frameOptions().sameOrigin();

    }

}
