package uk.co.datadisk.ddrolodex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uk.co.datadisk.ddrolodex.jwt.JWTTokenProvider;
import uk.co.datadisk.ddrolodex.jwt.filters.JWTAccessDeniedHandler;
import uk.co.datadisk.ddrolodex.jwt.filters.JWTAuthenticationEntryPoint;
import uk.co.datadisk.ddrolodex.jwt.filters.JWTAuthorizationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;
import static uk.co.datadisk.ddrolodex.constants.SecurityConstant.PUBLIC_URLS;
import static uk.co.datadisk.ddrolodex.constants.SecurityConstant.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JWTAuthorizationFilter jwtAuthorizationFilter;
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter,
                          JWTAccessDeniedHandler jwtAccessDeniedHandler,
                          JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          @Qualifier("userDetailsService") UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(IF_REQUIRED)
                .and()
                .authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureUrl("/login?error")
                    .successHandler(successHandler())
                .and()
                .logout()
                    .permitAll()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RolodexSuccessHandler successHandler() {
        return new RolodexSuccessHandler();
    }
}
