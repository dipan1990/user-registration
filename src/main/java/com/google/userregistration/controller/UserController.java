package com.google.userregistration.controller;

import java.util.List;
import java.util.Optional;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.userregistration.constants.URI;
import com.google.userregistration.entity.User;
import com.google.userregistration.model.CustomMessage;
import com.google.userregistration.model.LoginDto;
import com.google.userregistration.service.UserService;

//instead of component annotation which is generic spring uses
//@Controller
//We can add @ResponseBody at class level as well so, no need to add at each function
//@ResponseBody
//instead of using above two, spring have RestController which includes @Controller, @ResponseBody
//We can add @RequestMapping for specific value at class level as well so, no need to add at each function

@RestController
@RequestMapping(value = URI.USERS)

public class UserController {

	@Autowired
	private UserService service;

	// whatever is returned write to the response body
	@ResponseBody
	// This method should be called on get request, with value /employees, and
	// produces json
	// As jackson is availablein dependency it wil produxe JSON object directly
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return service.findAll();
	}

	@ResponseBody
	// here we are using, template as anything come after employees will be parsed
	// to id
	@RequestMapping(method = RequestMethod.GET, value = URI.EMAIL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	// How to assign id to userId? use annotation PathVariable
	// for multiple variables, "/users/{id}/{city}" and (@PathVariable("id") String
	// userId, @PathVariable("city") String city)
	public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
		return service.findByEmail(email);
		
	}

	@PostMapping(value = URI.LOGIN)
	// We are using user object from RequestBody so use @RequestBody
	public ResponseEntity<?> login(@RequestBody LoginDto LoginDto) {

		return service.login(LoginDto);
	}

	/*
	 * @RequestMapping(value = "/email/{email:.+}", method = RequestMethod.GET)
	 * public ResponseEntity findByEmail(@PathVariable(name = "email", value =
	 * "email") String email) {
	 * 
	 * User user = service.findByEmail(email); return new ResponseEntity(user,
	 * HttpStatus.OK); }
	 */
	/*
	 * @ResponseBody //here we are using, template as anything come after employees
	 * will be parsed to id
	 * 
	 * @RequestMapping(method=RequestMethod.GET, value=URI.ID,
	 * produces=MediaType.APPLICATION_JSON_UTF8_VALUE) //How to assign id to userId?
	 * use annotation PathVariable //for multiple variables, "/users/{id}/{city}"
	 * and (@PathVariable("id") String userId, @PathVariable("city") String city)
	 * public User findByMail(@PathVariable("id") String userId){ return
	 * service.findOne(userId); }
	 */
	@ResponseBody
	// As it consumes JSON
	@RequestMapping(method = RequestMethod.POST, value=URI.CREATE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	// We are using user object from RequestBody so use @RequestBody
	public ResponseEntity<?> create(@RequestBody User user) {
		return service.create(user);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, value = URI.EMAIL, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	// We are using id from PathVariable so use @PathVariable, using user from
	// RequestBody so @RequestBody
	public ResponseEntity<User> update(@PathVariable("email") String email, @RequestBody User user) {
		return service.update(email, user);

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE, value = URI.EMAIL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deactivate(@PathVariable("email") String email) {
		return service.deactivateByEmail(email);
	}
}
