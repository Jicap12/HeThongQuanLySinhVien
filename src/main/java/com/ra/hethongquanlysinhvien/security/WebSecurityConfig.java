package com.ra.hethongquanlysinhvien.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(authenticationProvider()) // Sử dụng provider để xác thực
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF cho đơn giản trong giai đoạn dev

                .authorizeHttpRequests(auth -> auth
                        // Các URL cho phép truy cập tự do
                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()

                        // Chỉ user đăng nhập mới vào được /dashboard
                        .requestMatchers("/dashboard").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUB_ADMIN")

                        // Chỉ ROLE_ADMIN được phép truy cập trang admin
                        .requestMatchers("/course/**", "/class/**", "/student/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUB_ADMIN","ROLE_TEACHER")
                        .requestMatchers("/account/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUB_ADMIN")

                        // Các yêu cầu khác cũng cần xác thực
                        .anyRequest().authenticated()
                )

                // Cấu hình form login
                .formLogin(login -> login
                        .loginPage("/login") // Trang hiển thị form đăng nhập
                        .loginProcessingUrl("/login") // URL xử lý form POST
                        .usernameParameter("username") // Tên field username
                        .passwordParameter("password") // Tên field password
                        .defaultSuccessUrl("/dashboard", true) // Redirect sau đăng nhập thành công
                        .failureUrl("/login?error") // Redirect nếu đăng nhập sai
                )

                // Cấu hình logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .build();
    }

    // Bean chịu trách nhiệm xác thực dựa vào userDetailsService + encoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Nạp thông tin user từ DB
        provider.setPasswordEncoder(passwordEncoder());     // So sánh mật khẩu đã mã hóa
        return provider;
    }

    // Sử dụng BCrypt để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
