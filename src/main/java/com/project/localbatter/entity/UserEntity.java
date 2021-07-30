package com.project.localbatter.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.localbatter.vo.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
public class UserEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(unique = true)
    private String username;

    private String profilePath;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "notificationId")
    private NotificationEntity notification;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profileId")
    private ProfileEntity profile;

    @Column
    @Enumerated(EnumType.STRING)
    private Role Role;

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantAuth = new ArrayList<>();
        grantAuth.add(new SimpleGrantedAuthority(getRole().getValue()));
        return grantAuth;
    }
    
}
