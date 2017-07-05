package com.oak.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"com.oak","com.oak.security","hello"}) // 扫描该包路径下的所有spring组件
@EnableJpaRepositories(basePackages={"com.oak.repository","com.oak.dao"}) // JPA扫描该包路径下的Repositorie
@EntityScan(basePackages={"com.oak.model","com.oak.domain"}) // 扫描实体类 NorthNut
@SpringBootApplication
/*public class OakApplication  { //Classic Spring Boot JAR deployment
	public static void main(String[] args) {
		SpringApplication.run(OakApplication.class, args);
	}

}*/
public class OakApplication extends SpringBootServletInitializer {// Spring Boot WAR deployment

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OakApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OakApplication.class, args);
	}









}