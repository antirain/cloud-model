package com.cloud.common.entity;

import com.cloud.api.system.dto.UserLoginDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @author local
 * @date 2025-09-22
 * @description
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private List<String> roles;
    private List<String> permissions;

    // 👇 构造函数：从业务用户实体转换而来
    public SecurityUser(UserLoginDTO sysUser) {
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
        this.nickname = sysUser.getNickname();
        this.avatar = sysUser.getAvatar();
        this.roles = sysUser.getRoles();
        this.permissions = sysUser.getPermissions();
    }

    // ========== Spring Security 必须实现的方法 ========== //

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 合并角色 + 权限，转换为 Spring Security 的 Authority
        return Stream.concat(
                        roles.stream().map(SimpleGrantedAuthority::new),
                        permissions.stream().map(SimpleGrantedAuthority::new)
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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