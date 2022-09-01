package io.github.rahulrajsonu.azureservice.controller;

import io.github.rahulrajsonu.azureservice.blobstorage.service.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

@RestController
public class FileStorageController {

    @Autowired
    private BlobStorageService blobStorageService;

    @GetMapping(value = "/read")
    public ResponseEntity<String> getFile(@RequestParam String fileName) {
        return ResponseEntity.ok(this.blobStorageService.read(fileName));
    }

    @PostMapping(value = "/write")
    public ResponseEntity<String> writeFile(@RequestParam String fileName, @RequestBody String content) throws FileNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        String blobUrl = this.blobStorageService.write(fileName, inputStream);
        return ResponseEntity.ok(blobUrl);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        return ResponseEntity.ok(this.blobStorageService.upload(file));
    }

}
