package cn.yanglj65.www.eloth_backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import cn.yanglj65.www.eloth_backend.entity.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResultUtil {
    public static Result resultGoodReturner(Object data){
        Result result = new Result();
        result.setErrCode(0);
        result.setMsg("ok");
        result.setData(data);
        return result;
    }

    public static Result resultGoodReturner( ){
        Result result = new Result();
        result.setErrCode(0);
        result.setMsg("ok");
        result.setData(null);
        return result;
    }

    public static Result resultBadReturner(String msg){
        Result result = new Result();
        result.setErrCode(1);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result resultBadReturner(Object data){
        Result result = new Result();
        result.setErrCode(1);
        result.setMsg("error");
        result.setData(data);
        return result;
    }

    public static Result resultBadReturner(String msg,Object data){
        Result result = new Result();
        result.setErrCode(1);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static String object2JsonStr(Object o){
        ObjectMapper mapper = new ObjectMapper();
        String ret = null;
        try {
            ret = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    public static void setResponse(String body, HttpServletResponse response){
        if(body==null)
            return;
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        if (body != null && !body.isEmpty()) {
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                os.write(body.getBytes("utf-8"));
                response.setContentLength(body.getBytes("utf-8").length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setResponseWithObject(Object o,HttpServletResponse response){
        setResponse(object2JsonStr(o),response);
    }

    public static void logger_request(Logger logger, HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" url " + request.getRequestURL() + "\n")
                .append(" remoteAddr " + request.getRemoteAddr() + "\n")
                .append(" remoteHost " + request.getRemoteHost() + "\n")
                .append(" remotePort " + request.getRemotePort() + "\n")
                .append(" remoteUser " + request.getRemoteUser() + "\n")
                .append(" queryString " + request.getQueryString() + "\n");
        Map<String,String[]> map = request.getParameterMap();
        if(map!=null){
            sb.append(" parameterMap " + object2JsonStr(map) + "\n");
        }
        logger.warn(sb.toString());
    }
}

