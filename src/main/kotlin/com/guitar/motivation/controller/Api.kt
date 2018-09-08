package com.guitar.motivation.controller

import com.guitar.motivation.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

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
        return userRepo.findById(uid).get().files.filter { it.type == t }
    }

    @DeleteMapping("/delete/{fid}")
    fun deleteOne(@PathVariable fid : Long) = fileRepo.deleteById(fid)
}

@RestController
@RequestMapping("/api/user")
class UserApiController(private val userRepo: UserRepo) {

    @GetMapping("/")
    @AuthTag(AuthType.USER)
    fun listAll() = userRepo.findAll()

    @GetMapping("/{id}")
    @ResponseBody
    fun userInfo(@PathVariable id: Long): Map<String, String?> {
        val u = userRepo.findById(id).get()
        return mapOf("username" to u.username, "description" to u.description, "avatarPath" to u.avatarPath)
    }

    @AuthTag(AuthType.USER)
    @PutMapping("/updateOne")
    fun modifyUser(user: User, @SessionAttribute currentUser: User): String {
        return if (userRepo.existsById(user.id!!) && user.id == currentUser.id) {
            userRepo.save(user)
            "success"
        } else {
            "failed"
        }
    }

    @PostMapping("/login")
    fun login(request: HttpServletRequest,
              @RequestParam username: String,
              @RequestParam password: String){
        val session = request.session
        val u = userRepo.findByUsernameAndPassword(username, password)
        if (u.isPresent) {
            session.setAttribute("currentUser", u.get())
        } else {
          throw AuthenticationException("Authenticated failed: username or password is wrong.")
        }
    }
}

// test the environment outside
@Controller
class HelloController {
    @GetMapping("/")
    fun hello(model : Model) : String {
        model["foo"] = "bar"
        return "filetrans"
    }
}