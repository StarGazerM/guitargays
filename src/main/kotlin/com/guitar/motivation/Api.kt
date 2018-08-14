package com.guitar.motivation

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/audio")
class AudioController(private val fileRepo: FileStoredRepo, private val userRepo: UserRepo) {

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long) = fileRepo.findById(id)
}

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepo: UserRepo) {

    @GetMapping("/info/{id}")
    fun userInfo(@PathVariable id : Long) : Map<String, String?> {
        val u = userRepo.findById(id).get()
        return mapOf("username" to u.username, "description" to u.description, "avatarPath" to u.avatarPath)
    }

    @PutMapping("/updateOne")
    fun modifyUser(user: User): String {
        if (userRepo.existsById(user.id)) {
            userRepo.save(user)
            return "success"
        } else {
            return "failed"
        }
    }
}