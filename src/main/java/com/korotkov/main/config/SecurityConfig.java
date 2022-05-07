package com.korotkov.main.config;

import com.korotkov.main.security.MappedExceptionAuthFailureHandler;
import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    UserAccountService userAccountService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http
                .headers().frameOptions().disable()
                .xssProtection().disable()
                .contentTypeOptions().disable()
                .and()
                .csrf().disable()
                .antMatcher("/**")
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/security/registration").not().fullyAuthenticated()
                .antMatchers("/welcome").hasAnyRole(String.valueOf(UserRoleEnum.LEVEL_ONE),
                        String.valueOf(UserRoleEnum.LEVEL_TWO),String.valueOf(UserRoleEnum.LEVEL_THREE),
                        String.valueOf(UserRoleEnum.LEVEL_FOUR),String.valueOf(UserRoleEnum.LEVEL_FIVE))
                .antMatchers("/l").hasRole(String.valueOf(UserRoleEnum.LEVEL_TWO))
                .antMatchers("/security/**","/security/verification/**","/security/login/**","/security/login?error=true",
                        "/security/login/change-exp-password","/security/login?badcredentials=true",
                        "/security/login?locked=true","/security/login/not-confirmed-email","/res/**",
                        "/security/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/security/login")
                .defaultSuccessUrl("/")
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/security/logout")
                .logoutSuccessUrl("/")
                .and()
                .sessionManagement()
                .maximumSessions(1);


    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userAccountService).passwordEncoder(bCryptPasswordEncoder());
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return MappedExceptionAuthFailureHandler.builder()
                .setDefaultFailureUrl("/security/login?error=true")
                .addMapping(CredentialsExpiredException.class,"/security/login/change-exp-password")
                .addMapping(BadCredentialsException.class,"/security/login?badcredentials=true")
                .addMapping(LockedException.class,"/security/login?locked=true")
                .addMapping(DisabledException.class,"/security/login/not-confirmed-email")
                .addMapping(AccountExpiredException.class, "/security/subscription-expired")
                .build();
    }
}
