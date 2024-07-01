package ollie.wecare.program.dto;

import lombok.Getter;
import ollie.wecare.program.entity.Program;

import java.time.LocalDateTime;

@Getter
public class PostProgramReq {
    private String name;

    private DateDto dueDate;

    private DateDto openDate;

    private String location;

    private String host;

    private String schedule;

    private String description;

    private String category;

    public static Program toProgram(PostProgramReq postProgramReq) {
        return Program.builder().name(postProgramReq.getName())
                .dueDate(convertToLocalDateTime(postProgramReq.getDueDate()))
                .openDate(convertToLocalDateTime(postProgramReq.getOpenDate()))
                .host(postProgramReq.getHost())
                .schedule(postProgramReq.getSchedule())
                .description(postProgramReq.getDescription())
                .build();
    }

    private static LocalDateTime convertToLocalDateTime(DateDto dateDto) {
        if(dateDto == null) return null;
        return LocalDateTime.of(dateDto.year(), dateDto.month(), dateDto.day(), 0, 0, 0);
    }


}

