//package com.gsp.fileoperations.resource;
//
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.*;
//
//import static java.nio.file.Files.copy;
//import static java.nio.file.Paths.get;
//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
//import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
//
//@RestController
//@RequestMapping("/file")
//public class FileResource {
//
//    public static final String DIRECTORY = System.getProperty("user.home") + "/OneDrive/Desktop/Uploads";
//
//    @PostMapping("/upload")
//    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
//        List<String> filenames = new ArrayList<>();
//        for(MultipartFile file: multipartFiles){
//            String filename = StringUtils.cleanPath(file.getOriginalFilename());
//            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
//            System.out.println(fileStorage+" "+ filename+ " "+ DIRECTORY);
//            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
//            filenames.add(filename);
//        }
//        return ResponseEntity.ok().body(filenames);
//    }
//
//
//    @GetMapping("download/{filename}")
//    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
//        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
//        if(!Files.exists(filePath)){
//            throw new FileSystemNotFoundException(filename + " is not found!!");
//        }
//        Resource resource = new UrlResource(filePath.toUri());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("File-Name", filename);
//        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
//                .headers(httpHeaders).body(resource);
//    }
//
//}
