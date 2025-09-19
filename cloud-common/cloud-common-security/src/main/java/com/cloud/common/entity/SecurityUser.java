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

@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private Long id;              // 业务主键
    private String username;      // 登录名（用户名/手机号/邮箱）
    private String password;      // 密码（加密后）
    private String nickname;      // 昵称
    private String avatar;        // 头像
    private List<String> roles;   // 角色列表 ["ROLE_ADMIN", "ROLE_USER"]
    private List<String> permissions; // 权限列表 ["user:read", "order:create"]

    // 👇 构造函数：从业务用户实体转换而来
    public SecurityUser(UserLoginDTO sysUser) {
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
        this.nickname = sysUser.getNickname();
        this.avatar = sysUser.getAvatar();
        this.roles = sysUser.getRoles(); // 假设 SysUser 有 getRoles()
        this.permissions = sysUser.getPermissions(); // 假设有 getPermissions()
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
        return true; // 业务逻辑可扩展：比如判断用户是否过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 业务逻辑可扩展：比如判断是否被锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 业务逻辑可扩展：比如判断密码是否过期
    }

    @Override
    public boolean isEnabled() {
        return true; // 业务逻辑可扩展：比如判断用户是否被禁用
    }

    // ========== 额外提供业务方法 ========== //

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}