package lk.ijse.eca.memberservice.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lk.ijse.eca.memberservice.service.FileStorageService;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.UUID;

@Service
@Primary
public class GCPCloudStorageService implements FileStorageService {

    private final Storage storage;
    private final String bucketName;

    public GCPCloudStorageService(Storage storage, @Value("${gcp.storage.bucket-name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    @Override
    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String savedFilename = UUID.randomUUID() + extension;

        try {
            BlobId blobId = BlobId.of(bucketName, savedFilename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            storage.createFrom(blobInfo, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return "https://storage.googleapis.com/" + bucketName + "/" + savedFilename;
    }
}
