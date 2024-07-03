package ollie.wecare.program.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.program.dto.GetProgramDetailRes;
import ollie.wecare.program.dto.GetProgramRes;
import ollie.wecare.program.dto.PostProgramReq;
import ollie.wecare.program.dto.ProgramListResponse;
import ollie.wecare.program.service.ProgramService;
import ollie.wecare.user.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ollie.wecare.common.constants.RequestURI.program;

@RestController
@RequestMapping(program)
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final ProgramService programService;
    private final AuthService authService;

    // 프로그램 조회 (월별)
    @GetMapping
    public BaseResponse<List<GetProgramRes>> getPrograms(@RequestParam(value = "year", defaultValue = "0", required = false) Long year, @RequestParam(value = "month", defaultValue = "0", required = false) Long month) {
        return new BaseResponse<>(programService.getPrograms(year, month));
    }

    // 프로그램 전체 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<ProgramListResponse>> getProgramList() {
        return programService.getProgramList();
    }

    // 프로그램 상세 조회
    @GetMapping("/{programIdx}")
    public BaseResponse<GetProgramDetailRes> getProgram(@PathVariable(value = "programIdx") Long programIdx) {
        return new BaseResponse<>(programService.getProgram(programIdx));
    }

    // 프로그램 등록
    @PostMapping
    public BaseResponse<String> saveProgram(@RequestBody PostProgramReq postProgramReq) throws BaseException {
        return programService.saveProgram(authService.getUserIdx(), postProgramReq);
    }
}
