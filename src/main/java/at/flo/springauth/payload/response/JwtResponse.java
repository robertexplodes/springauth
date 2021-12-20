package at.flo.springauth.payload.response;

import at.flo.springauth.domain.Order;
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
    private String email;
    private List<String> roles;
    private Collection<Order> orders;

    public JwtResponse(String jwt, String username, String email, List<String> roles, Collection<Order> orders) {
        this.token = jwt;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.orders = orders;
    }
}
