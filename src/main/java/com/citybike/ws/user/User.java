package com.citybike.ws.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.citybike.ws.auth.Token;
import com.citybike.ws.blog.Blog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message= "{citybike.validation.constraints.username.NotNull.message}")
	@Size(min=4, max=255)
	@UniqueUsername
	private String username;
	
	@NotNull(message= "{citybike.validation.constraints.displayName.NotNull.message}")
	@Size(min=4, max=255)
	private String displayName;
	
	@NotNull(message= "{citybike.validation.constraints.password.NotNull.message}")
	@Size(min=8, max=255)
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message= "{citybike.validation.constraints.password.Pattern.message}")
	private String password;
	
	private String image;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY )
	private List<Blog> blogs;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private  List<Token> token;
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
