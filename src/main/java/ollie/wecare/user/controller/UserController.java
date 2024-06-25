package ollie.wecare.user.controller;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.user.dto.*;
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
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
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

    // access token 재발급
    @PostMapping("/reissue-token")
    public BaseResponse<TokenResponse> reissueToken(@RequestBody ReissueTokenRequest reissueTokenRequest) {
        return userService.reissueAccessToken(reissueTokenRequest);
    }

    // 닉네임 중복 체크
    @PostMapping("/nickname")
    public BaseResponse<String> validateNickname(@RequestBody NicknameRequest nicknameRequest) {
        return userService.validateNickname(nicknameRequest.nickname());
    }

    // 아이디 중복 체크
    @PostMapping("/loginId")
    public BaseResponse<String> validateLoginId(@RequestBody LoginIdRequest loginIdRequest) {
        return userService.validateLoginId(loginIdRequest.loginId());
    }

    // 닉네임 변경
    @PatchMapping("/editNickname")
    public BaseResponse<String> modifyNickname(@RequestBody EditNicknameRequest editNicknameRequest) {
        return userService.editNickname(authService.getUserIdx(), editNicknameRequest);
    }

    // 비밀번호 변경
    @PatchMapping("/editPassword")
    public BaseResponse<String> modifyPassword(@RequestBody EditPasswordRequest editPasswordRequest) {
        return userService.editPassword(authService.getUserIdx(), editPasswordRequest);
    }

    // 마이페이지 조회
    @GetMapping("/myPage")
    public BaseResponse<MyPageResponse> getMyPage() {
        return userService.getMyPage(authService.getUserIdx());
    }

    // 회원가입 화면 조회
    @GetMapping("/signupView")
    public BaseResponse<SignupViewResponse> getSignupView() {
        return userService.getSignupView();
    }
}
