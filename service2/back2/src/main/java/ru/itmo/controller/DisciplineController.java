package ru.itmo.controller;

import ru.itmo.service.RemoteBeanLookup;
import service.SecondServiceI;
import stringEntity.Discipline;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/disciplines")
public class DisciplineController {

    private SecondServiceI service;

    public DisciplineController(){
        service = RemoteBeanLookup.lookupRemoteStatelessBean();
    }

    @GET
    public DisciplineResult getDisciplines(){
        return service.getDisciplines();
    }

    @GET
    @Path("/{id}")
    public Discipline getDiscipline(@PathParam("id")String disciplineId){
        return service.getDiscipline(disciplineId);
    }

    @GET
    @Path("/{id}/labworks")
    public LabWorksResult getDisciplineLabWorks(@PathParam("id") String disciplineId){
        return service.getDisciplineLabWorks(disciplineId);
    }

    @POST
    public ServerResponse createDiscipline(String strDiscipline){
        return service.createDiscipline(strDiscipline);
    }

    @DELETE
    @Path("/{discipline-id}/labwork/{labwork-id}/remove")
    public ServerResponse deleteLabWorkFromDiscipline(
            @PathParam("discipline-id") String disciplineId,
            @PathParam("labwork-id") String labWorkId){
        return service.removeLabWorkFromDiscipline(disciplineId, labWorkId);
    }

    @POST
    @Path("/{id}/{labwork-id}")
    public ServerResponse addLabWorkToDiscipline(@PathParam("id") String disciplineId, @PathParam("labwork-id") String labWorkId){
        return service.addLabWorkToDiscipline(disciplineId, labWorkId);
    }

    @OPTIONS
    @Path("{path : .*}")
    public Response options() {
        return Response.ok()
                .build();
    }

//    @SneakyThrows
//    @GET
//    @Path(("/test"))
//    public Response test(){
//        return Response.ok(ServiceDiscovery.getUriFromConsul()).build();
//    }
}
