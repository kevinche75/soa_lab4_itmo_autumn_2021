package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Discipline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Column(unique = true)
    private String name;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="discipline_labWorks",
            joinColumns = @JoinColumn( name="discipline_id")
    )
    @Column(name = "labWork_id")
    private List<Long> labWorks = new ArrayList<>();

    public stringEntity.Discipline toUnrealDiscipline(){
        return new stringEntity.Discipline(
          Long.toString(id),
          name
        );
    }
}
