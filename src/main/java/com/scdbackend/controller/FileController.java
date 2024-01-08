package com.scdbackend.controller;

import com.scdbackend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final StorageService service;

    @Autowired
    public FileController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/image")
    public ResponseEntity singleImageDownload(@RequestParam("path") String imgPath) {
        Resource resource = service.readImage(imgPath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/image")
    public ResponseEntity singleImageUpload(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok("\"" + service.writeImage(image) + "\"");
    }

}
