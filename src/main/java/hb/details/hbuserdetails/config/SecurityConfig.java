package hb.details.hbuserdetails.config;

import static hb.details.hbuserdetails.constants.SecurityConstants.ADMIN_ROLE;
import static hb.details.hbuserdetails.constants.SecurityConstants.ADMIN_URL;
import static hb.details.hbuserdetails.constants.SecurityConstants.CLIENT_ROLE;
import static hb.details.hbuserdetails.constants.SecurityConstants.CLIENT_URL;
import static hb.details.hbuserdetails.constants.SecurityConstants.COACH_ROLE;
import static hb.details.hbuserdetails.constants.SecurityConstants.COACH_URL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource datasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(datasource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, authority from authorities where username=?")
				.passwordEncoder(customPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(ADMIN_URL).hasRole(ADMIN_ROLE).antMatchers(COACH_URL)
				.hasAnyRole(COACH_ROLE, ADMIN_ROLE).antMatchers(CLIENT_URL).hasAnyRole(CLIENT_ROLE, ADMIN_ROLE)
				.antMatchers("/").permitAll().and().formLogin();
	}

	@Bean
	public PasswordEncoder customPasswordEncoder() {
		return new BCryptPasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(10));
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
			}
		};
	}

}
