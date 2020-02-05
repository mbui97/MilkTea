package com.tmh.bean;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tmh.entities.Order;

public class UserInfo {	

	private String fullName;
	private String email;
	private String password;
	private String phone;
	private String address;
}
