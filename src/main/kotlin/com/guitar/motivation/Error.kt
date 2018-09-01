package com.guitar.motivation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/err")
class ErrDispatcher {

    @RequestMapping("/accessDenied")
    @ResponseBody
    fun permissionDenied () :String {
        return "no permission, plz try login first."
    }
}