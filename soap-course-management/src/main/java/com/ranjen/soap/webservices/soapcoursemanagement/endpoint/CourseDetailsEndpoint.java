package com.ranjen.soap.webservices.soapcoursemanagement.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ranjen.courses.CourseDetails;
import com.ranjen.courses.GetCourseDetailsRequest;
import com.ranjen.courses.GetCourseDetailsResponse;


//tell Spring that this is a ENDPOINT
@Endpoint
public class CourseDetailsEndpoint {

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://ranjen.com/courses
	// GetCourseDetailsRequest
	//put namespace and name of the request from XML in PayloadRoot
	@PayloadRoot(namespace = "http://ranjen.com/courses", localPart = "GetCourseDetailsRequest")
	// To convert REQUEST xml to Java we use @RequestPayload
	//the mapCourseDetails will be returned as Java Object, so need to convert it to 
	//xml using @ResponsePayload
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		
		CourseDetails courseDetails = new CourseDetails();
		
		courseDetails.setId(request.getId());
		
		courseDetails.setName("SCIENCE");
		
		courseDetails.setDescription("Science is good");
		
		response.setCourseDetails(courseDetails);
		
		return response;
	}

}