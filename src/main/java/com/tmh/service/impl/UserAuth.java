package com.tmh.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.tmh.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuth extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private User user;

	public UserAuth(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			User user) {

		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.user = user;

	}

}

