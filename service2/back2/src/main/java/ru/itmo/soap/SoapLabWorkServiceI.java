package ru.itmo.soap;

import utils.ServerResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SoapLabWorkServiceI {

    @WebMethod
    ServerResponse increaseLabWorkDifficulty(String labWorkId, String steps) throws ServiceFault;
}
