package com.kurosz.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "songs2")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "album_id")
//    private AlbumEntity album;
//
//    public AlbumEntity getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(AlbumEntity album) {
//        this.album = album;
//    }


    public SongEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
