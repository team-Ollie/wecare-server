package ollie.wecare.user.controller;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.user.dto.JwtDto;
import ollie.wecare.user.dto.LoginRequest;
import ollie.wecare.user.dto.SignOutRequest;
import ollie.wecare.user.dto.SignupRequest;
import ollie.wecare.user.service.AuthService;
import ollie.wecare.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import static ollie.wecare.common.constants.RequestURI.user;

@RestController
@RequestMapping(user)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

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

    // 로그아웃
    @PatchMapping("/logout")
    public BaseResponse<String> logout() {
        return userService.logout(authService.getUserIdx());
    }

    // 회원 탈퇴
    @PatchMapping("/signout")
    public BaseResponse<String> signOut(@RequestBody SignOutRequest signoutRequest) {
        return userService.signout(authService.getUserIdx(), signoutRequest);
    }
}
