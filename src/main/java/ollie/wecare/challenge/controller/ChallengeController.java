package ollie.wecare.challenge.controller;


import lombok.RequiredArgsConstructor;
import ollie.wecare.challenge.dto.*;
import ollie.wecare.challenge.service.ChallengeService;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.common.base.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ollie.wecare.common.base.BaseResponseStatus.SUCCESS;
import static ollie.wecare.common.constants.RequestURI.challenge;

@RestController
@RequestMapping(challenge)
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    /*
    * 참여 중인 챌린지 조회
    * */
    @GetMapping
    @ResponseBody
    public BaseResponse<List<GetChallengesRes>> getMyChallenges() throws BaseException {
        return new BaseResponse<>(challengeService.getMyChallenges());
    }

    /*
    * 챌린지 인증
    * */
    @PostMapping("/attendance")
    @ResponseBody
    public BaseResponse<String> attendChallenge(@RequestBody AttendChallengeReq attendChallengeReq) throws BaseException {
        challengeService.attendChallenge(attendChallengeReq);
        return new BaseResponse<>(SUCCESS);
    }

    /*
     * 챌린지 참여 현황 조회(월별)
     * */
    @GetMapping("/attendance/{challengeIdx}")
    @ResponseBody
    public BaseResponse<List<GetAttendanceRes>> getAttendance(@PathVariable("challengeIdx") Long challengeIdx,
                                                        @RequestParam(value = "year", required = false, defaultValue = "0") Long year,
                                                        @RequestParam(value = "month", required = false, defaultValue = "0") Long month) {
        return new BaseResponse<>(challengeService.getAttendance(challengeIdx, year, month));
    }

    /*
    * 새로운 챌린지 참여
    * */
    @PostMapping("/participation")
    @ResponseBody
    public BaseResponse<String> participateChallenge(@RequestBody PostChallengeReq postChallengeReq) throws BaseException {
        challengeService.participateChallenge(postChallengeReq);
        return new BaseResponse<>(SUCCESS);

    }

    /*
    * 챌린지 검색
    * */
    @GetMapping("/search")
    @ResponseBody
    public BaseResponse<List<GetChallengesRes>> getChallenges(@RequestParam(value = "searhWord", defaultValue = "", required = false) String searchWord)throws BaseException {
        return new BaseResponse<>(challengeService.getChallenges(searchWord));
    }

    /*
    * 챌린지 광고 조회
    * */
    @GetMapping("/ads")
    @ResponseBody
    public BaseResponse<GetChallengeAdsRes> getChallengeAds() {
        return new BaseResponse<>(challengeService.getChallengeAds());
    }
}
