package ollie.wecare.program.controller;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import ollie.wecare.program.dto.PostProgramReq;
import ollie.wecare.program.service.ProgramService;
import org.springframework.web.bind.annotation.*;

import static ollie.wecare.common.base.BaseResponseStatus.SUCCESS;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;
    /*
     * 프로그램 조회 (월별)
     * */
    /*
    @GetMapping("/{month}")
    @ResponseBody
    public BaseResponse<List<GetProgramRes>> getPrograms(HttpServletRequest request, @PathVariable Long month) {
        return new BaseResponse<>(programService.getPrograms(month));
    }*/

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
