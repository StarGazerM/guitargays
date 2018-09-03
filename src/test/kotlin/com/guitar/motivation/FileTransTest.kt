package com.guitar.motivation

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@ExtendWith(SpringExtension::class)
@SpringBootTest
class FileServiceTest() {

    @Test
    fun test_file_service() {
        val fileInput = FileInputStream("D:\\workspace\\kotlin\\motivation\\motivation\\src\\test\\resources\\zs.png")
        launch(CommonPool) {
            val output = FileOutputStream(File("D:\\test.png"))
            fileInput.copyTo(output)
            output.flush()
            fileInput.close()
            output.close()
        }
    }
}