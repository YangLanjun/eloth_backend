package cn.yanglj65.www.eloth_backend.controller;

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
                           @RequestParam(value = "clothtype") String clothType,
                           @RequestHeader(value = "Authorization")String accessToken){

        return clothService.addCloth(color,type,size,accessToken,clothType);
    }
    @GetMapping(value = "tops")
    public Object getUserTops(@RequestHeader(value = "Authorization")String accessToken){
        return clothService.returnUserTops(accessToken);
    }
    @GetMapping(value = "pants")
    public Object getUserPants(@RequestHeader(value = "Authorization")String accessToken){
        return clothService.returnUserPants(accessToken);
    }
    @GetMapping(value = "shoes")
    public Object getUserShoes(@RequestHeader(value = "Authorization")String accessToken){
        return clothService.returnUserShoes(accessToken);
    }
    @GetMapping(value = "getall")
    public Object getUserCloths(@RequestHeader(value = "Authorization")String accessToken){
        return clothService.returnUserCloths(accessToken);
    }
}
