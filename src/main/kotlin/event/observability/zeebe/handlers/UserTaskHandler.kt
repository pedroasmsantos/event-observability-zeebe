package event.observability.zeebe.handlers

import event.observability.zeebe.entities.UserTask
import event.observability.zeebe.services.UserTaskService
import io.zeebe.client.api.response.ActivatedJob
import io.zeebe.client.api.worker.JobClient
import io.zeebe.client.api.worker.JobHandler
import io.zeebe.spring.client.annotation.ZeebeWorker
import org.springframework.stereotype.Component

@Component
class UserTaskHandler(val service: UserTaskService) : JobHandler {

    @ZeebeWorker
    override fun handle(client: JobClient?, job: ActivatedJob) {
        val headers = job.customHeaders
        val variables = job.variablesAsMap
        val name = headers.getOrDefault("name", job.elementId)
        val description = headers.getOrDefault("description", "")
        val formFields = headers["formFields"]
        val taskForm = headers["taskForm"]
        val assignee = headers["assignee"].toString()
        val candidateGroup = headers["candidateGroup"]

        val userTask = UserTask()
                .setKey(job.key)
                .setName(name)
                .setDescription(description)
                .setVariables(job.variables)
                //.setFormFields()
                //.setTaskForm()
                .setAssignee(assignee)
                //.setCandidateGroup(candidateGroup)
                .createdNow()

        service.createUserTask(userTask)
    }

}