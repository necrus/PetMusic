package com.example.petmusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .antMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()

                .antMatchers("/bands", "/albums", "/tracks").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/crypt/**").hasAuthority("READ_PROFILE")
//                .anyRequest().authenticated();
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
        http
//                .httpBasic(); // браузерная форма
                .formLogin()    // страница авторизации
//                .loginPage("/login") // своя страница
//                .loginProcessingUrl("/login_custom_url") // куда будет пост-запрос с логином и паролем
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
        // remember the password that is printed out and use in the next step
//        System.out.println(encoder.encode("password"));

        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$12$wtmIbhC1ROyo0lIfEVxsjeyh0N1LNDoadl/gfo8RLMwP/2bDPUEbe")
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$wtmIbhC1ROyo0lIfEVxsjeyh0N1LNDoadl/gfo8RLMwP/2bDPUEbe")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource){
////        UserDetails user = User.withUsername("user")
////                .password("{bcrypt}$2a$12$wtmIbhC1ROyo0lIfEVxsjeyh0N1LNDoadl/gfo8RLMwP/2bDPUEbe")
////                .roles("USER")
////                .build();
////        UserDetails admin = User.withUsername("admin")
////                .password("{bcrypt}$2a$12$wtmIbhC1ROyo0lIfEVxsjeyh0N1LNDoadl/gfo8RLMwP/2bDPUEbe")
////                .roles("ADMIN", "USER")
////                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
////        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
////            jdbcUserDetailsManager.deleteUser(user.getUsername());
////        }
////        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
////            jdbcUserDetailsManager.deleteUser(admin.getUsername());
////        }
////        jdbcUserDetailsManager.createUser(admin);
////        jdbcUserDetailsManager.createUser(user);
//        return jdbcUserDetailsManager;
//    }

//    UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(userService);
//        return authenticationProvider;
//    }
}