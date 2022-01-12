package ru.itmo.soap;

import stringEntity.Discipline;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SoapDisciplineServiceI {

    @WebMethod
    DisciplineResult getDisciplines() throws ServiceFault;

    @WebMethod
    Discipline getDiscipline(String disciplineId) throws ServiceFault;

    @WebMethod
    LabWorksResult getDisciplineLabWorks(String disciplineId) throws ServiceFault;

    @WebMethod
    ServerResponse createDiscipline(String strDiscipline) throws ServiceFault;

    @WebMethod
    ServerResponse deleteLabWorkFromDiscipline(String disciplineId, String labWorkId) throws ServiceFault;

    @WebMethod
    ServerResponse addLabWorkToDiscipline(String disciplineId, String labWorkId) throws ServiceFault;
}
