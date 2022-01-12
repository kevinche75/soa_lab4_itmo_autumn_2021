package service;

import stringEntity.Discipline;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.ejb.Remote;

@Remote
public interface SecondServiceI {

    public DisciplineResult getDisciplines();
    public Discipline getDiscipline(String stringId);
    public ServerResponse createDiscipline(String stringDiscipline);
    public LabWorksResult getDisciplineLabWorks(String stringDisciplineId);
    public ServerResponse addLabWorkToDiscipline(String stringDisciplineId, String stringLabWorkId);
    public ServerResponse removeLabWorkFromDiscipline(String stringDisciplineId, String stringLabWorkId);
    public ServerResponse increaseLabWorkDifficulty(String id, String stringSteps);
    public ServerResponse test();
}
