package com.example.granttrack_api.controller;

import com.example.granttrack_api.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/grants")
    public Map<String, Object> getGrantReport() {
        return reportService.getGrantReport();
    }
}
