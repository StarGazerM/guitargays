package com.guitar.motivation

import javax.persistence.*

const val VIDEO : Int = 0
const val AUDIO : Int = 1

@Entity
data class User(
        @Id @GeneratedValue val id: Long? = null,
        val login: Boolean,
        val username : String,
        val password: String,
        val wechat: String,
        val avatarPath: String,
        val description: String? = null,
        @OneToMany
        var files: MutableList<FileStored>
)

@Entity
data class FileStored(
        @Id @GeneratedValue val id: Long? = null,
        val title : String,
        val path : String,
        @ManyToOne @JoinColumn val user: User,
        val type: Int
)

