package ollie.wecare.program.entity;

import jakarta.persistence.*;
import lombok.*;
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

    private LocalDateTime openDate;

    private String locatedPlace;

    private String host;

    private String schedule;

    private String description;

    @OneToMany(mappedBy = "program")
    private List<Tag> tags;

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        for (Tag tag : this.tags) {
            tag.setProgram(this);
        }
    }
}
