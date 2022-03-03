package com.logistic.parcel.controller;

import com.logistic.parcel.service.ExcelService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ParcelController {

    Logger logger = LoggerFactory.getLogger(ParcelController.class.getTypeName());
    @Autowired
    HttpSession session;
    @Autowired
    HttpServletRequest servletRequest;
    private Map<String, Long> map = new HashMap<>();
    @Autowired
    private ExcelService excelService;

    @RequestMapping("hello")
    public String index() {
        Long timeOfNow = new Date().getTime();
        if (session.isNew()) {
            System.out.println("JSESSION" + session.getId());
            System.out.println(servletRequest.getRemoteAddr());
            map.put(session.getId(), session.getLastAccessedTime());
        } else {
            if (timeOfNow - map.get(session.getId()) > 10000) {
                session.invalidate();
                System.out.println("高频访问");
                return "/pages/errorPage.html";
            }
        }

        System.out.println("上一次访问时间 = " + session.getLastAccessedTime());
        return "index";
    }

    @PostMapping("/upload")
    public ResponseEntity<byte[]> processBarcodeByExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            return null;
        }
        logger.info("[根路径] " + System.getProperty("user.dir"));
        String userDir = System.getProperty("user.dir") + "/";
        try {
            byte[] bytes = file.getBytes();
            String fileName = userDir + file.getOriginalFilename();
            File oldFile = new File(fileName);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            Path path = Paths.get(fileName);
            path = Files.write(path, bytes);

            if (path == null) {
                return null;
            } else {
                excelService.processExcel(path.toString());
            }
            // today file
            File todayFile = new File(path.toString());
            DateTimeFormatter nTZ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStr = LocalDateTime.now().format(nTZ);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // substring of extension
            headers.setContentDispositionFormData("attachment", timeStr + file.getOriginalFilename().
                    substring(file.getOriginalFilename().lastIndexOf(".")));
            return new ResponseEntity<>(FileUtils.readFileToByteArray(todayFile), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
