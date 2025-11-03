package com.javatech.lab4.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HateoasConfig {

    @Bean
    public HalConfiguration halConfiguration() {
        return new HalConfiguration();
    }

    @Bean
    public HalLinkDiscoverer halLinkDiscoverer() {
        return new HalLinkDiscoverer();
    }
}