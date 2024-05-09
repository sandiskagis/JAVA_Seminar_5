package lv.venta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySpringSecurityConfig {
	
	@Bean
	public UserDetailsManager createTestUser() {
		PasswordEncoder encoder =
			    PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails u1Details = 
				User
				.builder()
				.username("admin")
				.password(encoder.encode("admin"))
				.authorities("ADMIN")
				.build();
		
		UserDetails u2Details = 
				User
				.builder()
				.username("karina")
				.password(encoder.encode("123"))
				.authorities("USER")
				.build();
		
		return new InMemoryUserDetailsManager(u1Details, u2Details);
		
	}
	
	@Bean
	public SecurityFilterChain configureEndpoints(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/hello").permitAll()
				.requestMatchers("/hello/msg").permitAll()
				.requestMatchers("/error").permitAll()
				.requestMatchers("/product/all").hasAnyAuthority("USER", "ADMIN")
				.requestMatchers("/product/one**").hasAnyAuthority("USER", "ADMIN")
				.requestMatchers("/product/all/**").hasAnyAuthority("USER", "ADMIN")
				.requestMatchers("/product/insert").hasAuthority("ADMIN")
				.requestMatchers("/product/update**").hasAuthority("ADMIN")
				.requestMatchers("/product/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/product/filter/**").hasAnyAuthority("USER", "ADMIN")
				);
		
		http.formLogin().permitAll();
		
		
		return http.build();
		
	}
	
	

}
