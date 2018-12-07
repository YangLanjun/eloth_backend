package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.BugReportDao;
import cn.yanglj65.www.eloth_backend.DaoJPA.UserDao;
import cn.yanglj65.www.eloth_backend.entity.BugReporter;
import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.util.AccessUtil;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BugService {
    private final UserDao userDao;
    private final BugReportDao bugReportDao;

    @Autowired
    public BugService(UserDao userDao, BugReportDao bugReportDao) {
        this.userDao = userDao;
        this.bugReportDao = bugReportDao;
    }

    public Result sendBug(String accessToken,String bug){
        Optional<User> userOptional=userDao.findByAccessToken(accessToken);
        if(!userOptional.isPresent()){
            return AccessUtil.ACCESSDENY();
        }
        BugReporter bugReporter=new BugReporter();
        bugReporter.setUid(userOptional.get().getId());
        bugReporter.setBug(bug);
        BugReporter bugReturner=bugReportDao.save(bugReporter);
        return ResultUtil.resultGoodReturner(bugReturner);
    }
}
