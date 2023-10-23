package com.graduate2.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.graduate2.project.service.CafenameService;
import com.graduate2.project.domain.*;

@RestController
@RequestMapping("/api")
public class CafenameController {
    @Autowired
    private CafenameService cafenameService; // 서비스 레이어의 CafenameService 주입.

    @PostMapping("/Savename")
    public ResponseEntity<String> saveName(@RequestBody Cafename cafename){
        //cafename 객체를 받아 db에 저장.
        cafenameService.saveName(cafename);
        return ResponseEntity.ok("카페 이름이 저장되었습니다.");
    }
}
