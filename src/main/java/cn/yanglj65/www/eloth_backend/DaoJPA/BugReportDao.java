package cn.yanglj65.www.eloth_backend.DaoJPA;

import cn.yanglj65.www.eloth_backend.entity.BugReporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugReportDao extends JpaRepository<BugReporter,Integer> {
}
