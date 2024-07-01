package ollie.wecare.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.common.enums.TagEnum;
import ollie.wecare.program.entity.Program;
import ollie.wecare.program.entity.Tag;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetChallengesAdminRes {

    private Long challengeIdx;

    private String name;

    private Integer participantsCount;

    private String location;
    private String locatedPlace;

    private String schedule;

    private Integer totalAttendanceRate;

    public static GetChallengesAdminRes fromChallenge(Challenge challenge) {

        return GetChallengesAdminRes.builder()
                .challengeIdx(challenge.getChallengeIdx())
                .name(challenge.getName())
                .locatedPlace(challenge.getProgram().getLocatedPlace())
                .participantsCount(challenge.getParticipants().size())
                .location(getLocationTag(challenge.getProgram()) != null ? getLocationTag(challenge.getProgram()).getTagName() : null)
                .schedule(challenge.getProgram().getSchedule())
                .totalAttendanceRate(challenge.getAttendanceRate()).build();
    }

    private static TagEnum getLocationTag(Program program) {
        return program.getTags().stream()
                .map(Tag::getName)
                .filter(tagEnum -> tagEnum.getParent() == TagEnum.LOCATION)
                .findFirst()
                .orElse(null);
    }

}

