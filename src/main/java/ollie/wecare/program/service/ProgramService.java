package ollie.wecare.program.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ollie.wecare.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static ollie.wecare.common.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ChallengeRepository challengeRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

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

    // 프로그램 등록
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> saveProgram(Long userIdx, PostProgramReq postProgramReq) {
        User user = userRepository.findByUserIdx(userIdx).orElseThrow(() -> new BaseException(INVALID_USER_IDX));
        if (!user.getRole().equals(Role.Admin)) throw new BaseException(INVALID_ROLE);

        Program newProgram = PostProgramReq.toProgram(postProgramReq);
        programRepository.save(newProgram);

        saveChallenge(newProgram, user);
        saveTag(postProgramReq, newProgram);
        return new BaseResponse<>(SUCCESS);
    }

    private void saveTag(PostProgramReq postProgramReq, Program program) {
        tagRepository.save(Tag.builder().name(TagEnum.getEnumByName(postProgramReq.getCategory())).program(program).build());
        tagRepository.save(Tag.builder().name(TagEnum.getEnumByName(postProgramReq.getLocation())).program(program).build());
    }

    private void saveChallenge(Program program, User user) {
        Duration duration = Duration.between(program.getOpenDate(), program.getDueDate());
        Integer totalNum = Math.toIntExact(duration.toDays() / 7); //TODO : 특정 요일 횟수 반영
        Challenge newChallenge = Challenge.builder()
                .program(program)
                .name(program.getName())
                .attendanceRate(0)
                .admin(user)
                .host(program.getHost())
                .totalNum(totalNum).build();
        challengeRepository.save(newChallenge);
    }

}
