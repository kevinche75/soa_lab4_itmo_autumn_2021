package ru.itmo.soap;

import ru.itmo.service.RemoteBeanLookup;
import service.SecondServiceI;
import service.ServiceError;
import stringEntity.Discipline;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.ejb.EJBException;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(endpointInterface = "ru.itmo.soap.SoapDisciplineServiceI", serviceName = "discipline_service")
public class SoapDisciplineService implements SoapDisciplineServiceI{

    private SecondServiceI service;

    public SoapDisciplineService() {
        service = RemoteBeanLookup.lookupRemoteStatelessBean();
    }

    @WebResult(partName="disciplines_result")
    @Override
    public DisciplineResult getDisciplines() throws ServiceFault {
        try {
            return service.getDisciplines();
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }

    @WebResult(partName="discipline")
    @Override
    public Discipline getDiscipline(String disciplineId) throws ServiceFault {
        try {
            return service.getDiscipline(disciplineId);
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }

    @WebResult(partName="labworks_result")
    @Override
    public LabWorksResult getDisciplineLabWorks(String disciplineId) throws ServiceFault {
        try {
            return service.getDisciplineLabWorks(disciplineId);
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }

    @WebResult(partName="serverResponse")
    @Override
    public ServerResponse createDiscipline(String name) throws ServiceFault {
        System.out.println("strDiscipline: " + name);
        try {
            return service.createDiscipline(name);
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }

    @WebResult(partName="serverResponse")
    @Override
    public ServerResponse deleteLabWorkFromDiscipline(String disciplineId, String labWorkId) throws ServiceFault {
        try {
            return service.removeLabWorkFromDiscipline(disciplineId, labWorkId);
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }

    @WebResult(partName="serverResponse")
    @Override
    public ServerResponse addLabWorkToDiscipline(String disciplineId, String labWorkId) throws ServiceFault {
        try {
            return service.addLabWorkToDiscipline(disciplineId, labWorkId);
        } catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }
}
