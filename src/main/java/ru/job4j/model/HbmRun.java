package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
             Session session = sf.openSession()) {
            session.beginTransaction();

            CarBrand carBrand = CarBrand.of("Toyota");
            carBrand.addModel(CarModel.of("Corolla"));
            carBrand.addModel(CarModel.of("Camry"));
            carBrand.addModel(CarModel.of("Fortuner"));
            carBrand.addModel(CarModel.of("Highlander"));
            carBrand.addModel(CarModel.of("RAV4"));

            session.save(carBrand);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
