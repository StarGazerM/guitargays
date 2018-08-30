package com.guitar.motivation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/file")
class MediaController(private val fileRepo: FileStoredRepo, private val userRepo: UserRepo) {

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long) = fileRepo.findById(id)

    @GetMapping("/listAll/{uid}")
    fun listUserMedia(@PathVariable uid: Long) = userRepo.findById(uid).get().files

    @GetMapping("/list{type}/{uid}")
    fun listUserAudio(@PathVariable type : String, @PathVariable uid: Long) : List<FileStored>{
        var t : Int = 0
        when (type) {
            "Audio" -> t = AUDIO
            "Video" -> t = VIDEO
        }
       // return fileRepo.findByUserIdAndType(uid, t)
        return emptyList()
    }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepo: UserRepo) {

    @GetMapping("/")
    fun listall() = userRepo.findAll()

    @GetMapping("/{id}")
    fun userInfo(@PathVariable id: Long): Map<String, String?> {
        val u = userRepo.findById(id).get()
        return mapOf("username" to u.username, "description" to u.description, "avatarPath" to u.avatarPath)
    }

    @PutMapping("/updateOne")
    fun modifyUser(user: User): String {
        return if (userRepo.existsById(user.id!!)) {
            userRepo.save(user)
            "success"
        } else {
            "failed"
        }
    }
}
