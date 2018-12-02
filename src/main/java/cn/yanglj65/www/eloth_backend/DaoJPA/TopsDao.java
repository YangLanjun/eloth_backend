package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.Tops;
import cn.yanglj65.www.eloth_backend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TopsDao extends ClothDao {
    ArrayList<Tops> getAllByUser(User user);
}
