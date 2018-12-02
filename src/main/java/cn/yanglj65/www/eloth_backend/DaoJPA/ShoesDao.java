package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.Shoes;
import cn.yanglj65.www.eloth_backend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ShoesDao extends ClothDao {
    ArrayList<Shoes> getAllByUser(User user);
}
