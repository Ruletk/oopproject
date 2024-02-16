package kz.aitu.se2311.oopproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;


    @Override
    public String getAuthority() {
        return getName();
    }
}
