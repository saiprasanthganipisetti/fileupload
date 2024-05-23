package com.gsp.fileoperations.resource;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.util.Optional;



@RestController
public class Sample {
    @Value("${app.deploy_date:}")
    private String deployDate;

    @RequestMapping(method = RequestMethod.GET, value="/up")
    public String hello(){
        return "Demo application (Single Module) is up";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public String hi(@RequestParam(required = false) Optional<String> name) {
        if (name.isPresent())
            return "Hi, " + name.get();
        else
            return "Hi, anonymous";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public String buildTime(){
        return "The Deploy Time is: " + deployDate;
    }
    //e703897

    public String DIRECTORY = System.getProperty("user.home") + "/OneDrive/Desktop/Uploads";
    List<String> filenames = new ArrayList<>();

    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles, @RequestParam("path")String folder_path) throws IOException {
        if(!folder_path.isBlank()){DIRECTORY = folder_path;}
        for(MultipartFile file: multipartFiles){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            System.out.println(fileStorage+" "+ filenames+ " "+ DIRECTORY);
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return multipartFiles.size() + " Files are uploaded succesfully";
    }


    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)){
            throw new FileSystemNotFoundException(filename + " is not found!!");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    @RequestMapping("/files")
    public List<String> getAllFiles() throws IOException {
        Stream<Path> stream = Files.list(Paths.get(DIRECTORY));
        return stream.filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    @RequestMapping("/dir")
    public String returnDIR(){
        return DIRECTORY;
    }

//    @RequestMapping("/error")
//    public String handleError(){
//        return DIRECTORY;
//    }
    //e703897
}













//package demo.cicd.javapoc.application.controller;



//import org.springframework.beans.factory.annotation.Value;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import org.springframework.web.bind.annotation.RequestParam;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import org.springframework.core.io.Resource;
//
//import org.springframework.core.io.UrlResource;
//
//import org.springframework.http.MediaType;
//
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import org.springframework.http.HttpHeaders;
//
//import org.springframework.util.StringUtils;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//import java.nio.file.*;
//
//import java.util.*;
//
//import java.util.stream.Collectors;
//
//import java.util.stream.Stream;
//
//import static java.nio.file.Files.copy;
//
//import static java.nio.file.Paths.get;
//
//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
//
//import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
//
//
//
//import java.util.Optional;
//
//
//
//@RestController
//
//public class Sample {
//
//    @Value("${app.deploy_date:}")
//
//    private String deployDate;
//
//
//
//    @RequestMapping(method = RequestMethod.GET, value="/up")
//
//    public String hello(){
//
//        return "Demo application (Single Module) is up";
//
//    }
//
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/hi")
//
//    public String hi(@RequestParam(required = false) Optional<String> name) {
//
//        if (name.isPresent())
//
//            return "Hi, " + name.get();
//
//        else
//
//            return "Hi, anonymous";
//
//    }
//
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/info")
//
//    public String buildTime(){
//
//        return "The Deploy Time is: " + deployDate;
//
//    }
//
//
//
//    public String DIRECTORY = System.getProperty("user.home") + "/Desktop/Uploads";
//
//    List<String> filenames = new ArrayList<>();
//    @PostMapping("/upload")
//
//    public String uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles, @RequestParam("path")String folder_path) throws IOException {
//
//        if(!folder_path.isBlank()){DIRECTORY = folder_path;}
//
//        for(MultipartFile file: multipartFiles){
//
//            String filename = StringUtils.cleanPath(file.getOriginalFilename());
//
//            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
//
//            System.out.println(fileStorage+" "+ filenames+ " "+ DIRECTORY);
//
//            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
//
//            filenames.add(filename);
//
//        }
//
//        return multipartFiles.size() + " Files are uploaded succesfully";
//
//    }
//
//
//
//
//
//    @GetMapping("download/{filename}")
//
//    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
//
//        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
//
//        if(!Files.exists(filePath)){
//
//            throw new FileSystemNotFoundException(filename + " is not found!!");
//
//        }
//
//        Resource resource = new UrlResource(filePath.toUri());
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        httpHeaders.add("File-Name", filename);
//
//        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
//
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
//
//                .headers(httpHeaders).body(resource);
//
//    }
//
//
//
//    @RequestMapping("/files")
//
//    public List<String> getAllFiles() throws IOException {
//
//        Stream<Path> stream = Files.list(Paths.get(DIRECTORY));
//
//        return stream.filter(Files::isRegularFile)
//
//                .map(Path::getFileName)
//
//                .map(Path::toString)
//
//                .collect(Collectors.toList());
//
//    }
//
//
//
//    @RequestMapping("/dir")
//
//    public String returnDIR(){
//
//        return DIRECTORY;
//
//    }
//
//}
//
//
//
