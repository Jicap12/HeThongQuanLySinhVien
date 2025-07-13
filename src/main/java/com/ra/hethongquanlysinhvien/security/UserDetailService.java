package com.ra.hethongquanlysinhvien.security;

import com.ra.hethongquanlysinhvien.model.entity.User;
import com.ra.hethongquanlysinhvien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        UserPrinciple userPrinciple = new UserPrinciple();
        userPrinciple.setUser(user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));
        userPrinciple.setAuthorities(user.get().getRoles().
                stream().map(role ->
                        new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet()));
        return userPrinciple;
    }
}
