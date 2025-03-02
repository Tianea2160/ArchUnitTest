package org.example.com.archunittest.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.AuditTable

@Entity
@Table(name = "team")
@AuditTable("team_log")
class Team(
    @Id
    @Column(name = "time_id", nullable = false)
    val teamId: Int,
    val name : String,
)