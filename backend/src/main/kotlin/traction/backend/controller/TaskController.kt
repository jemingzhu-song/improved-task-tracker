package traction.backend.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import traction.backend.model.Task
import traction.backend.model.dto.RecentWeekTasksDTO
import traction.backend.model.dto.TaskEditDTO
import traction.backend.service.TaskService
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("user/task")
class TaskController(
    private val taskService: TaskService
) {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidFormatException::class)
    fun handleJsonParseException(e: InvalidFormatException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/get/all/{userId}")
    fun getAllUserTasks(@PathVariable userId: Long): MutableList<Task> {
        return taskService.getAllUserTasks(userId)
    }

    @PostMapping("/get/recent/week/{userId}")
    fun getRecentWeekUserTasks(@PathVariable userId: Long, @RequestBody recentWeekTasksDTO: RecentWeekTasksDTO): MutableList<Task> {
        return taskService.getRecentWeekUserTasks(userId, recentWeekTasksDTO)
    }

    @PostMapping("/create/{userId}")
    fun createUserTask(@PathVariable userId: Long, @RequestBody task: Task): Task {
        return taskService.createUserTask(userId, task)
    }

    @PutMapping("/edit/{userId}")
    fun editUserTask(@PathVariable userId: Long, @RequestBody taskEditDTO: TaskEditDTO): Unit {
        return taskService.editUserTask(userId, taskEditDTO)
    }

    @DeleteMapping("/delete/{userId}/{taskId}")
    fun deleteUserTask(@PathVariable userId: Long, @PathVariable taskId: Long): Unit {
        return taskService.deleteUserTask(userId, taskId)
    }

}