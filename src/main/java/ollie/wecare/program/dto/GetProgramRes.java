package ollie.wecare.program.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ollie.wecare.program.entity.Program;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetProgramRes {

    private Long programIdx;

    private String name;

    private DateDto openDate;

    private DateDto dueDate;

    public static GetProgramRes fromProgram(Program program) {
        return GetProgramRes.builder()
                .programIdx(program.getProgramIdx())
                .name(program.getName())
                .openDate(convertToDateDto(program.getOpenDate()))
                .dueDate(convertToDateDto(program.getDueDate())).build();
    }

    private static DateDto convertToDateDto(LocalDateTime dueDate) {
        if (dueDate == null) return null;
        return new DateDto(
                dueDate.getYear(),
                dueDate.getMonthValue(),
                dueDate.getDayOfMonth());
    }
}
