package com.furongsoft.openmes.research.pms.controllers.api.v1;

import com.furongsoft.base.entities.RestResponse;
import com.furongsoft.rbac.entities.QUser;
import com.furongsoft.rbac.entities.User;
import com.furongsoft.rbac.repositories.UserRepository;
import com.furongsoft.rbac.security.JwtUtils;
import com.furongsoft.rbac.security.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 系统控制器
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/v1/system")
public class SystemController {
    private final UserRepository userRepository;

    @Autowired
    public SystemController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 响应内容
     */
    @PostMapping("/login")
    public RestResponse login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));

        Optional<User> user = userRepository.findOne(QUser.user.userName.eq(username));
        if (!user.isPresent()) {
            throw new UnknownAccountException();
        }

        return new RestResponse(HttpStatus.OK, null, null, JwtUtils.getToken(username, user.get().getPassword()));
    }

    @PostMapping("/init")
    public RestResponse init() {
        User user = new User();
        user.setUserName("1");
        user.setPassword("1");
        userRepository.save(PasswordHelper.encryptPassword(user));

        return new RestResponse(HttpStatus.OK);
    }
}
