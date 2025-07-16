package org.example.botucsof3012.buoi3.util;


import org.example.botucsof3012.buoi3.model.KhachSan;
import org.example.botucsof3012.buoi3.model.Phong;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


public class HibernateConfig {
    private static final SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2016Dialect");
        properties.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        properties.put(Environment.URL, "jdbc:sqlserver://localhost:1433;databaseName=BoTucSOF3012;encrypt=true;trustServerCertificate=true;");
        properties.put(Environment.USER, "sa");
        properties.put(Environment.PASS, "gacon");
        properties.put(Environment.SHOW_SQL, "true");

        conf.addAnnotatedClass(Phong.class); // khai bao model ngay sau khi lam xong model thi ghi vao day, bao nhieu model thi bay nhieu giong
        conf.addAnnotatedClass(KhachSan.class);

        conf.setProperties(properties);
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    public static SessionFactory getFACTORY() {
        return FACTORY;
    }

    public static void main(String[] args) {
        System.out.println(getFACTORY());
    }
}
