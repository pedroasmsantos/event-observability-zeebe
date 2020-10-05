package event.observability.zeebe

import io.zeebe.spring.client.EnableZeebeClient
import io.zeebe.spring.client.ZeebeClientLifecycle
import io.zeebe.spring.client.annotation.ZeebeDeployment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableZeebeClient
@RestController
@ZeebeDeployment(classPathResources = ["bpmn/workflows.bpmn"])
class ZeebeEventObservabilityAppApplication

	@Autowired
	private val client: ZeebeClientLifecycle? = null

	@GetMapping("/status")
	fun getStatus(): String? {
		val topologyResponse = client!!.newTopologyRequest().send().join()
		return topologyResponse.toString()
	}

	fun main(args: Array<String>) {
		runApplication<ZeebeEventObservabilityAppApplication>(*args)
	}
