package io.github.rahulrajsonu.azureservice.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.azure")
public class AzureStorageConfig {

    @Value("${spring.azure.blob.store.connectionString}")
    private String connectionString;

    @Value("${spring.azure.blob.store.containerName}")
    private String containerName;

    @Bean
    public BlobContainerClient getBlobContainerClient() {
        return new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
    }
}
