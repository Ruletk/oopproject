package kz.aitu.se2311.oopproject.auth;

import jakarta.transaction.Transactional;
import kz.aitu.se2311.oopproject.entities.Role;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " was not found."));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(false) // TODO: Make account verification
                .authorities(getGrantedAuthorities(user))
                .build();
    }


    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (Role role : roles)
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        return authorities;
    }
}
