package com.example.demo.entity;

public class FileBuilder {
    private String fileName;
    private Long id;

    public FileBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public File createFile() {
        return new File(fileName);
    }
}