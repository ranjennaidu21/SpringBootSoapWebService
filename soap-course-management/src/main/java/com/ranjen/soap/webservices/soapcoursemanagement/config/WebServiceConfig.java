package com.ranjen.soap.webservices.soapcoursemanagement.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable Spring Web Services
@EnableWs
// Tell Spring ths is Configuration
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{
	// MessageDispatcherServlet is servlet for webservices, identify endpoints
	// ApplicationContext
	// url -> /ws/*
	//this servlet handle request and map simple uri to it
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}

	//generate wsdl based on schema 
	// /ws/courses.wsdl
	// course-details.xsd
	
	//Spring create instance of XsdSchema bean , and autowire it in method below
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
	
	//use the schema coursesSchema returned from the XsdSchema bean above
	//so now we want to expose the wsdl in the following path /ws/courses.wsdl
	@Bean(name = "courses")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		
		//set the wdsl definition
		//PortType is something like interface
		definition.setPortTypeName("CoursePort");
		definition.setTargetNamespace("http://ranjen.com/courses");
		definition.setLocationUri("/ws");
		definition.setSchema(coursesSchema);
		return definition;
	}
	
	//XwsSecurityInterceptor - intercept all the request that coming in , 
	//check username and id if it is secure
	@Bean
	public XwsSecurityInterceptor securityInterceptor(){
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		//Callback Handler -> use the SimplePasswordValidationCallbackHandler bean below
		//which will check the username and password as given
		securityInterceptor.setCallbackHandler(callbackHandler());
		//Security Policy -> securityPolicy.xml to define security policy 
		//and configure it into this inteceptor
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}
	
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("user", "password"));
		return handler;
	}

	//Interceptors.add -> add the XwsSecurityInterceptor bean we have configured above
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}

}