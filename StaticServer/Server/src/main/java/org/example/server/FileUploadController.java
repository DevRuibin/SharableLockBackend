package org.example.server;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private static final String UPLOAD_DIR = "/uploads";

    @PostMapping("/upload-secrets")
    public ResponseEntity<FileNameResponse> uploadFileSecrets(@RequestParam("file") MultipartFile file) {
        try {
            File dest = new File(UPLOAD_DIR, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(dest);
            return ResponseEntity.ok().body(new FileNameResponse(dest.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<FileNameResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File dest = new File(UPLOAD_DIR, generateFileName(file));
            file.transferTo(dest);
            return ResponseEntity.ok().body(new FileNameResponse(dest.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/upload")
    public ResponseEntity<List<String>> uploadPage(Model model) {
        File uploadDir = new File(UPLOAD_DIR);
        List<String> fileNames = Arrays.stream(Objects.requireNonNull(uploadDir.listFiles()))
                .map(File::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(fileNames);
    }

    private String generateFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + UUID.randomUUID() + file.getOriginalFilename();
    }

}

