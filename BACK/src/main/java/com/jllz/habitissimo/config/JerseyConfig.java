package com.jllz.habitissimo.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
       // register(UserResource.class);
        register(ServiceExceptionMapper.class);
        register(GenericExceptionMapper.class);
    }
}
