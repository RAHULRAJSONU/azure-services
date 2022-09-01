package io.github.rahulrajsonu.azureservice.blobstorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface BlobStorageService {
    public String upload(MultipartFile multipartFile);
    public String write(String file, InputStream content);
    public String read(String file);
}