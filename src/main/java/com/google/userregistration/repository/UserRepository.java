package com.google.userregistration.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.google.userregistration.entity.User;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("select u from User u where u.active = 1")
	public List<User> findAll();
    @Query("select u from User u where u.email=:email")
	public Optional<User> findByEmail(String email);
	public User save(User user);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.active = 0 WHERE u.email = :email")
    int deacivateByEmail(@Param("email") String email);
    @Query("select u from User u where u.email=:email and active=1")
	public Optional<User> findByActiveEmail(String email);
	
	
}
