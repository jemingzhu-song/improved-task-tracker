package traction.backend.service

import org.springframework.stereotype.Service
import traction.backend.datasource.TaskRepository
import traction.backend.datasource.UserAccountRepository
import traction.backend.model.Task
import traction.backend.model.UserAccount
import traction.backend.model.dto.TaskEditDTO
import java.lang.IllegalStateException
import javax.transaction.Transactional

@Service
@Transactional
class TaskService(
    private val userAccountRepository: UserAccountRepository,
    private val taskRepository: TaskRepository
) {

    fun getAllUserTasks(userId: Long): MutableList<Task> {
        var userAccount: UserAccount = userAccountRepository.findById(userId).orElseThrow {
            IllegalStateException("User with userId: $userId does not exist")
        }
        return userAccount.tasks
    }

    fun createUserTask(userId: Long, task: Task): Unit {
        var userAccount: UserAccount = userAccountRepository.findById(userId).orElseThrow {
            IllegalStateException("User with userId: $userId does not exist")
        }
        userAccount.tasks.add(task)
    }

    fun editUserTask(userId: Long, taskEditDTO: TaskEditDTO): Unit {
        var userAccount: UserAccount = userAccountRepository.findById(userId).orElseThrow {
            IllegalStateException("User with userId: $userId does not exist")
        }
        var currentTask: Task = userAccount.tasks.first { it.taskId == taskEditDTO.taskId }
        var currentTaskIndex: Int = userAccount.tasks.indexOfFirst { it.taskId == taskEditDTO.taskId }
        // Only update details that have been provided and are different
        if (taskEditDTO.status != null && taskEditDTO.status != currentTask.status) {
            currentTask.status = taskEditDTO.status!!
        }
        if (taskEditDTO.taskDescription != null && taskEditDTO.taskDescription != currentTask.taskDescription) {
            currentTask.taskDescription = taskEditDTO.taskDescription!!
        }
        // "currentTask" has now been updated. Now update the database
        userAccount.tasks.set(currentTaskIndex, currentTask)
    }

    fun deleteUserTask(userId: Long, taskId: Long): Unit {
        var userAccount: UserAccount = userAccountRepository.findById(userId).orElseThrow {
            IllegalStateException("User with userId: $userId does not exist")
        }
        var result: Boolean = userAccount.tasks.removeIf { it.taskId == taskId }
        if (result == false) {
            throw IllegalStateException("Failed to remove task with taskId: $taskId from user with userId: $userId")
        }
    }
}