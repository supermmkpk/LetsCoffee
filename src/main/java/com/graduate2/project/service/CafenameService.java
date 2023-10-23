package com.graduate2.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.graduate2.project.repository.*;
import com.graduate2.project.domain.*;

import javax.transaction.Transactional;

@Service
public class CafenameService {
    @Autowired
    private CafenameRepository cafenameRepository; // db 접근을 위한 repository

    public void saveName(Cafename cafename){
        cafenameRepository.save(cafename); // cafename 객체를 db에 저장.
    }

}
