package com.kurosz.infrastructure;

import com.kurosz.entity.SongEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongsRepositoryTest {
    private SessionFactory sessionFactory;

//    private EntityManager entityManager;

    private SongsRepository songsRepository;

    @BeforeAll
    public void setUp() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(SongEntity.class);
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        Session session = sessionFactory.openSession();
        songsRepository = new SongsRepository(session);
    }


    @Test
    void shouldAddNewSong(){
        var song = new SongEntity();

        song.setTitle("Test song");


        songsRepository.save(song);
    }


}