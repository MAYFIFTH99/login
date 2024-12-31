package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            log.info("LoginCheckFilter 시작{}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("blackList => 인증 체크 로직 실행 URI: {}", requestURI);
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("loginMember") == null) {

                    log.info("미인증 사용자 요청 {}", requestURI);
                    //login으로 redirect
                    response.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally{
            log.info("인증 체크 필터 종료{}", requestURI);
        }
    }

    /**
     * whiteList 인 경우 인증 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
