package ollie.wecare.user.service;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.common.enums.Role;
import ollie.wecare.user.dto.JwtDto;
import ollie.wecare.user.dto.SignupRequest;
import ollie.wecare.user.entity.Center;
import ollie.wecare.user.entity.User;
import ollie.wecare.user.repository.CenterRepository;
import ollie.wecare.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ollie.wecare.common.base.BaseResponseStatus.INVALID_CENTER_IDX;
import static ollie.wecare.common.base.BaseResponseStatus.INVALID_IDENTIFIER;
import static ollie.wecare.common.constants.Constants.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CenterRepository centerRepository;
    private final AuthService authService;


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
}
