package com.guitar.motivation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

// this is just for testing boot and expose test

@Controller
class HelloController {

    @GetMapping("/")
    fun hello(model : Model) : String {
        model["foo"] = "bar"
        return "filetrans"
    }
}