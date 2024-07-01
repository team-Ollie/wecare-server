package ollie.wecare.program.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.common.enums.TagEnum;
import ollie.wecare.program.entity.Program;
import ollie.wecare.program.entity.Tag;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetProgramDetailRes {


    private Long programIdx;

    private String name;

    private DateDto openDate;

    private DateDto dueDate;

    private String location;

    private String category;

    private String host;

    private String schedule;

    private String description;


    public static GetProgramDetailRes fromProgram(Program program) {
        return GetProgramDetailRes.builder()
                .programIdx(program.getProgramIdx())
                .name(program.getName())
                .openDate(convertToDateDto(program.getOpenDate()))
                .dueDate(convertToDateDto(program.getDueDate()))
                .location(getLocationTag(program) != null ? getLocationTag(program).getTagName() : null)
                .category(getCategoryTag(program) != null ? getCategoryTag(program).getTagName() : null)
                .host(program.getHost())
                .schedule(program.getSchedule())
                .description(program.getDescription()).build();
    }

    private static DateDto convertToDateDto(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return new DateDto(
                dueDate.getYear(),
                dueDate.getMonthValue(),
                dueDate.getDayOfMonth());
    }
    private static TagEnum getCategoryTag(Program program) {
        return program.getTags().stream()
                .map(Tag::getName)
                .filter(tagEnum -> tagEnum.getParent() == TagEnum.CATEGORY)
                .findFirst()
                .orElse(null);
    }

    private static TagEnum getLocationTag(Program program) {
        return program.getTags().stream()
                .map(Tag::getName)
                .filter(tagEnum -> tagEnum.getParent() == TagEnum.LOCATION)
                .findFirst()
                .orElse(null);
    }
}
