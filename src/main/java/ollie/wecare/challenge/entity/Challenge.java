package ollie.wecare.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ollie.wecare.common.base.BaseEntity;
import ollie.wecare.program.entity.Program;
import ollie.wecare.user.entity.User;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Challenge extends BaseEntity {
    public void updateAttendanceCode(String attendanceCode) {
        this.attendanceCode = attendanceCode;
    }
    public void updateAttendanceRate(Integer attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_idx")
    private Long challengeIdx;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program_idx")
    private Program program;

    private String name;

    private String host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User admin;

    private String attendanceCode;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> participants;

    private Integer totalNum;

    private Integer attendanceRate;

    public void setParticipants(User user) {
        if(user != null) this.participants.add(user);
    }
    public void setProgram(Program program) {
        if(program != null) this.program = program;
    }
}
