package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    Optional<User> findByUserNameAndDeletedFalse(String userName);
    Optional<User> findByAccessTokenAndDeletedFalse(String accessToken);
    Optional<User> findByNickId(int nickId);
}
