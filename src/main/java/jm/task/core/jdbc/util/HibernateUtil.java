package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/users");
            settings.put(AvailableSettings.USER, "root");
            settings.put(AvailableSettings.PASS, "root");
            settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

            settings.put(AvailableSettings.SHOW_SQL, "true");
            settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
