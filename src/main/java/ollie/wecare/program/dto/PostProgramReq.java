package ollie.wecare.program.dto;

import lombok.Getter;
import ollie.wecare.program.entity.Program;

import java.time.LocalDateTime;

@Getter
public class PostProgramReq {
    private String name;

    private LocalDateTime dueDate;

    private String location;

    private String host;

    private String schedule;

    private String description;

    //private List<Tag> tags;

    public static Program toProgram(PostProgramReq postProgramReq) {
        return Program.builder().name(postProgramReq.getName())
                .dueDate(postProgramReq.getDueDate())
                .location((postProgramReq.getLocation()))
                .host(postProgramReq.getHost())
                .schedule(postProgramReq.getSchedule())
                .description(postProgramReq.getDescription())
                /*.tags(postProgramReq.getTags())*/
                .build();
    }


}

