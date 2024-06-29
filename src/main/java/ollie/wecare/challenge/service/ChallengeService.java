package ollie.wecare.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ollie.wecare.challenge.dto.*;
import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.challenge.entity.ChallengeAttendance;
import ollie.wecare.challenge.repository.ChallengeAttendanceRepository;
import ollie.wecare.challenge.repository.ChallengeRepository;
import ollie.wecare.common.base.BaseException;
import ollie.wecare.user.repository.UserRepository;
import ollie.wecare.user.service.AuthService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static ollie.wecare.common.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChallengeService {

    private final ChallengeAttendanceRepository challengeAttendanceRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    private final AuthService authService;

    /*
     * 참여 중인 챌린지 조회
     * */
    public List<GetChallengesRes> getMyChallenges() throws BaseException {
        //TODO : 특정 get 요청만 인증받지 않도록 변경
        Long userIdx = authService.getUserIdx();
        if(userIdx == null) throw new BaseException(INVALID_ACCESS_TOKEN);
        List<ChallengeAttendance> participationList = challengeAttendanceRepository.findByUser_UserIdx(userIdx);
        Long participationNum = (long)participationList.size();

        Set<Challenge> challengeSet = new HashSet<>();
        for(ChallengeAttendance ca : participationList)
            challengeSet.add(ca.getChallenge());

        List<Challenge> challengesList = new ArrayList<>(challengeSet);
        return challengesList.stream().map(challenge -> GetChallengesRes.fromChallenge(challenge, participationNum)).toList();
    }

    /*
     * 챌린지 인증
     * */
    public void attendChallenge(AttendChallengeReq attendChallengeReq) throws BaseException {
        Challenge challenge = challengeRepository.findById(attendChallengeReq.getChallengeIdx()).orElseThrow(()-> new BaseException(INVALID_CHALLENGE_IDX));
        if(!challenge.getAttendanceCode().equals(attendChallengeReq.getAttendanceCode()))
            throw new BaseException(INVALID_ATTENDANCE_CODE);
        else {
            ChallengeAttendance challengeAttendance = ChallengeAttendance.builder()
                    .user(userRepository.findById(authService.getUserIdx()).orElseThrow(()->new BaseException(INVALID_ACCESS_TOKEN)))
                    .challenge(challengeRepository.findById(attendChallengeReq.getChallengeIdx()).orElseThrow(()-> new BaseException(INVALID_CHALLENGE_IDX)))
                    .attendanceDate(LocalDateTime.now()).build();
            challengeAttendanceRepository.save(challengeAttendance);
        }
    }

    /*
     * 새로운 챌린지 참여
     * */
    @Transactional
    public void participateChallenge(PostChallengeReq postChallengeReq) throws BaseException {
        ChallengeAttendance challengeAttendance = ChallengeAttendance.builder()
                .user(userRepository.findById(authService.getUserIdx()).orElseThrow(()->new BaseException(INVALID_ACCESS_TOKEN)))
                .challenge(challengeRepository.findById(postChallengeReq.getChallengeIdx()).orElseThrow(()-> new BaseException(INVALID_CHALLENGE_IDX)))
                .attendanceDate(LocalDateTime.now()).build();
        challengeAttendanceRepository.save(challengeAttendance);
    }

    /*
     * 챌린지 검색
     * */
    public List<GetChallengesRes> getChallenges(String searchWord) {
        Long userIdx = authService.getUserIdx();
        if(userIdx == null) throw new BaseException(INVALID_ACCESS_TOKEN);
        List<Challenge> challenges = challengeRepository.findByNameContaining(searchWord);
        List<Challenge> challengeResult = new ArrayList<>();
        for(Challenge challenge : challenges) {
            List<ChallengeAttendance> challengeAttendances = challengeAttendanceRepository.findByUser_UserIdxAndChallenge_ChallengeIdx(userIdx, challenge.getChallengeIdx());
            if(challengeAttendances == null || challengeAttendances.isEmpty())
                challengeResult.add(challenge);
        }

        return challengeResult.stream().map(challenge -> GetChallengesRes.fromChallenge(challenge, 0L)).toList();
    }

    /*
     * 챌린지 참여 현황 조회(월별)
     * */
    public List<GetAttendanceRes> getAttendance(Long challengeIdx, Long year, Long month) {
        Long userIdx = authService.getUserIdx();
        if(userIdx == null) throw new BaseException(INVALID_ACCESS_TOKEN);
        int y = year != null && year > 0 ? year.intValue() : LocalDateTime.now().getYear();
        int m = month != null && month > 0 ? month.intValue() : LocalDateTime.now().getMonthValue();

        LocalDateTime firstDay = LocalDate.of(y, m, 1).atStartOfDay();
        LocalDateTime lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        /*int y = year.intValue();
        int m = month.intValue();
        LocalDateTime firstDay = LocalDate.of(y, m, 1).atStartOfDay();
        LocalDateTime lastDay = LocalDate.of(y, m, 1).atStartOfDay();
        if(year == 0) {
            firstDay = YearMonth.from(LocalDateTime.now().toLocalDate()).atDay(1).atStartOfDay();
            lastDay = YearMonth.from(LocalDateTime.now().toLocalDate()).atEndOfMonth().atStartOfDay();
        }*/
        return challengeAttendanceRepository.findByUser_UserIdxAndChallenge_ChallengeIdxAndAttendanceDateBetweenOrderByAttendanceDate(userIdx, challengeIdx, firstDay, lastDay)
                .stream()
                .skip(1L)
                .map(challengeAttendance -> GetAttendanceRes.fromAttendance(challengeAttendance))
                .collect(Collectors.toList());
    }

    /*
    * 챌린지 배너 조회 (홈화면)
    * */
    public GetChallengeAdsRes getChallengeAds() {

        Challenge mostAttendancedChallenge = challengeRepository.findTop1ByOrderByAttendanceRateDesc().orElseThrow(()-> new BaseException(NO_CHALLENGE));
        Challenge mostParticipatedChallenge = challengeRepository.findMostParticipatedChallenge(PageRequest.of(0, 1)).getContent().get(0);
        Challenge mostRecentlyStartedChallenge = challengeRepository.findTop1ByOrderByCreatedDateDesc().orElseThrow(()-> new BaseException(NO_CHALLENGE));


        return GetChallengeAdsRes.builder()
                .mostAttendancedChallenge(GetChallengesRes.fromChallenge(mostAttendancedChallenge, 0L))
                .mostParticipatedChallenge(GetChallengesRes.fromChallenge(mostParticipatedChallenge, 0L))
                .mostRecentlyStartedChallenge(GetChallengesRes.fromChallenge(mostRecentlyStartedChallenge, 0L)).build();

    }


}
