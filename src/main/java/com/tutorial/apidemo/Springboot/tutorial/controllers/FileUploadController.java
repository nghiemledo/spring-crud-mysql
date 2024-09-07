package com.tutorial.apidemo.Springboot.tutorial.controllers;

import com.tutorial.apidemo.Springboot.tutorial.models.ResponseObject;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "api/v1/FileUpload")
public class FileUploadController {
    // This controller receive file/image from client
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam("file") MultipartFile file) {
        try {
            // Save file to a folder => use a service
        }
        catch (Exception e) {

        }
    }
}
