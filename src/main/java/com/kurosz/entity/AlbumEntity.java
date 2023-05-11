package com.kurosz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}