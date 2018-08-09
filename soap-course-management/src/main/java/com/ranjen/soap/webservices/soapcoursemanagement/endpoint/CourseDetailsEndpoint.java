package com.ranjen.soap.webservices.soapcoursemanagement.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ranjen.courses.CourseDetails;
import com.ranjen.courses.GetAllCourseDetailsRequest;
import com.ranjen.courses.GetAllCourseDetailsResponse;
import com.ranjen.courses.GetCourseDetailsRequest;
import com.ranjen.courses.GetCourseDetailsResponse;
import com.ranjen.soap.webservices.soapcoursemanagement.bean.Course;
import com.ranjen.soap.webservices.soapcoursemanagement.service.CourseDetailsService;


//tell Spring that this is a ENDPOINT
@Endpoint
public class CourseDetailsEndpoint {
	
	@Autowired
	CourseDetailsService service;
	
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
		Course course = service.findById(request.getId());
		return mapCourseDetails(course);
	}
	
	//only getting one course details
	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
	
	//this method will creating coursedetails , set the values and return back 
	private GetCourseDetailsResponse mapCourse(GetCourseDetailsRequest request) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		
		Course course = service.findById(request.getId());
		
		CourseDetails courseDetails = new CourseDetails();
		
		courseDetails.setId(course.getId());
		
		courseDetails.setName(course.getName());
		
		courseDetails.setDescription(course.getDescription());
		
		response.setCourseDetails(courseDetails);
		
		return response;
	}
	
	@PayloadRoot(namespace = "http://ranjen.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}
	
	//get multiple course details
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		//looping around each course and map each course with coursedetails 
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

}