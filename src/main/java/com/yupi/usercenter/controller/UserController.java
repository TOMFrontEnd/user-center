package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.UserConstant.USER_LOGIN_STATE;

// 所以返回都是JSON 数据，适用于请求参数本身的校验，不涉及业务逻辑本身
//用户接口
@RestController
@RequestMapping("/user")
// 适用于编写RESTFUL风格的API, 返回JSON
//写两个请求，一个是用户注册，一个是用户登录
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
      //  userService.userRegister(userAccount, userPassword, checkPassword);

        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //  userService.userRegister(userAccount, userPassword, checkPassword);

        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
       // String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){

       if (!isAdmin(request)){
           return new ArrayList<>();
       }
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username",username);
        }

        List<User> userList=userService.list(queryWrapper);
        //此处过滤脱敏过的USER ONLY.
        return userList.stream().map(user -> userService.getSafetyUser((user))).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request){
        if (!isAdmin(request)){
            return false;
        }
        if (id<=0) {
            return false;
        }
        return userService.removeById(id);
    }
    /**
     * if admin?
     */
    private boolean isAdmin(HttpServletRequest request)
    {Object userObj=request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
        }

