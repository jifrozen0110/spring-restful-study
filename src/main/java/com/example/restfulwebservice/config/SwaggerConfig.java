package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT=new Contact("Moon","http://www.joneconsulting.co.kr","answldjs1836@joneconsulting.co.kr");
    private static final ApiInfo DEFAULT_API_INFO=new ApiInfo("Awesome API Title","My User management REST API service","1.0","urn:tos",DEFAULT_CONTACT,"" +
            "Apache 2.0","http://www.apache.org/licenses/LICENSE-2.0",new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_ANDCONSUMES=new HashSet<>(
            Arrays.asList("application/json","application/xml")
            );


    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_ANDCONSUMES)
                .consumes(DEFAULT_PRODUCES_ANDCONSUMES);
    }
    @Bean
    public LinkDiscoverers discoverers(){
        List<LinkDiscoverer> plugins=new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
}
