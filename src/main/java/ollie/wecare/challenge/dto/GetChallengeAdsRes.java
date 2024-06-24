package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetChallengeAdsRes {

    private GetChallengesRes mostParticipatedChallenge;
    private GetChallengesRes mostAttendancedChallenge;
    private GetChallengesRes mostRecentlyStartedChallenge;

}
