package com.furongsoft.rbac.security;

import com.furongsoft.rbac.entities.JwtToken;
import com.furongsoft.rbac.entities.User;
import com.furongsoft.rbac.entities.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 领域
 *
 * @author Alex
 */
@Component
public class MyRealm extends AuthorizingRealm {
    private final UserRepository userRepository;

    @Autowired
    public MyRealm(@Qualifier("com.furongsoft.rbac.entities.UserRepository") UserRepository userRepository, RetryLimitHashedCredentialsMatcher matcher) {
        super(matcher);
        this.userRepository = userRepository;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username;
        if (authenticationToken instanceof JwtToken) {
            JwtToken token = (JwtToken) authenticationToken;
            username = JwtUtils.getUsername(token.getToken());
            if (username == null) {
                throw new AuthenticationException();
            }
        } else if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            username = token.getUsername();
        } else {
            throw new AuthenticationException();
        }

        Optional<User> user = userRepository.findOne((root, cq, cb) -> cb.equal(root.get("userName"), username));
        if (!user.isPresent()) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(user.get().getUserName(), user.get().getPassword(), ByteSource.Util.bytes(user.get().getSalt()), getName());
    }
}