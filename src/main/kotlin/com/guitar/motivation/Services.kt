package com.guitar.motivation

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

    fun save(file: MultipartFile, fileInfo: FileStored) {
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

        // TODO(md5 need to be veryfied here)
    }
}

@Service
class WechatService()
