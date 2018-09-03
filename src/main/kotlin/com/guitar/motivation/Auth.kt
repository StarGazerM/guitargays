package com.guitar.motivation

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.SessionAttribute
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.reflect.Method

enum class AuthType {
    USER, ALL
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthTag(val authority: AuthType)

@Aspect
@Configuration
class AuthenticationAspect{

//    @Pointcut("execution(public * com.guitar.motivation..*.*(..))")
//    fun authenticate() {}

    @Before("@annotation(com.guitar.motivation.AuthTag)")
    fun isAccessedFunc(jointPoint: JoinPoint) {
        val attr = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val currentUser = attr.request.session.getAttribute("currentUser")
        val funcSignature = jointPoint.signature as MethodSignature
        val authType = getAuth(funcSignature.method)
        when (authType) {
            AuthType.ALL -> return
            AuthType.USER -> {
                if (currentUser == null) {
                    throw AuthenticationException("Authenticate failed: need to login first.")
                }
            }
        }
    }

    private fun getAuth(method: Method): AuthType {
        val au = method.annotations.find { it is AuthTag } as? AuthTag?
        return au?.authority ?: AuthType.ALL
    }
}