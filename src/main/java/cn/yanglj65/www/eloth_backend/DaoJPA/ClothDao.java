package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ClothDao extends JpaRepository<Cloth,Integer> {
    ArrayList<Cloth> getAllByUserAndColorAndUsability(User user, String color, boolean usability);
   // ArrayList<Cloth> getAllByUser(User user);
}
