package com.guitar.motivation

import org.springframework.data.repository.CrudRepository

interface FileStoredRepo : CrudRepository<FileStored, Long>

interface UserRepo : CrudRepository<User, Long>