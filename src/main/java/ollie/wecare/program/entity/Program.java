package ollie.wecare.program.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ollie.wecare.common.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Program extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_idx")
    private Long programIdx;

    private String name;

    private LocalDateTime dueDate;

    private String location;

    private String host;

    private String schedule;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;

}
