package com.guitar.motivation

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.io.File
import java.io.FileOutputStream

@Service
class FileService : (MultipartFile, String, String) -> Boolean{

    override fun invoke (file: MultipartFile, fileName : String, path : String) : Boolean {
        launch(CommonPool) {
            val input = file.inputStream
            val output = FileOutputStream(File(path))
            input.copyTo(output)
            output.flush()
            input.close()
            output.close()
        }
        return true
    }
}

//@Service
//class AuthenticationService(private val userRepo: UserRepo) {
//
//    fun validate() {
//        val loginedUser = request.
//    }
//}