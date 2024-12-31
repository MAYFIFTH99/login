package hello.login.web.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
