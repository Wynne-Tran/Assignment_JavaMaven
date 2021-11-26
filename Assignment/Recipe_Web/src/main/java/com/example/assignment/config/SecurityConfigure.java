/* ********************************************************************************
* Project: Create a Recipe Project Using Spring/Spring Boot
        * Assignment: 1
        * Author(s): Wynne Tran
        * Student Number: 101161665
        * Date: Nov 4 2021
        * Description: This page for configuration that allow user access html page, h2-console and PasswordEncoder....
******************************************************************************** */


package com.example.assignment.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true, prePostEnabled=true)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email as principal, password as credentials, true from users where email=?")
                .authoritiesByUsernameQuery("select user_email as principal, role_name as role from user_roles where user_email=?")
                .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO Auto-generated method stub
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/register", "/", "/forgotpassword", "/login",  "/css/**/**", "/css/**", "/image/*", "/webjars/**, /console/**").permitAll()
                .antMatchers("/profile").hasAnyRole("USER, ADMIN")
                .antMatchers(
                        "/viewrecipe", "/createrecipe", "/planrecipe",
                        "/search", "/Calendar", "/deleteFavRecipe", "/editprofile",
                        "/viewRecipes", "/viewFavorites", "viewplans",
                        "/addplan",  "/editplan", "/editplan/*",
                         "/viewingredient", "/viewingredient/*",
                        "/editingredient", "/editingredient/*",
                        "/addCart", "view_cart", "deleteCart",
                        "/changepassword").hasAnyRole("USER, ADMIN") //hasRole("ADMIN")
                .and().formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/")
                ;
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

}

