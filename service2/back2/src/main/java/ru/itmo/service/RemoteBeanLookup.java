package ru.itmo.service;

import service.SecondServiceI;
import stringEntity.Discipline;
import utils.DisciplineResult;
import utils.LabWorksResult;
import utils.ServerResponse;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteBeanLookup {

    public static SecondServiceI lookupRemoteStatelessBean() {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        try {
            final Context context = new InitialContext(jndiProperties);
            final String appName = "global";
            final String moduleName = "ejb_back2-snapshot";
            final String beanName = "SecondService";
            final String viewClassName = SecondServiceI.class.getName();
            String lookupName = "java:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName;
            System.out.println(lookupName);
            return (SecondServiceI) context.lookup(lookupName);
        } catch (NamingException e) {
            System.out.println("не получилось (");
            e.printStackTrace();
            return new SecondServiceI() {

                @Override
                public DisciplineResult getDisciplines() {
                    throw new EJBException("bean is not available");
                }

                @Override
                public Discipline getDiscipline(String stringId) {
                    throw new EJBException("bean is not available");                }

                @Override
                public ServerResponse createDiscipline(String stringDiscipline) {
                    throw new EJBException("bean is not available");                }

                @Override
                public LabWorksResult getDisciplineLabWorks(String stringDisciplineId) {
                    throw new EJBException("bean is not available");                }

                @Override
                public ServerResponse addLabWorkToDiscipline(String stringDisciplineId, String stringLabWorkId) {
                    throw new EJBException("bean is not available");                }

                @Override
                public ServerResponse removeLabWorkFromDiscipline(String stringDisciplineId, String stringLabWorkId) {
                    throw new EJBException("bean is not available");                }

                @Override
                public ServerResponse increaseLabWorkDifficulty(String id, String stringSteps) {
                    throw new EJBException("bean is not available");                }

                @Override
                public ServerResponse test() {
                    throw new EJBException("bean is not available");
                }
            };
        }
    }
}
