package cn.yanglj65.www.eloth_backend.controller;

import cn.yanglj65.www.eloth_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/signup")
    public Object sinUp(@RequestParam(value = "name") String username,
                        @RequestParam(value = "password") String password,@RequestParam(value = "phone") String phone) throws Exception {
        return userService.signUp(username, password,phone);
    }

    @PostMapping(value = "/modifypwd")
    public Object modifyPwd(@RequestParam(value = "name") String username,
                            @RequestParam(value = "newpassword") String new_password, @RequestParam(value = "oldpassword") String old_password) throws Exception {
        return userService.modifyPassword(username, new_password, old_password);
    }

    @GetMapping(value = "/getall")
    public Object getAllUser() {
        return userService.getUserList();
    }

    @PostMapping(value = "/delete")
    public Object deleteUser(@RequestParam(value = "id") int id) {
        return userService.deleteUser(id);
    }
}
