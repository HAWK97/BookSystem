package com.hawk.book.interceptor;

import com.hawk.book.annotation.LoginRequired;
import com.hawk.book.data.ResultEnum;
import com.hawk.book.data.entity.Ticket;
import com.hawk.book.data.entity.User;
import com.hawk.book.exception.MyException;
import com.hawk.book.handle.UserContextHolder;
import com.hawk.book.mapper.TicketDao;
import com.hawk.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * LoginInterceptor
 *
 * @author wangshuguang
 * @since 2017/12/26
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (request.getRequestURI().contains("swagger")) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean isLoginRequired = isAnnotationPresent(method, LoginRequired.class);
        if (isLoginRequired) {
            // 调用需要登录的接口时进行判断
            String ticketStr = getToken(getAuthHeader(request));
            Ticket ticket = ticketDao.findByTicket(ticketStr);
            if (ticket != null) {
                if (ticket.getExpired().getTime() < System.currentTimeMillis()) {
                    throw new MyException(ResultEnum.NEED_LOGIN);
                }
                User user = userService.getInfo(ticket.getUserId());
                if (user == null) {
                    throw new MyException(ResultEnum.NEED_LOGIN);
                }
                UserContextHolder.set(user);
            } else {
                throw new MyException(ResultEnum.NEED_LOGIN);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    private boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotationClass) {
        return method.getDeclaringClass().isAnnotationPresent(annotationClass) || method.isAnnotationPresent(annotationClass);
    }

    private String getAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        log.info("authHeader" + authHeader);
        return authHeader;
    }

    private String getToken(String authHeader) {
        String token = null;
        if (StringUtils.isNotEmpty(authHeader)) {
            token = authHeader.split(" ")[1];
        }
        return token;
    }
}
