package task.tracker.traction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TractionApplication

fun main(args: Array<String>) {
	runApplication<TractionApplication>(*args)
}
