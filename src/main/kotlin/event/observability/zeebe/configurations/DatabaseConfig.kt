package event.observability.zeebe.configurations

import event.observability.zeebe.repositories.UserTaskRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcTemplate
import java.util.logging.Logger
import javax.annotation.PostConstruct

@Configuration
class DatabaseConfig(val jdbcTemplate: JdbcTemplate) {

    private val LOGGER = Logger.getLogger(DatabaseConfig::class.java.name)

    @Value("\${zeebe.client.worker.adminUsername}")
    private val adminUsername: String? = null

    @Value("\${zeebe.client.worker.adminPassword}")
    private val adminPassword: String? = null

    @Bean
    fun getUserTasksRepository(): UserTaskRepository {
        return UserTaskRepository(jdbcTemplate)
    }

    @PostConstruct
    fun initializeDatabase()
    {
        val repository = getUserTasksRepository()
        try {
            repository.checkIfTableExists()
        } catch (e: BadSqlGrammarException) {
            LOGGER.info("Unable to find user tasks table... Will attempt to create it...")
            repository.createUserTasksTable()
        }
    }

}