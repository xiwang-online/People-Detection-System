package online.xiwang.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class detectionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //post请求直接放行，get请求登录后放行
        Object usertype = request.getSession().getAttribute("usertype");

        //System.out.println(usertype);
        //System.out.println(usertype.toString());
        //System.out.println("getRequestURI():"+request.getRequestURI());      //返回除去host（域名或者ip）部分的路径

        if(request.getMethod().equals("POST")){   //post请求直接放行
            return true;
        } else if (usertype!=null) {             //登录了就放行
            return true;
        }else {                                  //未登录，拦截
            response.setStatus(401);
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
