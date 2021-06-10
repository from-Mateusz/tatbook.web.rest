package me.m92.tatbook_web.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * In my case, where I'm implementing REST API, redirection after operations eg. login or logout
 * is not desired, let alone useless.
 */
public class OffRedirectStrategy implements RedirectStrategy {

    static private final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OffRedirectStrategy.class);
    }

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        LOGGER.info("No redirection!");
    }
}
