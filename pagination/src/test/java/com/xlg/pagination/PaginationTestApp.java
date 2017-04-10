package com.xlg.pagination;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:META-INF/pagination_test.xml"})
public class PaginationTestApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
        SpringApplication.run(PaginationTestApp.class, args);
    }
	
	@Bean
	public Filter paginationFilter() {
	   Filter filter = new PaginationFilter();
	   return filter;
	}
}
