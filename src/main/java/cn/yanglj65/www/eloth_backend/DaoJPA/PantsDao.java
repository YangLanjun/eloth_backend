package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.Pants;
import cn.yanglj65.www.eloth_backend.entity.User;

import java.util.ArrayList;

public interface PantsDao extends ClothDao {
    ArrayList<Pants> getAllByUser(User user);
}
