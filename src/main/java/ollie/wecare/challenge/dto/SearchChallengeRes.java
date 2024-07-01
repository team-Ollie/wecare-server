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
public class SearchChallengeRes {
    private Long challengeIdx;

    private String name;

    private String schedule;

    private String locatedPlace;

    public static SearchChallengeRes fromChallenge(Challenge challenge) {

        return SearchChallengeRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .schedule(challenge.getProgram().getSchedule())
                .locatedPlace(challenge.getProgram().getLocatedPlace())
                .build();
    }
}
