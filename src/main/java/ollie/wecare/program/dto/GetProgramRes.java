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

    private LocalDateTime dueDate;

    private String location;

    private String host;

    private String schedule;

    private String description;

    public static GetProgramRes fromProgram(Program program) {
        return GetProgramRes.builder()
                .programIdx(program.getProgramIdx())
                .name(program.getName())
                .dueDate(program.getDueDate())
                .location(program.getLocation())
                .host(program.getHost())
                .schedule(program.getSchedule())
                .description(program.getDescription()).build();

    }

}
