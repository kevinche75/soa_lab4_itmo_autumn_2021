package ru.itmo.soap;

import ru.itmo.service.RemoteBeanLookup;
import service.SecondServiceI;
import service.ServiceError;
import utils.ServerResponse;

import javax.ejb.EJBException;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(endpointInterface = "ru.itmo.soap.SoapLabWorkServiceI", serviceName = "labwork_service")
public class SoapLabWorkService implements SoapLabWorkServiceI{

    private SecondServiceI service;

    public SoapLabWorkService(){
        service = RemoteBeanLookup.lookupRemoteStatelessBean();
    }

    @WebResult(partName="serverResponse")
    @Override
    public ServerResponse increaseLabWorkDifficulty(String labWorkId, String steps) throws ServiceFault {
        try {
            return service.increaseLabWorkDifficulty(labWorkId, steps);
        }  catch (EJBException e){
            throw new ServiceFault(e.getMessage());
        } catch (Exception e){
            throw new ServiceFault("Unexpected error, try again!.500");
        }
    }
}
