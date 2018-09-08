package com.guitar.motivation

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import sun.rmi.transport.ObjectTable.getTarget
import javassist.compiler.MemberResolver.getModifiers
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import java.lang.reflect.Method
import java.lang.reflect.Modifier


@Aspect
@Component
@Order(3)
internal class WebLogAspect {
    private val logger: Logger = LoggerFactory.getLogger(WebLogAspect::class.java)

    @Pointcut("execution(* com.guitar.motivation.controller.*.*(..))")
    fun webLog(){}

    @Before("webLog()")
    fun logBefore(joinPoint: JoinPoint){
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = attributes.request
        logger.info("URL : {}", request.requestURL.toString())
        logger.info("HTTP_METHOD : {}", request.method)
        logger.info("IP : {}" , request.remoteAddr)
        logger.info("CLASS_METHOD : {}", joinPoint.signature.declaringTypeName + "." + joinPoint.signature.name)
        logger.info("ARGS : {}", Arrays.toString(joinPoint.args))
    }

    @AfterReturning(pointcut = "webLog()", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint, result: Object){
        logger.info("METHOD_NAME : {}", joinPoint.signature.name)
        logger.info("CLASS_NAME : {}", joinPoint.signature.declaringType.simpleName)
        logger.info("CLASS_NAME : {}", joinPoint.signature.declaringTypeName)
        logger.info("METHOD_DECLARE : {}", Modifier.toString(joinPoint.signature.modifiers))
        logger.info("PARAM_LIST :{}", joinPoint.args.toString())
        logger.info("RESULT :{}", result)
    }
}

enum class AuthType {
    USER, ALL
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthTag(val authority: AuthType)

@Aspect
@Configuration
@Order(2)
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