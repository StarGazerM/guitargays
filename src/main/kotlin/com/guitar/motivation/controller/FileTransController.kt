package com.guitar.motivation.controller

import com.guitar.motivation.FileService
import com.guitar.motivation.FileStored
import com.guitar.motivation.UserRepo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

data class FileStoredVO(val uid: Long, val title: String, val fileName: String, val type: Int)

@RestController
class FileTransController(private val userRepo: UserRepo,
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

        val fileInfo = FileStored(title = title, type = type, path = "/${uid}/$title", user = userRepo.findById(uid).get())
        fileService.save(file, fileInfo, "")
        return "uploaded"
    }
}