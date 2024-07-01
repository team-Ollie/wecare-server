package ollie.wecare.challenge.dto;

import lombok.*;
import ollie.wecare.challenge.entity.Challenge;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetChallengeAdRes {

    private Long challengeIdx;
    private String name;

    public static GetChallengeAdRes fromChallenge(Challenge challenge) {
        return GetChallengeAdRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .build();
    }

}
