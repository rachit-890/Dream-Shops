package org.dailycodework.dreamshops.security.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.model.User;
import org.dailycodework.dreamshops.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException("User not found "));
        return ShopUserDetails.buildUserDetails(user);
    }
}
