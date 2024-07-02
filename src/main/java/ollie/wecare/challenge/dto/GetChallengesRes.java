package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.challenge.entity.Challenge;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengesRes {
    private Long challengeIdx;
    private String name;
    private Integer participantsCount;
    private String schedule;
    private String locatedPlace;
    private Integer myAttendanceRate;
    private Integer totalAttendanceRate;

    public static GetChallengesRes fromChallenge(Challenge challenge, Integer myAttendanceRate) {

        return GetChallengesRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .participantsCount(challenge.getParticipants().size())
                .schedule(challenge.getProgram().getSchedule())
                .myAttendanceRate(myAttendanceRate)
                .locatedPlace(challenge.getProgram().getLocatedPlace())
                .totalAttendanceRate(challenge.getAttendanceRate()).build();
    }
}
