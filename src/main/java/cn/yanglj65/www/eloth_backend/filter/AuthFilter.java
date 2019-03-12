package cn.yanglj65.www.eloth_backend.filter;

import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.util.AccessUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
//@WebFilter(filterName = "AuthFilter",urlPatterns = {"/cloth/*","/user/*","/bug/*"})
public class AuthFilter implements Filter {
    private static final Set<String> ALLOWED_PATH = Collections.unmodifiableSet(new HashSet<>
            (Arrays.asList("/user/login", "/user/register")));
    @Autowired
    AccessUtil accessUtil;

    public static Logger logger=LoggerFactory.getLogger(AuthFilter.class);
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean isAllowedPath = ALLOWED_PATH.contains(path);
        if (isAllowedPath) {
            chain.doFilter(req, resp);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE,PATCH");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            String accessToken = request.getHeader("Authorization");
            User user =accessUtil.validateToken(accessToken);
            if (user != null) {
                request.setAttribute("USER", user);
                request.setAttribute("USER_ID", user.getId());
                chain.doFilter(req, resp);
            } else {
                Result result = AccessUtil.ACCESS_DENY();
                try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(response.getOutputStream(), "utf-8");
                     PrintWriter printWriter = new PrintWriter(outputStreamWriter, true)
                ) {
                    ObjectMapper mapper = new ObjectMapper();
                    String resultStr = mapper.writeValueAsString(result);
                    printWriter.write(resultStr);
                    printWriter.flush();
                } catch (UnsupportedEncodingException e) {
                   logger.error("过滤器返回失败信息"+e.getMessage(),e);
                } catch (IOException e) {
                    logger.error("过滤器返回失败信息"+e.getMessage(),e);
                }

            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
