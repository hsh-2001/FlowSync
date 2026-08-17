package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.services.UploadService;
import FlowSync.FlowSync.services.interfaces.IUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<String>> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(
                uploadService.upload(file)
        );
    }
}
