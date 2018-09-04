package com.guitar.motivation.controller

import com.guitar.motivation.User
import com.guitar.motivation.WechatService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/wechat")
class LoginController(private val wechatService: WechatService){

    @PostMapping("/login")
    @ResponseBody
    fun wechatLogin(@RequestParam accessToken: String,
                    @RequestParam username: String,
                    @RequestParam openID: String){

    }

}