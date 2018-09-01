package com.guitar.motivation

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
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
            val input = fileInput
            val output = FileOutputStream(File("D:\\test.png"))
            input.copyTo(output)
            output.flush()
            input.close()
            output.close()
        }
    }
}