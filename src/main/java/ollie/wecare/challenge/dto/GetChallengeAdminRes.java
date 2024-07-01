package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.user.entity.User;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetChallengeAdminRes {
    String identifier;
    Integer attendanceRate;

    public static GetChallengeAdminRes fromParticipant(User user, Integer attendanceRate) {
        return GetChallengeAdminRes.builder().identifier(user.getIdentifier()).attendanceRate(attendanceRate).build();
    }
}
