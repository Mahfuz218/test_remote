package com.example.search_app.capston.services;

import com.example.search_app.capston.models.Users;
import com.example.search_app.capston.repositories.UsersRepository;
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
import java.util.Optional;

/**
 * @author Touhid Hossain
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findByUsernameEqualsIgnoreCase(username);
        if(optionalUsers.isEmpty()){
            optionalUsers = usersRepository.findByEmailEqualsIgnoreCase(username);
        }
        Users users = optionalUsers.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(users);
    }

    static class CustomUserDetails extends Users implements UserDetails{

        public CustomUserDetails(Users users) {
            super(users);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            super.getRoleList().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
            return grantedAuthorities;
        }
    }
}
