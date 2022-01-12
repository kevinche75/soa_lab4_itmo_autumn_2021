package utils;

import stringEntity.Discipline;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "disciplines_result")
public class DisciplineResult implements Serializable {

    @XmlElement
    private final long totalDisciplines;

    @XmlElementWrapper(name = "disciplines")
    @XmlElement(name = "discipline")
    private final List<Discipline> list;
    public DisciplineResult(long totalDisciplines, List<entity.Discipline> list){
        this.totalDisciplines = totalDisciplines;
        this.list = list
                .stream()
                .map(entity.Discipline::toUnrealDiscipline)
                .collect(Collectors.toList());
    }

    public DisciplineResult(){
        totalDisciplines = 0;
        list = new ArrayList<>();
    }
}
