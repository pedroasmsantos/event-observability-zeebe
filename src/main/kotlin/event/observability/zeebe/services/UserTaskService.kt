package event.observability.zeebe.services

import event.observability.zeebe.entities.UserTask
import event.observability.zeebe.repositories.UserTaskRepository
import io.zeebe.spring.client.ZeebeClientLifecycle
import org.springframework.stereotype.Service

@Service
class UserTaskService (val repository: UserTaskRepository,
                        val zeebeClient: ZeebeClientLifecycle) {
    fun createUserTask(userTask: UserTask){
        repository.insertUserTask(userTask)
    }

    fun findUserTasksByAssignee(assignee: String?) : List<UserTask>{
        return repository.findUserTasksByAssignee(assignee)
    }

    fun acknowledgeUserTask(jobKey: Long){
        repository.removeUserTaskByKey(jobKey)
        zeebeClient.newCompleteCommand(jobKey).send().join()
    }
}