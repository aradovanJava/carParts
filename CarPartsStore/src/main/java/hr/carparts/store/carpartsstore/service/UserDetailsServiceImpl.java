package hr.carparts.store.carpartsstore.service;

import hr.carparts.store.carpartsstore.model.ApplicationRole;
import hr.carparts.store.carpartsstore.model.ApplicationUser;
import hr.carparts.store.carpartsstore.repository.SpringDataJpaApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private SpringDataJpaApplicationUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username);

        if(Optional.ofNullable(user).isEmpty()) {
            throw new UsernameNotFoundException("The user '" + username + "' does not exist!");
        }

        List<String> rolesString =  user.getRoles().stream()
                .map(ApplicationRole::getName)
                .toList();

        String[] rolesStringArray = rolesString.toArray(String[]::new);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(rolesStringArray)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
