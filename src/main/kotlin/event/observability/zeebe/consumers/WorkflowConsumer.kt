package event.observability.zeebe.consumers

import com.fasterxml.jackson.databind.ObjectMapper
import event.observability.zeebe.events.OrderEvent
import io.zeebe.spring.client.ZeebeClientLifecycle
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@Service
class WorkflowConsumer(val client: ZeebeClientLifecycle){
    private val logger = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["zeebequeue"])
    fun receivedMessage(
            event: OrderEvent,
            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) routingKey: String
    ) {
        println("Received the message $event due to the binding $routingKey")

        val jsonString: String = ObjectMapper().writeValueAsString(event)

        try {
            client
                .newPublishMessageCommand()
                .messageName(routingKey)
                .correlationKey(event.OrderId)
                .variables(jsonString)
                .send()
                .join()

            logger.info("Event $event successfully correlated to process instance ${event.OrderId}.")
        } catch (e: Exception) {
            logger.warn("Event $event couldn't be related with any workflow. Will be correlated to the error process.")

            client
                .newPublishMessageCommand()
                .messageName("NOT_CORRELATED_MSGS")
                .correlationKey(event.OrderId)
                .variables(jsonString)
                .send()
                .join()
        }
    }
}