package pro.sky.cw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.cw3.entity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask,Long> {

    List<NotificationTask> findAllByDateTime(LocalDateTime dateTime);
}
