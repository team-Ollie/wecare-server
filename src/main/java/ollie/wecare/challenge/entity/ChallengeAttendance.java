package ollie.wecare.challenge.entity;

import jakarta.persistence.*;
import ollie.wecare.common.Base.BaseEntity;
import ollie.wecare.user.entity.User;

import java.time.LocalDateTime;

public class ChallengeAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_idx")
    private Long attendanceIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_idx")
    private Challenge challenge;

    //TODO : createdDate로 대체해서 사용하는 것 고려해보기
    private LocalDateTime attendanceDate;



}
