package kz.aitu.se2311.oopproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    // Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // PostgreSQL
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 64)
    @JsonIgnore
    private String password;

    @Column
    private Boolean enabled = true;

    // Relationships
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Collection<Role> roles;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Collection<Cart> carts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Order> orders;

    @ManyToMany
    @JsonIgnore
    private Collection<Company> companies;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private RefreshToken token;

    //Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

}
