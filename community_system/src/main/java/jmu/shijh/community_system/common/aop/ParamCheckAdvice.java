package jmu.shijh.community_system.common.aop;


import jmu.shijh.community_system.common.annotation.MultiRequestBody;
import jmu.shijh.community_system.common.annotation.ParamCheck;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.exception.ParamCheckException;
import jmu.shijh.community_system.common.util.Str;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Order(2)
public class ParamCheckAdvice {

    @Pointcut("@annotation(jmu.shijh.community_system.common.annotation.ParamCheck)")
    public void pointCut() {
    }

    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> targetClass) {
        return ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(targetClass);
    }

    private boolean isAllowCheck(ParamCheck anno, String paramName) {
        if (anno.include().length > 0) {
            return Arrays.asList(anno.include()).contains(paramName);
        } else if (anno.exclude().length > 0) {
            return !Arrays.asList(anno.exclude()).contains(paramName);
        }
        return true;
    }

    private void throwException(ParamCheck anno) throws ParamCheckException {
        throw new ParamCheckException(anno == null ? "Unknown Error!" : anno.value());
    }

    private boolean isWrongParam(Object param, ParamCheck anno) {
        String regexp = anno.regexp();
        boolean b1 = param == null || Str.empty(param.toString());
        boolean matchRegex = true;
        if (!Str.empty(regexp)) {
            b1 = false;
            if (param instanceof String) {
                String p = (String) param;
                matchRegex = p.matches(regexp) || Str.empty(p) && anno.empty();
            }
        }
        return b1 || !matchRegex;
    }

    private boolean hasAllowedField(Class<?> clazz, ParamCheck anno) {
        for (Field field : clazz.getDeclaredFields()) {
            if (isAllowCheck(anno, field.getName())) return true;
        }
        return false;
    }

    @Before(value = "ParamCheckAdvice.pointCut()")
    public void paramCheck(JoinPoint jp) throws ParamCheckException, IllegalAccessException {
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Class[] argTypes = signature.getParameterTypes();
        String[] argNames = signature.getParameterNames();
        Parameter[] parameters = signature.getMethod().getParameters();
        ParamCheck anno = getAnnotation(jp, ParamCheck.class);
        String regexp = anno.regexp();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String argName = argNames[i];
            Class<?> argType = argTypes[i];
            if (arg instanceof HttpSession || arg instanceof ModelAndView || arg instanceof ModelMap
                    || arg instanceof ServletResponse || arg instanceof ServletRequest) continue;
            if (isAllowCheck(anno, argName) && isWrongParam(arg, anno)) {
                log.error("parameters {}.{} is null value"
                                + (Str.empty(regexp) ? "" : Str.f(" or didn't match [{}]", regexp)),
                        jp.getSignature().getName(), argName);
                throwException(anno);
            } else if (arg != null) {
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (isAllowCheck(anno, field.getName()) && isWrongParam(field.get(arg), anno)) {
                        log.error("parameter {}.{} of {} is null"
                                        + (Str.empty(regexp) ? "" : Str.f(" or didn't match [{}]", regexp)),
                                argName,
                                field.getName(),
                                jp.getSignature().getName());
                        throwException(anno);
                    }
                }
            } else if (hasAllowedField(argType, anno)) {
                RequestBody reqBody = parameters[i].getAnnotation(RequestBody.class);
                MultiRequestBody mulReqBody = parameters[i].getAnnotation(MultiRequestBody.class);
                if (reqBody != null && !reqBody.required() && !anno.regexp().isEmpty() && anno.empty()) {
                    return;
                }
                if (mulReqBody != null && !mulReqBody.required() && !anno.regexp().isEmpty() && anno.empty()) {
                    return;
                }
                log.error("parameters {}.{} which need to check is null value",
                        jp.getSignature().getName(), argName);
                throwException(anno);
            }
        }
    }

}
