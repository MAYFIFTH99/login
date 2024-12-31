package hello.login.web.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        /**
         * @RequestMapping : HandlerMethod
         * 정적 리소스 : ResourceHttpRequestHandler
         */
        request.setAttribute(LOG_ID, uuid);
        if (handler instanceof HandlerMethod) { //RequsetMapping이 호출되는 컨트롤러 = HandlerMethod
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST[{}][{}][{}]", requestURI, uuid, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{]}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute("logId");

        log.info("RESPONSE[{}][{}][{}]", logId, requestURI, handler);

        if(ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
