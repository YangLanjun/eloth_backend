package cn.yanglj65.www.eloth_backend.service;

import cn.yanglj65.www.eloth_backend.DaoJPA.*;
import cn.yanglj65.www.eloth_backend.entity.*;
import cn.yanglj65.www.eloth_backend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClothService {
    private final
    ClothDao clothDao;
    private final
    TopsDao topsDao;
    private final
    PantsDao pantsDao;
    private final
    ShoesDao shoesDao;

    @Autowired
    public ClothService(ClothDao clothDao,TopsDao topsDao, PantsDao pantsDao, ShoesDao shoesDao) {
        this.clothDao = clothDao;
        this.topsDao = topsDao;
        this.pantsDao = pantsDao;
        this.shoesDao = shoesDao;
    }

    public Result addCloth(String color, String type, String size, boolean usability,User user, String ClothType) {
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
        cloth.setUsability(usability);
        cloth.setUser(user);
        Cloth clothReturner;
        clothReturner = clothDao.save(cloth);
        return ResultUtil.resultGoodReturner(clothReturner);
    }

    public Result returnUserCloths(User user) {
        ClothList clothList = new ClothList();
        clothList.tops = getTopsByUser(user);
        clothList.pants = getPantsByUser(user);
        clothList.shoes = getShoesByUser(user);
        return ResultUtil.resultGoodReturner(clothList);
    }

    public Result returnUserTops(User user) {
        ArrayList<Tops> tops = getTopsByUser(user);
        return ResultUtil.resultGoodReturner(tops);
    }

    public Result returnUserPants(User user) {
        ArrayList<Pants> tops = getPantsByUser(user);
        return ResultUtil.resultGoodReturner(tops);
    }

    public Result returnUserShoes(User user) {
        ArrayList<Shoes> tops = getShoesByUser(user);
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

    public  void textExtendsAndInterface(){
        Cloth cloth=new Shoes();
        ((Shoes) cloth).showShoes();
        ClothInterface clothInterface=new Shoes();
        clothInterface.showCloth();
    }
}
