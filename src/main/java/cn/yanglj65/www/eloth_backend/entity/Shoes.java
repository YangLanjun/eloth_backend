package cn.yanglj65.www.eloth_backend.entity;

import cn.yanglj65.www.eloth_backend.util.LogUtil;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "shoes_t")
public class Shoes extends Cloth implements ClothInterface{
    public void showShoes() {
        LogUtil.logger.info("shoes");
    }

    @Override
    public void showCloth() {
        LogUtil.logger.info(clothType);
    }
}
