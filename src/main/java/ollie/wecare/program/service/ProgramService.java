package ollie.wecare.program.service;

import lombok.RequiredArgsConstructor;
import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.challenge.repository.ChallengeRepository;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.common.enums.Role;
import ollie.wecare.common.enums.TagEnum;
import ollie.wecare.program.dto.GetProgramDetailRes;
import ollie.wecare.program.dto.GetProgramRes;
import ollie.wecare.program.dto.PostProgramReq;
import ollie.wecare.program.dto.ProgramListResponse;
import ollie.wecare.program.entity.Program;
import ollie.wecare.program.entity.Tag;
import ollie.wecare.program.repository.ProgramRepository;
import ollie.wecare.program.repository.TagRepository;
import ollie.wecare.user.entity.User;
import ollie.wecare.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static ollie.wecare.common.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final UserService userService;
    private final ProgramRepository programRepository;
    private final ChallengeRepository challengeRepository;

    private final TagRepository tagRepository;

    // 프로그램 월별 조회
    public List<GetProgramRes> getPrograms(Long year, Long month) {
        int y = year != null && year > 0 ? year.intValue() : LocalDateTime.now().getYear();
        int m = month != null && month > 0 ? month.intValue() : LocalDateTime.now().getMonthValue();

        LocalDateTime firstDay = LocalDate.of(y, m, 1).atStartOfDay();
        LocalDateTime lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        return programRepository.findByDueDateBetween(firstDay, lastDay).stream()
                .map(GetProgramRes::fromProgram)
                .toList();
    }

    // 프로그램 전체 목록 조회
    public BaseResponse<List<ProgramListResponse>> getProgramList() {
        List<ProgramListResponse> programList = programRepository.findAll().stream()
                .map(ProgramListResponse::fromProgram)
                .toList();
        return new BaseResponse<>(programList);
    }

    // 프로그램 상세 조회
    public GetProgramDetailRes getProgram(Long programIdx) {
        return GetProgramDetailRes.fromProgram(
                programRepository.findById(programIdx).orElseThrow(()-> new BaseException(INVALID_PROGRAM_IDX)));
    }

    public void saveProgram(PostProgramReq postProgramReq) {
        User user = userService.getUserWithValidation();
        if(!user.getRole().equals(Role.Admin)) throw new BaseException(INVALID_ROLE);

        Program program = PostProgramReq.toProgram(postProgramReq);
        programRepository.save(program);
        this.saveTag(postProgramReq, program);
        //this.saveChallenge(PostProgramReq.toProgram(postProgramReq), user);
    }

    public void saveTag(PostProgramReq postProgramReq, Program program) {
        tagRepository.save(Tag.builder().name(TagEnum.getEnumByName(postProgramReq.getCategory())).program(program).build());
        tagRepository.save(Tag.builder().name(TagEnum.getEnumByName(postProgramReq.getLocation())).program(program).build());
    }

    public void saveChallenge(Program program, User user) {
        challengeRepository.save(Challenge.builder().program(program).name(program.getName()).attendanceRate(0)
                .admin(user).host(program.getHost()).totalNum(0).build());
    }
}
