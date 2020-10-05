package event.observability.zeebe.repositories

import event.observability.zeebe.entities.UserTask
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.Timestamp

class UserTaskRepository(val jdbcTemplate: JdbcTemplate) {
    private val tableName = "USER_TASKS"

    fun checkIfTableExists() {
        jdbcTemplate.execute(getValidateUserTasksTableQuery())
    }

    fun createUserTasksTable() {
        jdbcTemplate.execute(getCreateUserTasksTableStatement())
    }

    fun insertUserTask(task: UserTask): Int {
        return jdbcTemplate.update(getInsertUserTaskStatement(),
                task.getKey(),
                task.getName(),
                task.getDescription(),
                task.getVariables(),
                task.getAssignee(),
                Timestamp.from(task.getCreatedAt()?.toInstant()))
    }

    fun findUserTasksByAssignee(assignee : String?) : List<UserTask>{
        val queryParameters = arrayOf(assignee)
        return jdbcTemplate.query(getFindUserTasksByAssigneeQuery(), queryParameters, extractData())
    }

    fun removeUserTaskByKey(jobKey: Long){
        jdbcTemplate.update(getDeleteUserTaskByJobKeyStatement(), jobKey)
    }

    private fun getValidateUserTasksTableQuery(): String {
        return "SELECT 1 FROM $tableName"
    }

    private fun getCreateUserTasksTableStatement(): String {
        return "CREATE TABLE $tableName ( " +
                "key BIGINT, " +
                "name VARCHAR(100), " +
                "description VARCHAR(255), " +
                "variables VARCHAR(255), " +
                "assignee VARCHAR(100), " +
                "createdAt TIMESTAMP" +
                ")"
    }

    private fun getInsertUserTaskStatement() : String{
        return "INSERT INTO $tableName " +
                "(key, name, description, variables, assignee, createdAt) " +
                "VALUES(?, ?, ?, ?, ?, ?)"
    }

    private fun getFindUserTasksByAssigneeQuery() : String{
        return "SELECT key, name, description, variables, assignee, createdAt " +
                "FROM $tableName " +
                "WHERE assignee=?" +
                "ORDER BY createdAt ASC"
    }

    private fun getDeleteUserTaskByJobKeyStatement(): String{
        return "DELETE FROM $tableName WHERE key=?"
    }

    private fun extractData(): RowMapper<UserTask> {
        return RowMapper{ resultSet: ResultSet, _: Int ->
            UserTask()
                .setKey(resultSet.getLong("key"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setVariables(resultSet.getString("variables"))
                .setAssignee(resultSet.getString("assignee"))
                .setCreatedAt(resultSet.getTimestamp("createdAt"))

        }
    }

}