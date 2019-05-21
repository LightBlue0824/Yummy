package edu.nju.yummy.data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    private static SessionFactory sessionFactory;

    @Bean
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateConfig.class) {
                if (sessionFactory == null) {
                    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
                    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}
