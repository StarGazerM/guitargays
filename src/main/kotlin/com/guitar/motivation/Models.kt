package com.guitar.motivation

import javax.persistence.*

enum class FileType {
    VIDEO, AUDIO
}

@Entity
data class User(
        @Id @GeneratedValue val id: Long,
        val login: Boolean,
        val username : String,
        val password: String,
        val wechat: String,
        val avatarPath: String,
        val description: String? = null
)

@Entity
data class FileStored(
        @Id @GeneratedValue val id: Long? = null,
        val title : String,
        val path : String,
        val owner: User,
        @ManyToOne @JoinColumn val type: FileType
)

