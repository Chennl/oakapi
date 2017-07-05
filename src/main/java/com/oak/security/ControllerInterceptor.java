//package com.oak.security;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// * 拦截器：记录用户操作日志
// *
// * Created by Chennl on 7/5/2017.
// */
//@Aspect
//@Component
//public class ControllerInterceptor {
//    private final static Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);
//    /**
//     * 定义拦截规则： 拦截com.oak.controller包下面的所有类中，有@RequestMapping注解的方法
//     */
////    @Pointcut("execution(com.oak.controller.CustomerController.getAllCustomers()) ")
////     public void controllerMethodPointcut(){}
//
//    /**
//     * 拦截器具体实现
//     * @param
//     * @return JsonResult(被拦截方法的执行结果，或需要登录和错误提示)
//     */
//    @Before(value="com.oak.controller.CustomerController.getAllCustomers()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        // 记录下请求内容
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//    }
//    @After(value = "com.oak.controller.CustomerController.getAllCustomers()")
//    public void after(JoinPoint joinPoint) {
//        System.out.println("[Aspect1] after advise");
//    }
//
//
//}
