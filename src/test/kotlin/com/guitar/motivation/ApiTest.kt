package com.guitar.motivation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import com.nhaarman.mockito_kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
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
    fun `test_user_info_api`(){
        val u = User(1,true, "test user","test", "1",
                "fake", null, mutableListOf())
        val m = FileStored(1,"test media","/a/a", u, AUDIO)
        whenever(userRepo.findById(1)).thenReturn(Optional.of(u))
        mockMvc.perform (get("/api/user/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.username").value("test user"))
    }
}