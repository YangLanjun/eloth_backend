package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.BugReportDao;
import cn.yanglj65.www.eloth_backend.entity.BugReporter;
import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BugService {
    private final BugReportDao bugReportDao;

    @Autowired
    public BugService(BugReportDao bugReportDao) {
        this.bugReportDao = bugReportDao;
    }

    public Result sendBug(String bug,int user_id){

        BugReporter bugReporter=new BugReporter();
        bugReporter.setUid(user_id);
        bugReporter.setBug(bug);
        BugReporter bugReturner=bugReportDao.save(bugReporter);
        return ResultUtil.resultGoodReturner(bugReturner);
    }
}
