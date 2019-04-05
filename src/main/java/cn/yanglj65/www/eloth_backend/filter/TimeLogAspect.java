package cn.yanglj65.www.eloth_backend.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Aspect
@Component
public class TimeLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeLogAspect.class);
    public static long startTime;
    public static long takeTime;

    @Pointcut("execution(public * cn.yanglj65.www.eloth_backend.controller..*.*(..))")
    public void printTime() {
        logger.info("切面程序");
    }

    @Before("printTime()")
    public void before(JoinPoint joinPoint) {
        logger.info("执行前置切面");
        startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String requestAddress = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        String requestMethod = request.getMethod();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String remoteHost = request.getRemoteHost();
        logger.info("请求的URL=" + requestURI + "，请求的ip=" + requestAddress + "，请求的端口=" + remotePort + "，请求的方法=" + requestMethod + "，请求的包名=" + declaringTypeName + "，请求的类名=" + methodName);
        logger.info("请求的Host: " + remoteHost);
    }

    @After("printTime()")
    public void after() {
        takeTime = System.currentTimeMillis() - startTime;
        logger.info("执行后置切面");
    }

    @AfterReturning(value = "printTime()", returning = "returner")
    public void afterReturning(Object returner) {
        logger.info("本次操作耗时:{}ms ", takeTime);
        logger.info("返回结果:{}", returner.toString());
    }

    @Around("printTime()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        logger.info("环绕通知的方法名: {}",proceedingJoinPoint.getSignature().getName());
        try {
            Object object=proceedingJoinPoint.proceed();
            logger.info("返回结果:{}", object.toString());
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;

    }
}
