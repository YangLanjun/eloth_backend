package cn.yanglj65.www.eloth_backend.controller;

import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.service.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "cloth")
public class ClothController {
    private final
    ClothService clothService;

    @Autowired
    public ClothController(ClothService clothService) {
        this.clothService = clothService;
    }
    @PostMapping(value = "add")
    public Object addCloth(@RequestParam(value = "color") String color,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "size") String size,
                           @RequestParam(value = "usability")boolean usability,
                           @RequestParam(value = "class") String clothType,
                           @RequestAttribute(value = "USER") User user){

        return clothService.addCloth(color,type,size,usability,user,clothType);
    }
    @GetMapping(value = "tops")
    public Object getUserTops(@RequestAttribute(value = "USER") User user){
        return clothService.returnUserTops(user);
    }
    @GetMapping(value = "pants")
    public Object getUserPants(@RequestAttribute(value = "USER") User user){
        return clothService.returnUserPants(user);
    }
    @GetMapping(value = "shoes")
    public Object getUserShoes(@RequestAttribute(value = "USER") User user){
        return clothService.returnUserShoes(user);
    }
    @GetMapping(value = "getall")
    public Object getUserCloths(@RequestAttribute(value = "USER") User user){
        return clothService.returnUserCloths(user);
    }
}
