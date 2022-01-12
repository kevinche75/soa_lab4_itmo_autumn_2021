package ru.itmo.service;

import ru.itmo.DAO.DisciplineDAO;
import ru.itmo.converter.FieldConverter;
import ru.itmo.converter.XMLConverter;
import entity.Difficulty;
import entity.Discipline;
import service.SecondServiceI;
import service.ServiceError;
import stringEntity.LabWork;
import ru.itmo.utils.*;
import ru.itmo.validator.Validator;
import ru.itmo.validator.ValidatorResult;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class SecondService implements SecondServiceI, Serializable {

    private XMLConverter xmlConverter;
    private DisciplineDAO dao;
    private Map<Integer, String> difficultyMap;

    public SecondService() {
        xmlConverter = new XMLConverter();
        dao = new DisciplineDAO();
        difficultyMap = Arrays
                .stream(Difficulty.values())
                .collect(Collectors.toMap(Difficulty::ordinal, Difficulty::toString));
    }

    public ResponseWrapper getInfo(int code, String message) {
        ServerResponse serverResponse = new ServerResponse(message);
        return new ResponseWrapper(code, xmlConverter.toStr(serverResponse));
    }


    public DisciplineResult getDisciplines(){
        try {
            DisciplineResult disciplineResult = dao.getAllDisciplines();
            return disciplineResult;
        } catch (Exception e) {
            throw new ServiceError(500, "Server error, try again");
        }
    }

    public stringEntity.Discipline getDiscipline(String stringId){
        try {
            ValidatorResult validatorResult = new ValidatorResult();
            Long id = FieldConverter.longConvert(stringId, "Discipline Id", validatorResult);
            if (!validatorResult.isStatus()) {
                throw new ServiceError(400, validatorResult.getMessage());
            }
            Optional<Discipline> discipline = dao.getDiscipline(id);
            if (discipline.isPresent()) {
                return discipline.get().toUnrealDiscipline();
            } else {
                throw new ServiceError(400, String.format("No discipline with id: %s", stringId));
            }
        } catch (ServiceError e){
            throw e;
        } catch (Exception e){
            throw new ServiceError(500, "Server error, try again");
        }
    }

    public ServerResponse createDiscipline(String name){
        try {
//            stringEntity.Discipline stringDiscipline = xmlConverter.fromStr(name, stringEntity.Discipline.class);
            ValidatorResult validatorResult = Validator.validateDiscipline(name);
            if (!validatorResult.isStatus()) {
                throw new ServiceError(400, validatorResult.getMessage());
            }
            Discipline discipline = new Discipline();
            discipline.setName(name);
            Long id = dao.createDiscipline(discipline);
            return new ServerResponse("Success");
        }catch (PersistenceException e){
            throw new ServiceError(400, "Discipline has already existed");
//        } catch (JAXBException e) {
//            throw new ServiceError(400, "Unknown data structure");
        } catch (ServiceError e){
            throw e;
        } catch (Exception e) {
            throw new ServiceError(500, "Server error, try again");
        }
    }

    public LabWorksResult getDisciplineLabWorks(String stringDisciplineId){
        ValidatorResult validatorResult = new ValidatorResult();
        Long id = FieldConverter.longConvert(stringDisciplineId, "Discipline Id", validatorResult);
        if (!validatorResult.isStatus()) {
            throw new ServiceError(400, validatorResult.getMessage());
        }
        LabWorksResult result = null;
        try {
            result = dao.getDisciplineLabWorks(id);
        } catch (EntityNotFoundException e){
            throw new ServiceError(400, e.getMessage());
        }
        return result;
    }

    public ServerResponse addLabWorkToDiscipline(String stringDisciplineId, String stringLabWorkId){
        ValidatorResult validatorResult = new ValidatorResult();
        Long id = FieldConverter.longConvert(stringDisciplineId, "Discipline Id", validatorResult);
        Long labWorkId = FieldConverter.longConvert(stringLabWorkId, "LabWork Id", validatorResult);
        if (!validatorResult.isStatus()) {
            throw new ServiceError(400, validatorResult.getMessage());
        }
        try {
            dao.addLabWork(id, labWorkId);
        } catch (EntityNotFoundException | EntityExistsException e){
            throw new ServiceError(400, e.getMessage());
        }
        return new ServerResponse("Success");
    }

    public ServerResponse removeLabWorkFromDiscipline(String stringDisciplineId, String stringLabWorkId){
        ValidatorResult validatorResult = new ValidatorResult();
        Long id = FieldConverter.longConvert(stringDisciplineId, "Discipline Id", validatorResult);
        Long labWorkId = FieldConverter.longConvert(stringLabWorkId, "LabWork Id", validatorResult);
        if (!validatorResult.isStatus()) {
            throw new ServiceError(400, validatorResult.getMessage());
        }
        try {
            dao.deleteLabWorkFromDiscipline(id, labWorkId);
        } catch (EntityNotFoundException e){
            throw new ServiceError(400, e.getMessage());
        }
        return new ServerResponse("Success");
    }

    public ServerResponse increaseLabWorkDifficulty(String id, String stringSteps) {
        try {
            ValidatorResult validatorResult = new ValidatorResult();
            FieldConverter.longConvert(id, "Discipline Id", validatorResult);
            Integer steps = FieldConverter.intConvert(stringSteps, "Steps", validatorResult);
            if (!validatorResult.isStatus()) {
                throw new ServiceError(400, validatorResult.getMessage());
            }
            Response labWorkResponse = dao.getTarget().path(id).request().accept(MediaType.APPLICATION_XML).get();
            if (labWorkResponse.getStatus() != 200){
                ServerResponse serverResponse = labWorkResponse.readEntity(ServerResponse.class);
                throw new ServiceError(labWorkResponse.getStatus(), serverResponse.getMessage());
            }
            String strLab = labWorkResponse.readEntity(String.class);
            LabWork labWork = xmlConverter.fromStr(strLab, LabWork.class);
            int difficulty = Difficulty.valueOf(labWork.getDifficulty()).ordinal();
            int resultDifficulty = difficulty + steps;
            if (!difficultyMap.containsKey(resultDifficulty)){
                throw new ServiceError(400, "Increased Difficulty out of bounds");
            }
            labWork.setDifficulty(difficultyMap.get(resultDifficulty));
            Response changeDifficultyResponse = dao.getTarget().path(id).request().accept(MediaType.APPLICATION_XML).put(Entity.entity(labWork, MediaType.APPLICATION_XML));
            if (changeDifficultyResponse.getStatus() != 200){
                ServerResponse serverResponse = changeDifficultyResponse.readEntity(ServerResponse.class);
                throw new ServiceError(changeDifficultyResponse.getStatus(), serverResponse.getMessage());
            }
        } catch (ServiceError e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceError(500, "Server error, try again");
        }
        return new ServerResponse("Success");
    }

    @Override
    public ServerResponse test() {
        return new ServerResponse("Success");
    }
}