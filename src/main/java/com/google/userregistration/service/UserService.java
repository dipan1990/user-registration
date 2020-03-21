package com.google.userregistration.service;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.userregistration.entity.User;
import com.google.userregistration.model.LoginDto;

@Service
public interface UserService {
	public ResponseEntity<List<User>> findAll();
//	public User findOne(@PathVariable("id") String userId);
	public ResponseEntity<?> create(@RequestBody User user);
//	public User saveAndUpdateUser(@PathVariable("email") String email, @RequestBody User user);
	public ResponseEntity<?> deactivateByEmail(@PathVariable("email") String email);
	public ResponseEntity<?> findByEmail(@PathVariable("email")String email);
	public ResponseEntity<User> update(@PathVariable("email")String email, @RequestBody User user);
	public ResponseEntity<?> login(LoginDto loginDto);
}
