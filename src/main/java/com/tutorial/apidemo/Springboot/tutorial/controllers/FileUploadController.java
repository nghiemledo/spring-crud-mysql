package com.tutorial.apidemo.Springboot.tutorial.controllers;

import com.tutorial.apidemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.apidemo.Springboot.tutorial.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/v1/FileUpload")
public class FileUploadController {
    // This controller receive file/image from client

    // Inject Storage Service here
    @Autowired
    private IStorageService service;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam("file") MultipartFile file) {
        try {
            // Save file to a folder => use a service
            String generatedFileName = service.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", generatedFileName)
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", e.getMessage(), "")
            );
        }
    }

    // get file name in browser
    @GetMapping("files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable("fileName") String fileName) {
        try {
             byte[] bytes = service.readFileContent(fileName);
             return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
         catch (Exception e) {
            return ResponseEntity.noContent().build();
         }
    }

    // load all uploaded image
    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadedFile() {
        try {
            List<String> urls = service.loadAll()
                    .map(path -> {
                        // convert file name to Url(send request "readDetailFile)
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile", path.getFileName()).build().toUri().toString();
                        return urlPath;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok", "List file successfully", urls));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject("failed", "List file failed", new String[] {}));
        }

    }
}
