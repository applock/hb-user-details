package hb.details.hbuserdetails.pojo;

import java.util.Set;

import javax.validation.constraints.*;

import hb.details.hbuserdetails.constants.Role;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Role role;
    
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Role getRole() {
      return this.role;
    }
    
    public void setRole(Role role) {
      this.role = role;
    }
}
