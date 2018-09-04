package com.guitar.motivation

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FileStoredRepo : JpaRepository<FileStored, Long>

interface UserRepo : JpaRepository<User, Long> {
    fun findByUsernameAndPassword(username: String, password: String): Optional<User>
    fun findByUsernameAndWechat(username: String, wechat: String): Optional<User>
}