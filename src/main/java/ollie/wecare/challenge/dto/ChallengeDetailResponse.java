package ollie.wecare.challenge.dto;

import ollie.wecare.program.dto.DateDto;

import java.util.List;

public record ChallengeDetailResponse(String name,
                                      Integer participantsCount,
                                      List<DateDto> attendanceList,
                                      Integer totalAchievementRate,
                                      Integer myAchievementRate) {
}
