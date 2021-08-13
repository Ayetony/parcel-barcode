package com.logistic.parcel.service.impl;

import com.logistic.parcel.service.ExcelService;
import com.logistic.parcel.utils.ReadAndFormatExcel;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void processExcel(String proPath) {
        ReadAndFormatExcel.format(proPath);
    }
}
