package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.challenge.entity.Challenge;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetChallengesRes {

    private Long challengeIdx;

    private String name;

    private Long participantsNum;

    private String location;

    private String schedule;

    private Long attendanceRate;

    private Long totalAttendanceRate;

    public static GetChallengesRes fromChallenge(Challenge challenge, Long userParticipationNum) {

        return GetChallengesRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .participantsNum((long) challenge.getParticipants().size())
                .location(challenge.getProgram().getLocation())
                .schedule(challenge.getProgram().getSchedule())
                .attendanceRate(Math.round((double)userParticipationNum/challenge.getTotalNum()) * 100)
                .totalAttendanceRate(challenge.getAttendanceRate()).build();

    }

}
