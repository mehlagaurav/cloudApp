package com.utils.filestorageservice.controller;

import com.utils.filestorageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value="file")MultipartFile file){
        String response=service.uploadFile(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
        byte[] byteArray=service.downloadFile(fileName);
        ByteArrayResource resource=new ByteArrayResource(byteArray);
        return ResponseEntity
                .ok()
                .contentLength(byteArray.length)
                .header("Content-type","application/octet-stream")
                .header("Content-disposition","attachment; fileName=\""+fileName+"\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        return new ResponseEntity<>(service.deleteFile(fileName),HttpStatus.OK);
    }
}
