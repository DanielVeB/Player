package com.kurosz.infrastructure;

import com.kurosz.entity.SongEntity;
import jakarta.persistence.EntityManager;


public class SongsRepository {

    private final EntityManager entityManager;

    public SongsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(SongEntity song){
        var tx = entityManager.getTransaction();
        tx.begin();
        if(song.getId() == null){
            entityManager.persist(song);
        }else {
            entityManager.merge(song);
        }
        tx.commit();
    }

    public SongEntity getById(Long id){
        return entityManager.find(SongEntity.class,id);
    }

//    public List<SongEntity> getAll(){
//        return entityManager.get
//    }
}
