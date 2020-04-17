package com.wejoy.wejoycms.config;

import com.wejoy.wejoycms.service.MyUserService;
import com.wejoy.wejoycms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    MyUserService myUserService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("wejoyTec").password(passwordEncoder().encode("WejoyTechnology")).roles("superAdmin");
        auth.userDetailsService(myUserService);
        auth.userDetailsService(myUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/security/**").hasRole("superAdmin")
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
                .failureUrl("/login").defaultSuccessUrl("/security/banner")
                .and().headers().frameOptions().disable()
                .and().logout().logoutUrl("/security/logout").logoutSuccessUrl("/web/login").invalidateHttpSession(true)
                .and().csrf().disable();
    }
}
