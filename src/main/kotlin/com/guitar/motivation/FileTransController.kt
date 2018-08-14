package com.guitar.motivation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileTransController {

    @GetMapping("/upload")
    fun upload(): String {
        return "uploaded"
    }
}