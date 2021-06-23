package me.m92.tatbook_web.configuration;

import me.m92.tatbook_web.security.OffRedirectStrategy;
import me.m92.tatbook_web.security.jwt.JWTAuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    private static final RequestMatcher PUBLIC_ENDPOINTS = new OrRequestMatcher(
            new AntPathRequestMatcher("/authentication")
    );

    private static final RequestMatcher PRIVATE_ENDPOINTS = new NegatedRequestMatcher(PUBLIC_ENDPOINTS);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(utf8CharacterEncodingFilter(), CsrfFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PRIVATE_ENDPOINTS);

        http.authorizeRequests()
                .requestMatchers(PRIVATE_ENDPOINTS)
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

        LOGGER.info("Application's security has been configured");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().requestMatchers(PUBLIC_ENDPOINTS);
    }

    @Bean
    JWTAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JWTAuthenticationProcessingFilter filter = new JWTAuthenticationProcessingFilter(PRIVATE_ENDPOINTS);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return filter;
    }

    private CharacterEncodingFilter utf8CharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        return characterEncodingFilter;
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setRedirectStrategy(new OffRedirectStrategy());
        return handler;
    }

    @Bean
    FilterRegistrationBean omitAutoRegistrationOfJwtAuthenticationProcessingFilter(final JWTAuthenticationProcessingFilter jwtAuthenticationProcessingFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(jwtAuthenticationProcessingFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://localhost:3900"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }

    private AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(FORBIDDEN);
    }
}
