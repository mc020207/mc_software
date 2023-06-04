package com.onlineshopping.Service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.rowset.serial.SerialException;

@SpringBootTest
public class TestUserService {
    @Resource
    UserService userService;
    @Test
    public void testRegister(){
        // 正常插入
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjdklasjd");
            userRegisterDTO.setUserPhone("15267019805");
            userRegisterDTO.setUserIdCard("330106200112234013");
            userRegisterDTO.setUserEmail("20304444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 用户名重复
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("root");
            userRegisterDTO.setUserPhone("15267019804");
            userRegisterDTO.setUserIdCard("332106200112234013");
            userRegisterDTO.setUserEmail("20314444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 电话号码重复
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019805");
            userRegisterDTO.setUserIdCard("330106200112234113");
            userRegisterDTO.setUserEmail("203044454@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 身份证重复
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019705");
            userRegisterDTO.setUserIdCard("330106200112234013");
            userRegisterDTO.setUserEmail("20314444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 邮箱重复
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019705");
            userRegisterDTO.setUserIdCard("330103200112234013");
            userRegisterDTO.setUserEmail("20304444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 注册角色错误
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(10);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019705");
            userRegisterDTO.setUserIdCard("330106200112234013");
            userRegisterDTO.setUserEmail("20304444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 电话格式错误
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("152670197055");
            userRegisterDTO.setUserIdCard("330106200112234013");
            userRegisterDTO.setUserEmail("20304444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 身份证号格式错误
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019705");
            userRegisterDTO.setUserIdCard("33010620011223401223");
            userRegisterDTO.setUserEmail("20304444@fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        // 邮箱格式错误
        try{
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUserRole(0);
            userRegisterDTO.setUserName("asjajdee");
            userRegisterDTO.setUserPhone("15267019705");
            userRegisterDTO.setUserIdCard("330106200112234013");
            userRegisterDTO.setUserEmail("20304444#fudan.edu.cn");
            userRegisterDTO.setUserPwd("123456");
            userService.register(userRegisterDTO);
            System.out.println("finish!!!!!!");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
