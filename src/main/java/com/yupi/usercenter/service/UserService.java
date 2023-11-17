package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author PC
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-05 15:06:41
*/
public interface UserService extends IService<User> {
     String USER_LOGIN_STATE ="userLoginState" ;
    /**
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 只需要按/ ** 回车就会自动注释
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}
