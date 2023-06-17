package com.academiadesenvolvedor.persistence;

import com.academiadesenvolvedor.models.Produto;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory ;

    static {
        try{
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(Produto.class);
            Metadata metadata = metadataSources.buildMetadata();
            sessionFactory = metadata.buildSessionFactory();
        }catch (Exception e){
            System.out.println("Erro ao Iniciar Hibernate:" + e.getMessage());
            throw new RuntimeException("Hibernate is null");
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
