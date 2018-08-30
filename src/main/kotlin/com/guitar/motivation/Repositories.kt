package com.guitar.motivation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface FileStoredRepo : CrudRepository<FileStored, Long>
interface UserRepo : JpaRepository<User, Long>