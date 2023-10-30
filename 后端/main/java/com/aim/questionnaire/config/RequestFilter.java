package com.aim.questionnaire.config;


import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.exception.CommonJsonException;
import com.aim.questionnaire.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestFilter extends OncePerRequestFilter implements Filter {

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //每个请求记录一个traceId,可以根据traceId搜索出本次请求的全部相关日志
            MDC.put("traceId", UUID.randomUUID().toString().replace("-", "").substring(0, 12));
            setUsername(request);
            setProductId(request);
            request = new ContentCachingRequestWrapper(request);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            //清理ThreadLocal
            MDC.clear();
        }
    }

    /**
     * 将url参数中的productId放入ThreadLocal
     */
    private void setProductId(HttpServletRequest request) {
        String productIdStr = request.getParameter("productId");
        if (!StringUtils.isBlank(productIdStr)) {
            MDC.put("productId", productIdStr);
        }
    }

    @Resource
    TokenService client;

    private void setUsername(HttpServletRequest request) {
        //通过token解析出username
        String token = request.getHeader("token");
        if (!StringUtils.isBlank(token)) {
            MDC.put("token", token);
            try {
                UserEntity info = client.getUserInfo();
                if (info != null) {
                    String username = info.getUsername();
                    MDC.put("username", username);
                }
            } catch (CommonJsonException e) {

            }

        }
    }
}