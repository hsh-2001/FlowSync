package FlowSync.FlowSync.services;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.services.interfaces.IUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService implements IUploadService {
    private final S3Client s3Client;

    @Override
    public BaseResponse<String> upload(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        String key = "uploads/" + UUID.randomUUID() + extension;

        byte[] bytes = file.getBytes();

        String bucketName = "sakda";
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(
                        file.getContentType() != null
                                ? file.getContentType()
                                : "application/octet-stream"
                )
                .contentLength((long) bytes.length)
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(bytes)
        );

        return BaseResponse.success(
                "File uploaded successfully",
                key
        );
    }
}
