package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();

            session.save(Candidate.of("Anna", 5, 5000));
            session.save(Candidate.of("Petr", 3, 3000));
            session.save(Candidate.of("Mike", 7, 7000));

            Query<Candidate> query = session.createQuery("from Candidate", Candidate.class);
            for (Candidate candidate : query.list()) {
                System.out.println(candidate);
            }

            query =  session.createQuery("from Candidate as can where can.id = 3", Candidate.class);
            System.out.println(query.uniqueResult());

            query = session.createQuery("from Candidate as can where can.name = 'Mike'", Candidate.class);
            for (Candidate candidate : query.list()) {
                System.out.println(candidate);
            }

            session.createQuery("update Candidate can set can.experience = 8, can.salary = 8000 where can.id = 3")
                    .executeUpdate();

            session.createQuery("delete from Candidate can where can.id = 1")
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }
}
