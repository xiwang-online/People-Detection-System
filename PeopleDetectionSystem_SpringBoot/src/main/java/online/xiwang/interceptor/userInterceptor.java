package online.xiwang.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //除了登录请求外，其他请求只有管理员才能访问
        Object usertype = request.getSession().getAttribute("usertype");

        //System.out.println(request.getRequestURI());

        if(usertype==null) {              //未登录
            if(request.getRequestURI().equals("/api/user/login")){   //登录请求则放行
                return true;
            }else{                                                  //其他情况 拦截
                response.setStatus(401);
                return false;
            }
        }else if(usertype.toString().equals("administrator")){             //管理员放行
            return true;
        }else if(usertype.toString().equals("visitor")){
            if(request.getRequestURI().equals("/api/user/login")){   //登录后重新登录也可以
                    return true;
            }else{                                                  //其他情况 拦截
                    response.setStatus(403);
                    return false;
            }

        }else {
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
