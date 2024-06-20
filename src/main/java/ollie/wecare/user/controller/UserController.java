package ollie.wecare.user.controller;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.user.dto.JwtDto;
import ollie.wecare.user.dto.LoginRequest;
import ollie.wecare.user.dto.SignupRequest;
import ollie.wecare.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ollie.wecare.common.constants.RequestURI.user;

@RestController
@RequestMapping(user)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping(value = "/signup")
    public BaseResponse<JwtDto> signup(@RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<JwtDto> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
