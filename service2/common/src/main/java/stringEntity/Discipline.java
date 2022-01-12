package stringEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@XmlRootElement
public class Discipline implements Serializable {

    @XmlElement
    private String id;

    @XmlElement
    private String name;

    public entity.Discipline toRealDiscipline(){
        return new entity.Discipline(
                Long.parseLong(id),
                name,
                null
        );
    }
}
