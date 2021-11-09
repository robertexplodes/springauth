package at.flo.springauth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Basic";
    private String username;
    private String password;
    private List<String> roles;

    public JwtResponse(String jwt, String username, String password, List<String> roles) {
        this.token = jwt;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
