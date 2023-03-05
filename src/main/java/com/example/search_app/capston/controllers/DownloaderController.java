package com.example.search_app.capston.controllers;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Controller
public class DownloaderController {
    private final Environment environment;

    public DownloaderController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadReportWithFileName(@RequestParam String fileName) {
        File file = new File(environment.getProperty("fileStore.directory") + "/" + fileName);
        byte[] downloadAbleFile = null;
        try {
            downloadAbleFile = Files.readAllBytes(file.toPath());
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileName);
        return new ResponseEntity<>(downloadAbleFile, headers, HttpStatus.OK);
    }

}
