package com.guitar.motivation

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import javax.servlet.http.HttpServletRequest

class AuthenticationException(message: String?) : Throwable(message)
class FileOperationException(message: String) : Throwable(message)

const val AUTH_FAILED_CODE = -1
const val FILE_OP_FAILED_CODE = -2

data class ErrorInfo(val requestUrl: String, val message: String?, val code: Int)

@ControllerAdvice
class ExceptionDispatcher {

    var log: Logger = LoggerFactory.getLogger(ExceptionDispatcher::class.java)

    @ExceptionHandler(AuthenticationException::class)
    @ResponseBody
    fun authExceptionHandler(request: HttpServletRequest, exception: AuthenticationException): ErrorInfo {
        this.log.error(exception.message)
        return ErrorInfo(request.requestURI, exception.message, AUTH_FAILED_CODE)
    }

    @ExceptionHandler(FileOperationException::class)
    @ResponseBody
    fun fileOperationFailedHandler(request: HttpServletRequest, exception: FileOperationException): ErrorInfo{
        this.log.error(exception.message)
        return ErrorInfo(request.requestURI, exception.message, FILE_OP_FAILED_CODE)
    }

    @ExceptionHandler(IOException::class)
    @ResponseBody
    fun fileExceptionHandler(request: HttpServletRequest, exception: IOException): ErrorInfo {
        this.log.error(exception.message)
        return ErrorInfo(request.requestURI, "file operation failed.", AUTH_FAILED_CODE)
    }
}