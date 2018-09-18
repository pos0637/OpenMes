package com.furongsoft.rbac.security;

import com.furongsoft.rbac.entities.JwtToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 重复认证限制的密码匹配器
 *
 * @author Alex
 */
@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    /**
     * 最大重试登录次数
     */
    @Value("${security.max-retry-count}")
    private int MAX_RETRY_COUNT;

    /**
     * 缓存
     */
    private Cache<String, AtomicInteger> mRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        // TODO: create cache
        this.mRetryCache = null;
        this.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);
        this.setHashIterations(PasswordHelper.HASH_ITERATIONS);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token instanceof JwtToken) {
            return doJwtCredentialsMatch((JwtToken) token, info);
        } else if (token instanceof UsernamePasswordToken) {
            return doUsernamePasswordCredentialsMatch((UsernamePasswordToken) token, info);
        } else {
            return false;
        }
    }

    /**
     * 执行用户名密码匹配检查
     *
     * @param usernamePasswordToken 用户名密码令牌
     * @param info                  认证信息
     * @return 是否匹配
     */
    private boolean doUsernamePasswordCredentialsMatch(UsernamePasswordToken usernamePasswordToken, AuthenticationInfo info) {
        String token = usernamePasswordToken.getUsername();
        if (mRetryCache != null) {
            AtomicInteger retryCount = mRetryCache.get(token);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
                mRetryCache.put(token, retryCount);
            }

            if (retryCount.incrementAndGet() > MAX_RETRY_COUNT) {
                throw new ExcessiveAttemptsException();
            }
        }

        return super.doCredentialsMatch(usernamePasswordToken, info);
    }

    /**
     * 执行JWT令牌匹配检查
     *
     * @param jwtToken JWT令牌
     * @param info     认证信息
     * @return 是否匹配
     */
    private boolean doJwtCredentialsMatch(JwtToken jwtToken, AuthenticationInfo info) {
        String token = jwtToken.getToken();
        String username = JwtUtils.getUsername(token);
        if (username == null) {
            return false;
        }

        if (mRetryCache != null) {
            AtomicInteger retryCount = mRetryCache.get(token);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
                mRetryCache.put(token, retryCount);
            }

            if (retryCount.incrementAndGet() > MAX_RETRY_COUNT) {
                throw new ExcessiveAttemptsException();
            }
        }

        return JwtUtils.verify(token, username, (String) info.getCredentials());
    }
}
