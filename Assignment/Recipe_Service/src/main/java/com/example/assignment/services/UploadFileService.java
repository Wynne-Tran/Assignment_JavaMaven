
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is UploadFileService, this page was implement in RegisterController and RecipeController
 It will handle image upload.
 ******************************************************************************** */

package com.example.assignment.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadFileService {

    public void UploadFileHandling(MultipartFile files){
        if(!files.isEmpty()){
            try {
                String fileName = files.getOriginalFilename();
                String dirLocation ="Assignment/src/main/resources/static/image/";
                File file = new File(dirLocation);
                if(!file.exists()) {
                    file.mkdir();
                }
                byte[] bytes = files.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation+new File(fileName)));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
