package ollie.wecare.program.dto;

import ollie.wecare.common.enums.TagEnum;
import ollie.wecare.program.entity.Program;
import ollie.wecare.program.entity.Tag;

import java.time.LocalDateTime;

public record ProgramListResponse(Long programIdx,
                                  String name,
                                  DateDto openDate,
                                  DateDto dueDate,
                                  String location,
                                  String category) {
    private static DateDto convertToDateDto(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return new DateDto(
                dueDate.getYear(),
                dueDate.getMonthValue(),
                dueDate.getDayOfMonth());
    }

    public static ProgramListResponse fromProgram(Program program) {
        return new ProgramListResponse(
                program.getProgramIdx(),
                program.getName(),
                convertToDateDto(program.getOpenDate()),
                convertToDateDto(program.getDueDate()),
                getLocationTag(program) != null ? getLocationTag(program).getTagName() : null,
                getCategoryTag(program) != null ? getCategoryTag(program).getTagName() : null);
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
