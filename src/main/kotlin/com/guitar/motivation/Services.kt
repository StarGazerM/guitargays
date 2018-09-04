package com.guitar.motivation

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.security.MessageDigest

@Service
class FileService(@Autowired private val fileStoredRepo: FileStoredRepo) {

    fun saveAsync (file: MultipartFile, fileInfo: FileStored) {
        launch(CommonPool) {
            val input = file.inputStream
            val output = FileOutputStream(File(fileInfo.path))
            try {
                input.copyTo(output)
                output.flush()
            } catch (e: IOException) {
                print("file exception")
            }finally {
                input.close()
                output.close()
            }
        }.invokeOnCompletion {
            fileStoredRepo.save(fileInfo)
            print("finish save")
        }
        print("leave save function")
    }

    fun save(file: MultipartFile, fileInfo: FileStored, md5value: String) {
        val input = file.inputStream
        val f = File(fileInfo.path)
        val output = FileOutputStream(f)
        try {
            input.copyTo(output)
            // TODO(md5 need to be veryfied here)
            val byteBuffer = output.channel.map(FileChannel.MapMode.READ_ONLY, 0, file.size)
            output.flush()
        } catch (e: IOException) {
            print("file exception")
        } finally {
            input.close()
            output.close()
        }
    }

    fun checkMD5(byteBuffer: ByteBuffer, md5value: String) {
        val messageDigest = MessageDigest.getInstance("MD5")
        messageDigest.update(byteBuffer);
        val sum = BigInteger(1, messageDigest.digest()).toString()

        if(sum != md5value) {
            throw FileOperationException("check sum failed")
        }
    }
}

@Service
class WechatService(private val userRepo: UserRepo) {
    fun saveWechatUser(accessToken: String, userInfo: User) {
        // check form the api of the wechat
        userRepo.save(userInfo)
    }

    fun checkWechatUser(username: String, wechat: String): User {
        val u = userRepo.findByUsernameAndWechat(username, wechat)
        if (u.isPresent) {
            return u.get()
        } else{
            throw AuthenticationException("wechat user does not exist.")
        }
    }
}
