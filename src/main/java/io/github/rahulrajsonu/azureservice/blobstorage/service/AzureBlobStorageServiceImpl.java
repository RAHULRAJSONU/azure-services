package io.github.rahulrajsonu.azureservice.blobstorage.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.specialized.AppendBlobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AzureBlobStorageServiceImpl implements BlobStorageService {

    @Autowired
    private BlobContainerClient containerClient;

    private final Logger LOGGER = LoggerFactory.getLogger(AzureBlobStorageServiceImpl.class);

    @Override
    public String upload(MultipartFile multipartFile) {
        BlobClient blob = containerClient.getBlobClient(multipartFile.getOriginalFilename());
        try{
           blob.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
           return blob.getBlobUrl();
        }catch (IOException ioe) {
            throw new RuntimeException("Error while uploading file to azure blob, file: "+multipartFile.getOriginalFilename()+", error: "+ioe.getLocalizedMessage());
        }
    }

    @Override
    public String write(String blobName, InputStream content) {
        try {
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            AppendBlobClient appendBlobClient = blobClient.getAppendBlobClient();
            appendBlobClient.createIfNotExists();
            appendBlobClient.appendBlock(content, content.available());
            return blobClient.getBlobUrl();
        } catch (Exception e) {
            throw new RuntimeException("Error while writing file to azure blob: "+blobName+", error: "+e.getLocalizedMessage());
        }
    }

    @Override
    public String read(String blobName) {
        try {
            AppendBlobClient appendBlobClient = containerClient.getBlobClient(blobName).getAppendBlobClient();
            return appendBlobClient.downloadContent().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error reading azure blob: "+blobName+", error: "+e.getLocalizedMessage());
        }
    }

}