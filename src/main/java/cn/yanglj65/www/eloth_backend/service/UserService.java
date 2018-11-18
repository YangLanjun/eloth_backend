package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.UserDao;
import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public Result getUserById() {
        Optional<User> user = userDao.findById(1);
        return ResultUtil.resultGoodReturner(user);
    }

    public Result login(String username, String password) {
        Optional<User> optionalUser=userDao.findByUserName(username);
        if (username != null) {
            if (!optionalUser.isPresent()) {
                return ResultUtil.resultBadReturner("找不到该用户");
            }
            User user =optionalUser.get();
            if(user.isDeleted()){
                return ResultUtil.resultBadReturner("该用户已被删除");
            }else if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                return ResultUtil.resultBadReturner("密码错误");
            } else {
                final String accessToken = UUID.randomUUID().toString().replace("-", "");
                user.setAccessToken(accessToken);
                userDao.save(user);
                return ResultUtil.resultGoodReturner(user);
            }
        } else {
            return ResultUtil.resultBadReturner("用户名错误");
        }
    }

    //注册
    public Result signUp(String username, String password,String phone) {
        Optional<User> optionalUser=userDao.findByUserName(username);
        if (optionalUser.isPresent()) {
            return ResultUtil.resultBadReturner("该用户名已被注册");
        }
        if(phone.length()!=11){
            return ResultUtil.resultBadReturner("请输入正确的中国大陆地区手机号");
        }
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String createTime = fDate.format(new Date());
        User user = new User();
        user.setUserName(username);
        String md5Pwd= DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Pwd);
        user.setCreateTime(createTime);
        user.setPhone(phone);
        final String accessToken = UUID.randomUUID().toString().replace("-", "");
        user.setAccessToken(accessToken);
        User createUser = userDao.save(user);
        return ResultUtil.resultGoodReturner(createUser);
    }

    //修改密码
    public Result modifyPassword(String userName, String new_password, String old_password) {
        Optional<User> optionalUser=userDao.findByUserName(userName);
        if (!optionalUser.isPresent()) {
            return ResultUtil.resultBadReturner("找不到该用户");
        }
        User user = optionalUser.get();
        if(user.isDeleted()){
            return ResultUtil.resultBadReturner("该用户已被删除");
        }else if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(old_password.getBytes()))) {
            return ResultUtil.resultBadReturner("当前密码错误");
        } else {
            user.setPassword(DigestUtils.md5DigestAsHex(new_password.getBytes()));
            userDao.save(user);
            return ResultUtil.resultGoodReturner(user);
        }
    }

    //查询所有用户 --管理员权限
    public Result getUserList() {
        List<User> users = userDao.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isDeleted()) {
                users.remove(i);
            }
        }
        return ResultUtil.resultGoodReturner(users);
    }

    //删除特定用户  --管理员权限
    public Result deleteUser(int id) {
        Optional<User> optionalUser= userDao.findById(id);
        if (!optionalUser.isPresent()) {
            return ResultUtil.resultBadReturner("找不到该用户");
        }else{
            User user =optionalUser.get();
            if(user.isDeleted()){
                return ResultUtil.resultBadReturner("用户已被删除");
            }
            if(user.getRole().equals("SUPER")){
                return ResultUtil.resultBadReturner("管理员用户不能被删除");
            }
            user.setDeleted(true);
        }
        return ResultUtil.resultGoodReturner("用户已成功删除");
    }
}
