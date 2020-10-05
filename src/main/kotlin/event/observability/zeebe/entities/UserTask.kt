package event.observability.zeebe.entities

import java.io.Serializable
import java.util.*

class UserTask : Serializable {
    private var key: Long? = null
    private var name: String? = null
    private var description: String? = null
    private var variables: String? = null
    private var assignee: String? = null
    private var createdAt: Date? = null

    fun getKey(): Long? {
        return key
    }

    fun setKey(key: Long?): UserTask {
        this.key = key
        return this
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?): UserTask {
        this.name = name
        return this
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?): UserTask {
        this.description = description
        return this
    }

    fun getVariables(): String? {
        return variables
    }

    fun setVariables(variables: String?): UserTask {
        this.variables = variables
        return this
    }

    fun getAssignee(): String? {
        return assignee
    }

    fun setAssignee(assignee: String?): UserTask {
        this.assignee = assignee
        return this
    }

    fun getCreatedAt(): Date? {
        return createdAt
    }

    fun setCreatedAt(created: Date?): UserTask {
        this.createdAt = created
        return this
    }

    fun createdNow(): UserTask {
        return setCreatedAt(Date())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that: UserTask = other as UserTask
        return getKey() == that.getKey() && getName() == that.getName() && getDescription() == that.getDescription() &&
                getVariables() == that.getVariables() && getAssignee() == that.getAssignee() && getCreatedAt() == that.getCreatedAt()
    }

    override fun hashCode(): Int {
        return Objects.hash(getKey(), getName(), getDescription(), getVariables(), getAssignee(), getCreatedAt())
    }

}