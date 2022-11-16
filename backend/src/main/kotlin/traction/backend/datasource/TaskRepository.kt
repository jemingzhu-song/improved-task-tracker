package traction.backend.datasource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import traction.backend.model.Task
import java.util.Date

@Repository
public interface TaskRepository: JpaRepository<Task, Long> {

    // Get a list of tasks within the week before the specified date
    @Query(value = "SELECT * FROM task t WHERE t.date_created <= ?1 AND t.date_created >= ?2 ORDER BY t.date_created", nativeQuery = true)
    fun getRecentWeekTasksBeforeDate(beforeDate: Date, afterDate: Date): MutableList<Task>
}