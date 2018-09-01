package com.guitar.motivation

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Auth

// 使用环绕通知实现
@Aspect
@Component
class AuthenticationAspect(@Autowired private val userRepo: UserRepo) {

    @Pointcut("execution(* com.guitar.motivation.Api.*(..))")
    fun auth() {}

    @Around("auth()")
    fun isAccessedFunc(jointPoint: ProceedingJoinPoint) {

    }

    private fun checkAnnotation()
}