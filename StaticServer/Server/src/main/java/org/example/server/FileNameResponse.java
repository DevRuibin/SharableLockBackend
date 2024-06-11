package org.example.server;

import java.io.Serializable;

public class FileNameResponse implements Serializable {
    private String fileName;

    public FileNameResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

