package com.aim.questionnaire.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-21 10:49
 * @since 0.1.0
 **/
public class CommonJsonException extends RuntimeException {
    private final JSONObject resultJson;

    /**
     * 调用时可以在任何代码处直接throws这个Exception,
     * 都会统一被拦截,并封装好json返回给前台
     *
     * @param errorEnum 以错误的ErrorEnum做参数
     */
    public CommonJsonException(String code, String msg) {
        this.resultJson = new JSONObject();
        resultJson.put("code", code);
        resultJson.put("msg", msg);
        resultJson.put("info", new JSONObject());
    }

    public CommonJsonException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }
}
