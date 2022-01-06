package ru.itmo.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.itmo.entity.*;

import java.io.Serializable;

public class HibernateUtil implements Serializable {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() throws ExceptionInInitializerError{
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(LabWork.class);
            configuration.addAnnotatedClass(Coordinates.class);
            configuration.addAnnotatedClass(Difficulty.class);
            configuration.addAnnotatedClass(Location.class);
            configuration.addAnnotatedClass(Person.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("build SeesionFactory failed :" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
