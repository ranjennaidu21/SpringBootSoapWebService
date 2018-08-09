package com.ranjen.soap.webservices.soapcoursemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoapCourseManagementApplication {
	//To test run this app. 
	//Download Wizdler Chrome plugin. 
	//Open wsdl path based on WebServiceConfig eg:http://localhost:8080/ws/courses.wsdl
	//Click on the widzler plugin icon and select the method.
	//Type the input details inside the tag and click go for POST method.
	//You can see the response return back
	
	//Authentication added
	//So in widzler select authentication and choose WSSE PasswordText authentication
	//type username and passwrod before click on the POST button
	public static void main(String[] args) {
		SpringApplication.run(SoapCourseManagementApplication.class, args);
	}
}
