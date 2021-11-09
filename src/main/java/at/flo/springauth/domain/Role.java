package at.flo.springauth.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name ="roles")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    public Role() {
    }

    public Role(Roles role) {
        this.name = role;
    }
}