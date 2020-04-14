package com.google.userregistration.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.userregistration.entity.User;
import com.google.userregistration.exception.BadRequest;
import com.google.userregistration.exception.NotFound;
import com.google.userregistration.model.CustomMessage;
import com.google.userregistration.model.LoginDto;
import com.google.userregistration.repository.UserRepository;
import com.google.userregistration.service.UserService;

import com.google.userregistration.entity.Role;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	/*
	 * @Autowired public UserServiceImpl(UserRepository repository) {
	 * this.repository = repository; }
	 */

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public ResponseEntity<List<User>> findAll() {
		return new ResponseEntity<List<User>>(repository.findAll(), HttpStatus.OK);

	}

//	@org.springframework.transaction.annotation.Transactional(readOnly=true)	
//	@Override
//	public User findOne(String id) {
//		return repository.findOne(id).orElseThrow(() -> 
//		new NotFound("User with id "+ id + "does not found"));
//	}
//	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> findByEmail(String email) {
		Optional<User> userData = repository.findByEmail(email);
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData, HttpStatus.OK);
		} else
			return new ResponseEntity(new CustomMessage("user doesn't exist", 400, "Bad Request"),
					HttpStatus.BAD_REQUEST);
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public ResponseEntity<User> create(User user) {
		Optional<User> mayExists = repository.findByEmail(user.getEmail());
		if (mayExists.isPresent())
			// throw new BadRequest("User with email "+ user.getEmail() + "already exists");
			return new ResponseEntity(new CustomMessage("user already exists", 400, "Bad Request"),
					HttpStatus.BAD_REQUEST);
		else
			user.setActive(1);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return new ResponseEntity(repository.save(user), HttpStatus.CREATED);
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public ResponseEntity<User> update(String email, User user) {
		Optional<User> userData = repository.findByEmail(email);

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setName(user.getName());
			_user.setMobileNo(user.getMobileNo());
			_user.setOrganization(user.getOrganization());
			_user.setPassword(user.getPassword());
			_user.setActive(user.getActive());
			return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
		} else {

			return new ResponseEntity(
					new CustomMessage("User does not exist!! Give proper mail id", 400, "Bad Request"),
					HttpStatus.BAD_REQUEST);

		}

	}

//	@org.springframework.transaction.annotation.Transactional
	@SuppressWarnings("unchecked")
	// @Override
//	 public User saveAndUpdateUser(String email,User user) {
//		repository.findByEmail(email).orElseThrow(() -> 
//		new NotFound("User with id "+ email + "does not found"));
//		    return repository.saveAndFlush(user);
//		  }
	@org.springframework.transaction.annotation.Transactional
	@Override
	public ResponseEntity<?> deactivateByEmail(String email) {
		Optional<User> userData = repository.findByEmail(email);
		if (userData.isPresent()) {
			int rowsAffected = repository.deacivateByEmail(email);
			if (rowsAffected > 0) {
				return new ResponseEntity(new CustomMessage("User successfully deactivated", 200, "Success"),
						HttpStatus.OK);

			} else
				return new ResponseEntity(
						new CustomMessage("User not successfully deactivated", 500, "Some server error"),
						HttpStatus.INTERNAL_SERVER_ERROR);

		} else {
			return new ResponseEntity(
					new CustomMessage("User does not exist!! Give proper mail id", 400, "Bad Request"),
					HttpStatus.BAD_REQUEST);

		}
	}

	@SuppressWarnings("unchecked")
	@org.springframework.transaction.annotation.Transactional
	@Override
	public ResponseEntity<?> login(LoginDto loginDto) {
		Optional<User> userData = repository.findByActiveEmail(loginDto.getEmail());
		if (userData.isPresent()) {
			User _user = userData.get();
			if (bCryptPasswordEncoder.matches(loginDto.getPassword(), _user.getPassword())) {
				return new ResponseEntity(new CustomMessage("Login Successful", 200, "Success"), HttpStatus.OK);

			} else
				return new ResponseEntity(
						new CustomMessage("Login Unsuccessful!! password incorrect", 400, "Bad Request"),
						HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity(new CustomMessage("Login Unsuccessful!! email id incorrect", 400, "Bad Request"),
					HttpStatus.BAD_REQUEST);
	}
}
