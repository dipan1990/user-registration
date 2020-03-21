package com.google.userregistration.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Version {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    @NotNull
	private String name;
    
    @NotNull
    private String link;
    
    @NotNull
    private String message;
    
    @NotNull
    private String type;

	@Override
	public String toString() {
		return "Version [id=" + id + ", name=" + name + ", link=" + link + ", message=" + message + ", type=" + type
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Version() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
