package hello.login.web.session;

import hello.login.web.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() throws Exception {
        //given - 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //when 세션 조회
        Object session = sessionManager.getSession(request);

        //then
        assertThat(session).isEqualTo(member);

        sessionManager.clearSession(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }

}