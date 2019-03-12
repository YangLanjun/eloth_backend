package cn.yanglj65.www.eloth_backend.util;

import cn.yanglj65.www.eloth_backend.DaoJPA.UserDao;
import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessUtil {
    @Autowired
    UserDao userDao;
    public User getUserById(int id) {
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }
    public User validateToken(String accessToken){
        if(accessToken==null||accessToken.isEmpty()){
            return null;
        }
        Optional<User> userOptional=userDao.findByAccessTokenAndDeletedFalse(accessToken);
        return userOptional.orElse(null);
    }
    public static Result ACCESS_DENY(){
        return ResultUtil.resultBadReturner("Access Deny");
    }
}
