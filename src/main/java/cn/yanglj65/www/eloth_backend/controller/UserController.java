package cn.yanglj65.www.eloth_backend.controller;

import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.service.UserService;
import cn.yanglj65.www.eloth_backend.view.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/login")
    public Object login(@RequestParam(value = "name") String username,
                        @RequestParam(value = "password") String password) throws Exception {
        return userService.login(username, password);
    }

    @PostMapping(value = "/register")
    public Object sinUp(@RequestParam(value = "name") String username,
                        @RequestParam(value = "password") String password, @RequestParam(value = "phone") String phone) throws Exception {
        return userService.register(username, password, phone);
    }

    @PostMapping(value = "/modifypwd")
    @JsonView(UserView.UserCommonView.class)
    public Object modifyPwd(@RequestParam(value = "newpassword") String new_password,
                            @RequestParam(value = "oldpassword") String old_password,
                            @RequestAttribute(value = "USER") User user) throws Exception {
        return userService.modifyPassword(user, new_password, old_password);
    }

    @GetMapping(value = "/getall")
    public Object getAllUser() {
        return userService.getUserList();
    }

    @PostMapping(value = "/delete")
    public Object deleteUser(@RequestParam(value = "id") int id) {
        return userService.deleteUser(id);
    }

    @PostMapping(value = "/upload/avatar")
    public Object uploadAvatar(@RequestParam("avatar") MultipartFile file, @RequestHeader("id") int nickId) {
       // timeCalculationAspect.printTime();
        return userService.uploadAvatar(file, nickId);
    }

    @GetMapping(value = "/getavatar")
    @ResponseBody
    public Object getAvatar(@RequestParam("id") int nickId){
        return userService.getAvatar(nickId);
    }
}
