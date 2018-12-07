package cn.yanglj65.www.eloth_backend.controller;

import cn.yanglj65.www.eloth_backend.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "bug")
public class BugController {

    private final BugService bugService;

    @Autowired
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @PostMapping(value = "/send")
    public Object sendBud(@RequestParam(value = "bug") String bug, @RequestHeader(value = "Authorization") String accessToken) {
        return bugService.sendBug(accessToken, bug);
    }
}
