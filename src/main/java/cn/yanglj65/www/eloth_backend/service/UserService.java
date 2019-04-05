package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.UserDao;
import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Value("${upload.avatar.path}")
    private String uploadAvatarPath;

    @javax.annotation.Resource
    private ResourceLoader resourceLoader;
    public Result login(String username, String password) {
        Optional<User> optionalUser = userDao.findByUserNameAndDeletedFalse(username);
        if (username != null) {
            if (!optionalUser.isPresent()) {
                return ResultUtil.resultBadReturner("找不到该用户");
            }
            User user = optionalUser.get();
            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
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
    public Result register(String username, String password, String phone) {
        Optional<User> optionalUser = userDao.findByUserNameAndDeletedFalse(username);
        if (optionalUser.isPresent()) {
            return ResultUtil.resultBadReturner("该用户名已被注册");
        }
        if (phone.length() != 11) {
            return ResultUtil.resultBadReturner("请输入正确的中国大陆地区手机号");
        }
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String createTime = fDate.format(new Date());
        User user = new User();
        user.setUserName(username);
        String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Pwd);
        user.setCreateTime(createTime);
        user.setPhone(phone);
        final String accessToken = UUID.randomUUID().toString().replace("-", "");
        user.setAccessToken(accessToken);
        User createUser = userDao.save(user);
        return ResultUtil.resultGoodReturner(createUser);
    }

    //修改密码
    public Result modifyPassword(User user, String new_password, String old_password) {
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(old_password.getBytes()))) {
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
        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent()) {
            return ResultUtil.resultBadReturner("找不到该用户");
        } else {
            User user = optionalUser.get();
            if (user.getRole().equals("SUPER")) {
                return ResultUtil.resultBadReturner("管理员用户不能被删除");
            }
            user.setDeleted(true);
        }
        return ResultUtil.resultGoodReturner("用户已成功删除");
    }

    public Result uploadAvatar(MultipartFile file, int nickId) {
        final Set<String> ALLOWED_FILE_TYPE = Collections.unmodifiableSet(new HashSet<>
                (Arrays.asList(".jpg", ".png", ".jpeg", ".gif")));
        if (file == null) {
            return ResultUtil.resultBadReturner("请上传文件");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!ALLOWED_FILE_TYPE.contains(suffixName)) {
            return ResultUtil.resultBadReturner("   请上传正确的图片格式，目前支持jpg,jpeg,png和gif格式");
        }
        String distFileName = UUID.randomUUID().toString() + nickId + suffixName;
        String filePath="C:\\Users\\12849\\IdeaProjects\\eloth_backend\\static\\img\\avatar\\"+distFileName;
        // String filePath="./static/img/avatar"+distFileName;
        File distFile=new File(filePath);
        if(!distFile.getParentFile().exists()){
            distFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(distFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.resultBadReturner("文件写入失败，请稍后重试");
        }
        Optional<User> optionalUser = userDao.findByNickId(nickId);
        User user;
        user = optionalUser.orElseGet(() -> (User) (register(UUID.randomUUID().toString().substring(0, 8), "a123456", "17665070189").getData()));
        user.setImgUrl(distFileName);
        user.setNickId(nickId);
        userDao.save(user);
        return ResultUtil.resultGoodReturner("头像上传成功");
    }

    public ResponseEntity getAvatar(int nickId) {
        Optional<User> optionalUser = userDao.findByNickId(nickId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            User user = optionalUser.get();
            if(user.getImgUrl()==null){
                return ResponseEntity.notFound().build();
            }
            String filePath = uploadAvatarPath + user.getImgUrl();
            return ResponseEntity.ok(resourceLoader.getResource("file:"+filePath));
        }

    }
}
