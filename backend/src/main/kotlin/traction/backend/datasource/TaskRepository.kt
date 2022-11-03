package traction.backend.datasource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import traction.backend.model.Task

@Repository
public interface TaskRepository: JpaRepository<Task, Long> {

}