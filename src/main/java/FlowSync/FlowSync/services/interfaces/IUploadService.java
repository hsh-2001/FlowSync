package FlowSync.FlowSync.services.interfaces;

import FlowSync.FlowSync.models.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    BaseResponse<String> upload(MultipartFile file) throws IOException;
}
