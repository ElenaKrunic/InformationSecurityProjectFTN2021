package uns.ac.rs.ib.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import uns.ac.rs.ib.security.security.auth.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService; 
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint; 
	
	@Autowired
	private void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
	    AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
	    authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
	    return authenticationTokenFilter;
	 }
	  
	//tacka 6.1: 
		//korisnici koji nisu autentifikovati nemaju prava pristupa ni jednoj stranici, osim login i register
		//za register cu morati staviti register sister, register doctor i ostalo 
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	      http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
	      		.and().authorizeRequests().antMatchers("/api/users/login").permitAll()
	      		.antMatchers("api/users/register-clinic-center-admin").permitAll()
	      		.antMatchers("api/users/register-clinic-admin").permitAll()
	      		.antMatchers("api/users/register-doctor").permitAll()
	      		.antMatchers("api/users/register-nurse").permitAll()
	      		.antMatchers("api/users/register-patient").permitAll();
	      		 //.antMatchers("/**").permitAll().
	      		//.anyRequest().authenticated();

	      http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	  }
}
