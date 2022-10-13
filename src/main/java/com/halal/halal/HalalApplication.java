package com.halal.halal;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@SpringBootApplication
public class HalalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalalApplication.class, args);
	}

	@Bean
	public VelocityEngineFactoryBean velocityEngine() throws Exception {
	    VelocityEngineFactoryBean velocityBean = new VelocityEngineFactoryBean();
	    Properties velocityProperties= new Properties();
	    velocityProperties.setProperty("resource.loader", "class");
	    velocityProperties.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    velocityBean.setVelocityProperties(velocityProperties);
	    return velocityBean;
	}
}
