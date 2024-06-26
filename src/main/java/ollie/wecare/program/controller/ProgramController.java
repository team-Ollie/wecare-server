package ollie.wecare.program.controller;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.program.dto.GetProgramDetailRes;
import ollie.wecare.program.dto.GetProgramRes;
import ollie.wecare.program.dto.PostProgramReq;
import ollie.wecare.program.service.ProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ollie.wecare.common.base.BaseResponseStatus.SUCCESS;
import static ollie.wecare.common.constants.RequestURI.program;

@RestController
@RequestMapping(program)
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    /*
     * 프로그램 조회 (월별)
     * */

    @GetMapping
    @ResponseBody
    public BaseResponse<List<GetProgramRes>> getPrograms(@RequestParam(value = "year", defaultValue = "0", required = false) Long year,
                                                         @RequestParam(value = "month", defaultValue = "0", required = false) Long month) {
        return new BaseResponse<>(programService.getPrograms(year, month));
    }

    @GetMapping("/{programIdx}")
    @ResponseBody
    public BaseResponse<GetProgramDetailRes> getProgram(@PathVariable(value = "programIdx") Long programIdx) {
        return new BaseResponse<>(programService.getProgram(programIdx));
    }

    /*
     * 프로그램 등록
     * */
    @PostMapping
    @ResponseBody
    public BaseResponse<String> saveProgram(@RequestBody PostProgramReq postProgramReq) throws BaseException {
        programService.saveProgram(postProgramReq);
        return new BaseResponse<>(SUCCESS);
    }
}
