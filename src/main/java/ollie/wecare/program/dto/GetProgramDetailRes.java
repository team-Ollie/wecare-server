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
public class GetProgramDetailRes {


    private Long programIdx;

    private String name;

    private DateDto openDate;

    private DateDto dueDate;

    private String location;

    private String host;

    private String schedule;

    private String description;


    public static GetProgramDetailRes fromProgram(Program program) {
        return GetProgramDetailRes.builder()
                .programIdx(program.getProgramIdx())
                .name(program.getName())
                .openDate(convertToDateDto(program.getOpenDate()))
                .dueDate(convertToDateDto(program.getDueDate()))
                .location(program.getLocation())
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
}
