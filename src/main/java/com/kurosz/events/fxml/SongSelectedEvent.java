package com.kurosz.events.fxml;

public class SongSelectedEvent extends FxmlEvent{

    private final String title;
    private final String author;

    private final String path;
    private final SongSelection selectedFrom;


    public SongSelectedEvent(String title, String author, String path, SongSelection selectedFrom) {
        this.title = title;
        this.author = author;
        this.path = path;
        this.selectedFrom = selectedFrom;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPath() {
        return path;
    }

    public SongSelection getSelectedFrom() {
        return selectedFrom;
    }

    @Override
    public String toString() {
        return "SongSelectedEvent{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", path='" + path + '\'' +
                ", selectedFrom=" + selectedFrom +
                '}';
    }
}

