package FlowSync.FlowSync.services;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.services.interfaces.IUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService implements IUploadService {

    private final S3Client s3Client;

    private static final String BUCKET_NAME = "sakda";
    private static final String UPLOAD_FOLDER = "uploads/";

    @Override
    public BaseResponse<String> upload(MultipartFile file) throws IOException {

        // 1. Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 2. Get original filename
        String originalFilename = file.getOriginalFilename();

        // 3. Get file extension
        String extension = getFileExtension(originalFilename);

        // 4. Generate unique S3 key
        String key = UPLOAD_FOLDER + UUID.randomUUID() + extension;

        // 5. Get content type
        String contentType = file.getContentType() != null
                ? file.getContentType()
                : "application/octet-stream";

        // 6. Create S3 request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .contentType(contentType)
                .contentLength(file.getSize())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            inputStream,
                            file.getSize()
                    )
            );
        }

        return BaseResponse.success(
                "File uploaded successfully",
                key
        );
    }

    public byte[] readFile(String key) throws IOException {

        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("File key is required");
        }

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);

        return response.asByteArray();
    }

    private String getFileExtension(String filename) {

        if (filename == null || filename.isBlank()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");

        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex).toLowerCase();
    }
}