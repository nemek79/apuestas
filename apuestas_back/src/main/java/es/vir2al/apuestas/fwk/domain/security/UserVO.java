package es.vir2al.apuestas.fwk.domain.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVO implements UserDetails {
   
    private String userName;
    private String aplicacion;

    private Collection<? extends GrantedAuthority> authorities;

    public UserVO() {

	}

	public UserVO(String userName, String authorities, String aplicacion) {

		this.userName = userName;
		this.setAuthorities(authorities);
		this.aplicacion = aplicacion;

	}

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	private void setAuthorities(String auths) {

		auths = auths.replace("[", "");
		auths = auths.replace("]", "");

		this.authorities = Arrays.stream(auths.split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {

		return null;
	}

	@Override
	public String getUsername() {

		return this.userName;
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
