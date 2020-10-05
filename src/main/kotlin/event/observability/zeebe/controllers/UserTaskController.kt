package event.observability.zeebe.controllers

import event.observability.zeebe.services.UserTaskService
import io.zeebe.spring.client.ZeebeClientLifecycle
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserTaskController(val service: UserTaskService, val client: ZeebeClientLifecycle) {

    @GetMapping("userTasks")
    fun getUserTasks(model: Model) : String{
        val assignee = getUsername()
        val tasks = service.findUserTasksByAssignee(assignee)
        model.addAttribute("tasks", tasks)
        model.addAttribute("count", "You have ${tasks.count()} user tasks pending interaction")

        return "tasks"
    }

    @PostMapping("userTasks/acknowledge")
    fun acknowledgeUserTask(
            @RequestParam(value = "jobKey") jobKey: Long,
            model: Model){
        service.acknowledgeUserTask(jobKey)
    }

    private fun getUsername(): String? {
        return SecurityContextHolder.getContext().authentication.name
    }

}