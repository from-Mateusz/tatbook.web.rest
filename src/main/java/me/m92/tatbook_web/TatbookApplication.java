package me.m92.tatbook_web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Locale;
import java.util.Properties;

@SpringBootApplication
public class TatbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TatbookApplication.class, args);
    }

    @Bean
    @Qualifier("character.encoding.utf8")
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        return characterEncodingFilter;
    }

    @Bean
    public FilterRegistrationBean createFilterRegistrationBean(@Qualifier("character.encoding.utf8") CharacterEncodingFilter characterEncodingFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(characterEncodingFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//          System.out.println("All beans:");
//          String[] beanNames = ctx.getBeanDefinitionNames();
//          for(String name : beanNames) {
//              System.out.println(name);
//          }
//        };
//    }
}
