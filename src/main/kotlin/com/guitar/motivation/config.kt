package com.guitar.motivation

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("blog")
class ConfigProperties {

    class FileSetting {
        var basepath: String? = ""
    }
}