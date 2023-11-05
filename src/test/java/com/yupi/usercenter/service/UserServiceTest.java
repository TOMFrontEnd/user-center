package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
/*
*用户测试
*
* @author yupi
 */
@SpringBootTest
class UserServiceTest {
@Resource
private UserService userService;

@Test
    public void testAddUser(){
    User user = new User();

    user.setUsername("dogYupi");
    user.setUserAccount("123");
    user.setAvatarUrl("https://scontent.fbne8-1.fna.fbcdn.net/v/t45.5328-4/377508834_6684393004989060_3689077749923592422_n.jpg?stp=c43.0.260.260a_dst-jpg_p261x260&_nc_cat=104&ccb=1-7&_nc_sid=247b10&_nc_ohc=v5o2Jl8BgK8AX8rPFB1&_nc_ht=scontent.fbne8-1.fna&oh=00_AfD0gZFkyTCKdo-K8SV_pl9ZeyFg-nH8i3pvx5s8uMI1lw&oe=654B6800");
    user.setGender(0);
    user.setUserPassword("xxx");
    user.setEmail("123");
    user.setPhone("456");


   boolean result=userService.save(user);
    System.out.println(user.getId());
    Assertions.assertTrue(result);
}

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "dogyupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);
    }
}