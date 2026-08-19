package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse<String>> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(uploadService.upload(file));
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(
            @RequestParam String key
    ) throws IOException {
        byte[] file = uploadService.readFile(key);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + getFileName(key) + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    private String getFileName(String key) {
        int lastSlash = key.lastIndexOf("/");
        if (lastSlash == -1) {
            return key;
        }
        return key.substring(lastSlash + 1);
    }
}
