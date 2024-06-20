package ollie.wecare.user.service;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.common.enums.Role;
import ollie.wecare.user.dto.*;
import ollie.wecare.user.entity.Center;
import ollie.wecare.user.entity.User;
import ollie.wecare.user.repository.CenterRepository;
import ollie.wecare.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ollie.wecare.common.base.BaseResponseStatus.*;
import static ollie.wecare.common.constants.Constants.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CenterRepository centerRepository;
    private final AuthService authService;
    private final RedisService redisService;


    // 회원가입
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<JwtDto> signup(SignupRequest signupRequest) {
        Center center = centerRepository.findByCenterIdxAndStatusEquals(signupRequest.centerIdx(), ACTIVE).orElseThrow(() -> new BaseException(INVALID_CENTER_IDX));

        if(!isValidIdentifier(signupRequest.identifier())) throw new BaseException(INVALID_IDENTIFIER);
        User newUser = createUser(signupRequest, center);
        userRepository.save(newUser);

        return new BaseResponse<>(authService.generateToken(newUser.getUserIdx()));
    }

    // User 생성
    private User createUser(SignupRequest signupRequest, Center center) {
        return new User(
                Role.Challenger,
                signupRequest.loginId(),
                encoder.encode(signupRequest.password()),
                signupRequest.nickname(),
                signupRequest.identifier(),
                center);
    }

    // Identifier 유효성 검사
    private static boolean isValidIdentifier(String identifier) {
        String pattern = "^[가-힣]{2,7}\\d{4}$";

        // 정규식 검사
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(identifier);

        return matcher.matches();
    }

    // 로그인
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<JwtDto> login(LoginRequest loginRequest) {
        User user = userRepository.findByLoginId(loginRequest.loginId()).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        if(!encoder.matches(loginRequest.password(), user.getPassword())) throw new BaseException(WRONG_PASSWORD);

        JwtDto jwtDto = authService.generateToken(user.getUserIdx());

        user.login();
        userRepository.save(user);
        return new BaseResponse<>(jwtDto);
    }

    // 로그아웃
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> logout(Long userIdx) {
        User user = userRepository.findByUserIdxAndStatusEquals(userIdx, ACTIVE).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        authService.logout(userIdx);

        user.logout();
        userRepository.save(user);
        return new BaseResponse<>(SUCCESS);
    }

    // 회원 탈퇴
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> signout(Long userIdx, SignOutRequest signoutRequest) {
        User user = userRepository.findByUserIdxAndStatusEquals(userIdx, ACTIVE).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        if (!encoder.matches(signoutRequest.password(), user.getPassword())) throw new BaseException(WRONG_PASSWORD);
        authService.signout(userIdx);

        user.signout();
        userRepository.save(user);
        return new BaseResponse<>(SUCCESS);
    }

    // access token 재발급
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<TokenResponse> reissueAccessToken(ReissueTokenRequest reissueTokenRequest) {
        User user = userRepository.findByLoginIdAndStatusEquals(reissueTokenRequest.loginId(), ACTIVE).orElseThrow(() -> new BaseException(NO_MATCH_USER));
        validateRefreshToken(reissueTokenRequest, user.getUserIdx());

        // refresh token이 유효한 경우 access token 재발급
        return new BaseResponse<>(new TokenResponse(authService.generateAccessToken(user.getUserIdx())));
    }

    // refreshToken 유효성 체크
    private void validateRefreshToken(ReissueTokenRequest reissueTokenRequest, Long userIdx) {
        String refreshTokenFromRequest = reissueTokenRequest.refreshToken();
        if (refreshTokenFromRequest == null || refreshTokenFromRequest.isEmpty())
            throw new BaseException(INVALID_REFRESH_TOKEN);

        String refreshTokenFromRedis = redisService.getToken(userIdx);
        if(!refreshTokenFromRedis.equals(refreshTokenFromRequest)) throw new BaseException(INVALID_REFRESH_TOKEN);
    }

    // 닉네임 중복 체크
    public BaseResponse<String> validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) throw new BaseException(DUPLICATED_NICKNAME);
        return new BaseResponse<>(SUCCESS);
    }

    // 아이디 중복 체크
    public BaseResponse<String> validateLoginId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) throw new BaseException(DUPLICATED_LOGIN_ID);
        return new BaseResponse<>(SUCCESS);
    }

    // 닉네임 변경
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> editNickname(Long userIdx, EditNicknameRequest editNicknameRequest) {
        User user = userRepository.findByUserIdxAndStatusEquals(userIdx, ACTIVE).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        if (editNicknameRequest.nickname().equals("") || editNicknameRequest.nickname().equals(" ")) throw new BaseException(INVALID_NICKNAME);
        validateNickname(editNicknameRequest.nickname());

        user.editNickname(editNicknameRequest.nickname());
        userRepository.save(user);
        return new BaseResponse<>(SUCCESS);
    }

    // 비밀번호 변경
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> editPassword(Long userIdx, EditPasswordRequest editPasswordRequest) {
        User user = userRepository.findByUserIdxAndStatusEquals(userIdx, ACTIVE).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        if (!encoder.matches(editPasswordRequest.password(), user.getPassword())) throw new BaseException(WRONG_PASSWORD);
        if (editPasswordRequest.newPassword().equals("") || editPasswordRequest.newPassword().equals(" ")) throw new BaseException(INVALID_PASSWORD);

        user.editPassword(editPasswordRequest.newPassword());
        userRepository.save(user);
        return new BaseResponse<>(SUCCESS);
    }

    // 마이페이지 조회
    public BaseResponse<MyPageResponse> getMyPage(Long userIdx) {
        User user = userRepository.findByUserIdxAndStatusEquals(userIdx, ACTIVE).orElseThrow(() -> new BaseException(INVALID_USER_IDX));

        MyPageResponse myPageResponse = new MyPageResponse(user.getNickname(), user.getLevel());
        return new BaseResponse<>(myPageResponse);
    }
}
