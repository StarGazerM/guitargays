package com.guitar.motivation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileTransController(private val fileRepo: FileStoredRepo,
                          private val userRepo: UserRepo,
                          private val fileService: FileService) {

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile,
               @RequestParam("uid") uid: Long,
               @RequestParam("title") title: String,
               @RequestParam("fileName") name: String,
               @RequestParam("type") type: Int): String {
        if (!userRepo.findById(uid).isPresent) {
            return "failed"
        }
        val f = FileStored(title = title, type = type, path="/${uid}/$title", user = userRepo.findById(uid).get())
        fileRepo.save(f)
        fileService(file, name, path="/${uid}/$title")

        return "uploaded"
    }
}