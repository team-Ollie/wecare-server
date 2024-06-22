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
public class GetChallengeAdsRes {

    private Challenge mostParticipatedChallenge;
    private Challenge mostAttendancedChallenge;
    private Challenge mostRecentlyStartedChallenge;

}
