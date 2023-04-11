package online.xiwang.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cameraInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //未登录放行get，管理员放行全部
        Object usertype = request.getSession().getAttribute("usertype");

        //System.out.println(request.getMethod());

        if(usertype==null) {              //未登录
            if (request.getMethod().equals("GET")) {   //get方法直接放行
                return true;
            } else if (request.getMethod().equals("POST")) {         //post请求 拦截
                response.setStatus(401);
                return false;
            }
            } else {             //放行
                return true;
        }
        response.setStatus(401);
        return false;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
