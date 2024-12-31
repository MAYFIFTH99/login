package hello.login.web;

import hello.login.web.argumentresolver.Login;
import hello.login.web.member.Member;
import hello.login.web.member.MemberRepository;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLoginV1(
            @CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) {
            return "home";
        }

        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Member member = (Member) sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        if (session == null) {
            return "home";
        }
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "/login/loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV3SpringAnnotation(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "/login/loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "/login/loginHome";
    }

}