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

    private Integer participantsCount;

    private String location;

    private String schedule;

    private Integer myAttendanceRate;

    private Integer totalAttendanceRate;

    public static GetChallengesRes fromChallenge(Challenge challenge, Integer myAttendanceRate) {

        return GetChallengesRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .participantsCount(challenge.getParticipants().size())
                .location(challenge.getProgram().getLocation())
                .schedule(challenge.getProgram().getSchedule())
                .myAttendanceRate(myAttendanceRate)
                .totalAttendanceRate(challenge.getAttendanceRate()).build();
    }

}
