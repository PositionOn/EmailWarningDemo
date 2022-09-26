package com.example.emailwarningdemo.Excetion;
import com.example.emailwarningdemo.Utils.Email;
import com.example.emailwarningdemo.Utils.EmailService;
import com.example.emailwarningdemo.Utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;


/**
 * Created by stf.
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ProfileConfig profileConfig;

    private String[] email = new String[]{"215063628@qq.com"};

    @ResponseBody
    @ExceptionHandler
    private R handlerException(Exception e,HttpServletRequest request) {
        log.error("请求地址：" + request.getRequestURL());
        String requstBody = "";

        if (request.getMethod().equals("GET")) {
            requstBody = parseParams(request);
        } else {
            //post请求兼容非json格式请求体
            try {
                requstBody  = ((RequestWrapper)request).getRequestBody();
            } catch (Exception exception){
                requstBody =  parseParams(request);
            }

        }
        log.error("请求体:{}",requstBody);
        //本地环境不发邮件
        if (ProfileConfig.PRO_PROFILE.equals(profileConfig.getActiveProfile())){
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            Email mail = new Email();
            mail.setEmail(email);
            mail.setSubject("异常告警邮件通知");
            mail.setContent(stringWriter.toString());
            // mailService.send(mail);//发送普通邮件
            mail.setTemplate("notifyEmail.ftl");
            HashMap<String, Object> mapParam = new HashMap<>(); //自定义模板参数，用于在ftl中接收展示
            mapParam.put("requestUrl", request.getRequestURL());
            mapParam.put("exceptionMessage", e.getMessage());
            mapParam.put("exceptionClass", e.getClass());
            mapParam.put("method", request.getMethod());
            mapParam.put("requestBody",requstBody);
            mail.setKvMap(mapParam);
            emailService.sendFreemarker(mail);//发送模板邮件
        }

        log.error(e.getMessage(), e);
        return R.failed(e.getMessage());
    }


    /**
     * 获取get 请求信息
     *
     * @param request 请求
     * @return 结果
     */
    public static String parseParams(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = (String) parameterNames.nextElement();
            String value = request.getParameter(name);
            stringBuilder.append(name).append("=").append(value).append("&");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


}
