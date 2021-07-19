package hb.details.hbuserdetails.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hb.details.hbuserdetails.constants.Role;
import hb.details.hbuserdetails.pojo.SignupRequest;

@RestController
@RequestMapping("/admin")
public class UserOnboardingController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/check")
	public String sayHi() {
		//String sql = "select username, password, enabled from users where username=?";
		String sql = "select password from users where username=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { "iman" }, String.class);
		//return " Hi ";
	}

	@PostMapping("/onboard")
	public String addUser(@RequestBody SignupRequest req) {
		String sql = "INSERT INTO users VALUES (?, ?, ?)";
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		String pass = b.encode(req.getPassword());
		jdbcTemplate.update(sql, new Object[] { req.getUsername(), pass, true });

		String authSql = "INSERT INTO authorities VALUES (?, ?)";
		Set<Role> s = req.getRole();
		jdbcTemplate.update(authSql, new Object[] { req.getUsername(), s });

		return "success";
	}
}
