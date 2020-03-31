package com.huang.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 记录日志aop
 * @author guangtou
 * @create 2020--02--05--18:55
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 以com.huang.controller下所有的类的所有方法作为切点
     */
    @Pointcut("execution(* com.huang.controller.*.*(..))")
    public void log(){}

    /**
     *切点之前执行,获取ip和请求的URL，记录到日志中
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
//        获取请求的request
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
//        获取切点的Controller包名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
//       获取请求的参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){

    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturun(Object result){

    }

    /**
     * 切点内部类，用于输出请求的url+ip+classMethod+args
     */
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
