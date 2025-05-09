package fr.eni.tp.projet.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
    public class WebConfiguration implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            String userHome = System.getProperty("user.home");
            registry
                    .addResourceHandler("/uploads/**")
                    .addResourceLocations("file:///" + userHome + "/uploads/");
        }
    }

