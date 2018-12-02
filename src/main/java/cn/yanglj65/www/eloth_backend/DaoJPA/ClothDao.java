package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ClothDao extends JpaRepository<Cloth,Integer> {
    ArrayList<Cloth> getAllByUserAndColorAndCanUse(User user, String color, boolean canUse);
   // ArrayList<Cloth> getAllByUser(User user);
}
