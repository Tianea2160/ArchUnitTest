package org.example.com.archunittest.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.AuditTable
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@AuditTable("user_log")
class User(
    @Id
    @Column(name = "user_id", nullable = false)
    val userId: Int,
    @Column(name = "name", nullable = false)
    val name: String,
)