package com.guitar.motivation

import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.io.FileInputStream
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
class ApiTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var userRepo: UserRepo

    @MockBean
    private  lateinit var fileStoredRepo: FileStoredRepo

    @MockBean
    private lateinit var fileService: FileService

    @Test
    fun test_user_info_api(){
        val u = User(1,true, "test user","test", "1",
                "fake", null, mutableListOf())
        val m = FileStored(1,"test media","/a/a", u, AUDIO)
        whenever(userRepo.findById(1)).thenReturn(Optional.of(u))
        mockMvc.perform (get("/api/user/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.username").value("test user"))
    }

    @Test
    fun test_upload_file_api(){
        val u = User(1,true, "test user","test", "1",
                "fake", null, mutableListOf())

        val fileInput = FileInputStream("D:\\workspace\\kotlin\\motivation\\motivation\\src\\test\\resources\\zs.png")
        val mockFile = MockMultipartFile("file", fileInput)
        whenever(userRepo.findById(1)).thenReturn(Optional.of(u))

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file(mockFile)
                .param("uid", "1")
                .param("title", "test")
                .param("fileName", "test")
                .param("type", "1")
        )
                .andExpect(status().isOk)
                .andExpect(content().string("uploaded"))
    }
}