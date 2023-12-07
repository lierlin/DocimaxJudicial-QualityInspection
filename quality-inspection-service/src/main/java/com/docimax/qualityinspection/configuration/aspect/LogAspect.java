package com.docimax.qualityinspection.configuration.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xufubiao
 * @Description: 日志aop
 * @Date: 2019/10/15 9:05
 */
@Aspect
@Component
@Order(1)
public class LogAspect {
//    private final static Logger auditLogger = LoggerUtils.getAuditLogger();
//    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//    @Autowired
//    // private LogService logService;
//
//    /**
//     * ..表示包及子包 该方法代表controller层的所有方法
//     * Pointcut定义时，还可以使用&&、||、! 这三个运算
//     */
//    @Pointcut("@annotation(com.docimax.erms.common.annotation.Log)")
//    public void controllerMethod() {
//
//    }
//
//    /**
//     * 后置通知
//     */
//    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
//    public void logResultVoInfo(JoinPoint joinPoint, Object result) {
//        handleLog(joinPoint, null, result);
//    }
//
//    /**
//     * 异常通知
//     *
//     * @param joinPoint
//     * @param exception
//     */
//    @AfterThrowing(value = "controllerMethod()", throwing = "exception")
//    public void doAfterThrowingAdvice(JoinPoint joinPoint, Exception exception) {
//        handleLog(joinPoint, exception, null);
//    }
//
//    /**
//     * 处理log
//     *
//     * @param joinPoint
//     * @param exception
//     * @param result
//     */
//    protected void handleLog(final JoinPoint joinPoint, final Exception exception, Object result) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes == null) {
//            return;
//        }
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method targetMethod = methodSignature.getMethod();
//        Log logAnnotation = methodSignature.getMethod().getAnnotation(Log.class);
//
//        CommonLogModel aLog = new CommonLogModel();
//        int type = logAnnotation.logType();
//        int businessType = logAnnotation.businessType();
//        aLog.setLogType(type);
//        aLog.setBusinessType(businessType);
//
//        aLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        try {
//            String uname = URLDecoder.decode(HeaderParamHolder.getInstance().getUName(), StandardCharsets.UTF_8.toString());
//            aLog.setOperateUser(uname);
//            String deptName = URLDecoder.decode(HeaderParamHolder.getInstance().getDeptName(), StandardCharsets.UTF_8.toString());
//            aLog.setOperateUserDept(deptName);
//        } catch (UnsupportedEncodingException e) {
//            logger.error("URLDecoder.decode error", e);
//        }
//        setBusinessName(joinPoint, targetMethod, aLog);
//        if (joinPoint.getArgs().length == 0) {
//            aLog.setParams("{}");
//        } else if (joinPoint.getArgs().length == 1) {
//            Object arg = joinPoint.getArgs()[0];
//            // 是否实现校验父接口
//            if (!(arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile)) {
//                aLog.setParams(JSON.toJSONString(arg));
//            }
//        } else {
//            List<Object> list = new ArrayList<>();
//            for (Object arg : joinPoint.getArgs()) {
//                // 是否实现校验父接口
//                if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
//                    continue;
//                }
//                list.add(arg);
//            }
//            aLog.setParams(JSON.toJSONString(list));
//        }
//        if (result != null) {
//            aLog.setResult(JSON.toJSONString(result));
//        }
//        if (exception != null) {
//            aLog.setLogStatus(LogStatusEnum.ABNORMAL.code);
//            aLog.setError(exception.getMessage());
//        } else {
//            aLog.setLogStatus(LogStatusEnum.NORMAL.code);
//        }
//        if (type == 0) {
//            logger.info("{}", value("sysLog", aLog));
//        } else {
//            auditLogger.info("{}", value("auditLog", aLog));
//        }
//    }
//
//
//    /**
//     * 设置业务模块
//     *
//     * @param joinPoint
//     * @param targetMethod
//     * @param operationalLog
//     */
//    private void setBusinessName(JoinPoint joinPoint, Method targetMethod, CommonLogModel operationalLog) {
//        // 类注解
//        Tag api = AnnotationUtil.getAnnotation(joinPoint.getSignature().getDeclaringType(), Tag.class);
//        // 方法注解
//        Operation annotation = AnnotationUtil.getAnnotation(targetMethod, Operation.class);
//        if (StringUtils.isNotBlank(api.name())) {
//            operationalLog.setOperateContent(api.name() + ":" + annotation.summary());
//        } else {
//            operationalLog.setOperateContent(annotation.summary());
//        }
//    }
}
