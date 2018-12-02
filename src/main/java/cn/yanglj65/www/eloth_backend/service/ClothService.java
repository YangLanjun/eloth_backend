package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.*;
import cn.yanglj65.www.eloth_backend.entity.*;
import cn.yanglj65.www.eloth_backend.util.AccessUtil;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClothService {
    private final
    ClothDao clothDao;
    private final
    UserDao userDao;
    private final
    TopsDao topsDao;
    private final
    PantsDao pantsDao;
    private final
    ShoesDao shoesDao;

    @Autowired
    public ClothService(ClothDao clothDao, UserDao userDao, TopsDao topsDao, PantsDao pantsDao, ShoesDao shoesDao) {
        this.clothDao = clothDao;
        this.userDao = userDao;
        this.topsDao = topsDao;
        this.pantsDao = pantsDao;
        this.shoesDao = shoesDao;
    }

    public Result addCloth(String color, String type, String size, String accessToken, String ClothType) {
        Optional<User> userOptional = userDao.findByAccessToken(accessToken);
        if (!userOptional.isPresent()) {
            return AccessUtil.ACCESSDENY();
        }
        Cloth cloth;
        switch (ClothType) {
            case "top": {
                cloth = new Tops();
                break;
            }
            case "pants": {
                cloth = new Pants();
                break;
            }
            case "shoes": {
                cloth = new Shoes();
                break;
            }
            default:
                return ResultUtil.resultBadReturner("衣服类型错误");
        }
        cloth.setColor(color);
        cloth.setSize(size);
        cloth.setType(type);
        cloth.setCanUse(true);
        cloth.setUser(userOptional.get());
        Cloth clothReturner;
        clothReturner = clothDao.save(cloth);
        return ResultUtil.resultGoodReturner(clothReturner);
    }

    public Result returnUserCloths(String accessToken) {
        Optional<User> userOptional = userDao.findByAccessToken(accessToken);
        if (!userOptional.isPresent()) {
            return AccessUtil.ACCESSDENY();
        }
        ClothList clothList = new ClothList();
        clothList.tops = getTopsByUser(userOptional.get());
        clothList.pants = getPantsByUser(userOptional.get());
        clothList.shoes = getShoesByUser(userOptional.get());
        return ResultUtil.resultGoodReturner(clothList);
    }

    public Result returnUserTops(String accessToken) {
        Optional<User> userOptional = userDao.findByAccessToken(accessToken);
        if (!userOptional.isPresent()) {
            return AccessUtil.ACCESSDENY();
        }
        ArrayList<Tops> tops = getTopsByUser(userOptional.get());
        return ResultUtil.resultGoodReturner(tops);
    }

    public Result returnUserPants(String accessToken) {
        Optional<User> userOptional = userDao.findByAccessToken(accessToken);
        if (!userOptional.isPresent()) {
            return AccessUtil.ACCESSDENY();
        }
        ArrayList<Pants> tops = getPantsByUser(userOptional.get());
        return ResultUtil.resultGoodReturner(tops);
    }

    public Result returnUserShoes(String accessToken) {
        Optional<User> userOptional = userDao.findByAccessToken(accessToken);
        if (!userOptional.isPresent()) {
            return AccessUtil.ACCESSDENY();
        }
        ArrayList<Shoes> tops = getShoesByUser(userOptional.get());
        return ResultUtil.resultGoodReturner(tops);
    }

    private ArrayList<Tops> getTopsByUser(User user) {
        return topsDao.getAllByUser(user);
    }

    private ArrayList<Pants> getPantsByUser(User user) {
        return pantsDao.getAllByUser(user);
    }

    private ArrayList<Shoes> getShoesByUser(User user) {
        return shoesDao.getAllByUser(user);
    }
}
