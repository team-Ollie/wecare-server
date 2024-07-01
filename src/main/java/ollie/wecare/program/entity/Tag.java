package ollie.wecare.program.entity;

import jakarta.persistence.*;
import lombok.*;
import ollie.wecare.common.base.BaseEntity;
import ollie.wecare.common.enums.TagEnum;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_idx")
    private Long tagIdx;

    @Enumerated(EnumType.STRING)
    private TagEnum name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_idx")
    private Program program;
}
