package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.api.user.authentication.CredentialsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER;

    private static final RequestMatcher PUBLIC_REQUEST_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/signup/**"),
                                                                                    new AntPathRequestMatcher("/authentication/**"),
                                                                                        new AntPathRequestMatcher("/apiauth"),
                                                                                        new AntPathRequestMatcher("/error/**"));

    private static final RequestMatcher PUBLIC_AUTHENTICATION_REQUEST_URL = new AntPathRequestMatcher("/authentication");

    private static final RequestMatcher PRIVATE_REQUEST_URLS = new NegatedRequestMatcher(PUBLIC_REQUEST_URLS);

    private static final RequestMatcher PRIVATE_TATTOOIST_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/configure/tattooist/**")
    );

    private static final RequestMatcher PRIVATE_CLIENT_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/configure/client/**")
    );

    private CharacterEncodingFilter characterEncodingFilter;

    private JwtTokenService jwtTokenService;

    private JwtAuthenticationService jwtAuthenticationService;

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private CredentialsValidator credentialsValidator;

    static {
        LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
    }

    @Autowired
    public SecurityConfiguration(@Qualifier("character.encoding.utf8") CharacterEncodingFilter characterEncodingFilter,
                                 JwtTokenService jwtTokenService,
                                 JwtAuthenticationService jwtAuthenticationService,
                                 CredentialsValidator credentialsValidator) {
        this.characterEncodingFilter = characterEncodingFilter;
        this.jwtTokenService = jwtTokenService;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtAuthenticationService);
        this.credentialsValidator = credentialsValidator;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PUBLIC_REQUEST_URLS);
    }

    @PostConstruct
    private void log() {
        LOGGER.info("Security configuration is up and running");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(characterEncodingFilter, CsrfFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), AnonymousAuthenticationFilter.class)
                .addFilterBefore(primaryAuthenticationFilter(), JwtAuthenticationProcessingFilter.class)
                .addFilterAfter(jwtCookieAuthenticationFilter(), AnonymousAuthenticationFilter.class);

        http.sessionManagement()
                .sessionCreationPolicy(STATELESS);

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PRIVATE_REQUEST_URLS);

        http.authorizeRequests()
                .requestMatchers(PRIVATE_REQUEST_URLS)
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

        http.authorizeRequests()
                .requestMatchers(PRIVATE_TATTOOIST_URLS).hasRole("TATTOOIST")
                .requestMatchers(PRIVATE_CLIENT_URLS).hasRole("CLIENT");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() throws Exception {
        JwtAuthenticationProcessingFilter filter = new JwtAuthenticationProcessingFilter(PRIVATE_REQUEST_URLS, jwtTokenService);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setRedirectStrategy(new OffRedirectStrategy());
        return handler;
    }

    @Bean
    JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter() {
        JwtCookieAuthenticationFilter filter = new JwtCookieAuthenticationFilter(PRIVATE_REQUEST_URLS);
        return filter;
    }

    @Bean
    PrimaryAuthenticationFilter primaryAuthenticationFilter() {
        PrimaryAuthenticationFilter filter = new PrimaryAuthenticationFilter(PUBLIC_AUTHENTICATION_REQUEST_URL,
                                                                                credentialsValidator,
                                                                                jwtAuthenticationService);
        return filter;
    }

    @Bean
    FilterRegistrationBean withholdJwtAuthenticationProcessingFilterAutoRegistration(final JwtAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean withholdJwtCookieAuthenticationFilterAutoRegistration(final JwtCookieAuthenticationFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(FORBIDDEN);
    }

}
