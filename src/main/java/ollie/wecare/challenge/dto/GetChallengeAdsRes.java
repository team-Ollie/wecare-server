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

    private GetChallengeAdRes mostParticipatedChallenge;
    private GetChallengeAdRes mostAttendancedChallenge;
    private GetChallengeAdRes mostRecentlyStartedChallenge;

}
