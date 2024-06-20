package ollie.wecare.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ollie.wecare.common.base.BaseEntity;
import ollie.wecare.common.enums.Role;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    private Role role;

    private String loginId;

    private String password;

    private String nickname;

    private String identifier;//홍길동 + 1234

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_idx")
    private Center center;


}
