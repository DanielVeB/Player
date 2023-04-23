package com.kurosz.domain;

import java.util.List;

public enum SongFormat {
    MP3(List.of("audio/mpeg", "audio/x-mpeg")),
    WAV(List.of("audio/wav", "audio/x-wav")),
    AIFF(List.of("audio/aiff", "audio/x-aiff", "audio/aiffc", "audio/x-aiffc")),
    FLAC(List.of("audio/flac", "audio/x-flac")),
    ;
    private final List<String> validContentType;

    SongFormat(List<String> validContentType) {
        this.validContentType = validContentType;
    }

    public static SongFormat fromContentType(String contentType) throws IllegalArgumentException {
        for (SongFormat songFormat : values()) {
            if (songFormat.validContentType.contains(contentType)) {
                return songFormat;
            }
        }
        throw new IllegalArgumentException("Invalid content type");
    }
}
