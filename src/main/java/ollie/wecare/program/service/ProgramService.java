package ollie.wecare.program.service;

import lombok.RequiredArgsConstructor;
import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.challenge.repository.ChallengeRepository;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.enums.Role;
import ollie.wecare.program.dto.GetProgramRes;
import ollie.wecare.program.dto.PostProgramReq;
import ollie.wecare.program.entity.Program;
import ollie.wecare.program.repository.ProgramRepository;
import ollie.wecare.user.entity.User;
import ollie.wecare.user.repository.UserRepository;
import ollie.wecare.user.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

import static ollie.wecare.common.base.BaseResponseStatus.INVALID_ROLE;
import static ollie.wecare.common.base.BaseResponseStatus.INVALID_USER_IDX;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final AuthService authService;
    private final ProgramRepository programRepository;
    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;
    public List<GetProgramRes> getPrograms(Long month) {
        //programRepository.findByDueDate
        return null;
    }

    public void saveProgram(PostProgramReq postProgramReq) throws BaseException {
        User user = userRepository.findById(authService.getUserIdx()).orElseThrow(()-> new BaseException(INVALID_USER_IDX));
        if(!user.getRole().equals(Role.Admin)) throw new BaseException(INVALID_ROLE);

        Program program = PostProgramReq.toProgram(postProgramReq);
        programRepository.save(program);

        this.saveChallenge(PostProgramReq.toProgram(postProgramReq), user);
    }

    public void saveChallenge(Program program, User user) {
        challengeRepository.save(Challenge.builder().program(program).name(program.getName()).attendanceRate(0L)
                .admin(user).host(program.getHost()).totalNum(0L).build());
    }
}
