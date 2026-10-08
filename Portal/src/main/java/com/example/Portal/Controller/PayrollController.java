package com.example.Portal.Controller;


import com.example.Portal.Dto.PayrollResponse;
import com.example.Portal.Service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PayrollController {


        @Autowired
        PayrollService payrollService;


        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/api/employee/Generate")
        public ResponseEntity<?> generate(@RequestParam String month) {

            payrollService.generatePayroll(month);

            return ResponseEntity.ok(
                    Map.of(
                            "ok", true,
                            "message", "Payroll generate Success",
                            "month", month
                    )
            );
        }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/payroll")
    public List<PayrollResponse> getpayslip(@RequestParam String month) {

       return payrollService.getMonth(month);
        }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/payroll/emp")
    public List<PayrollResponse> getpayslipandemployer(@RequestParam String employeeId, @RequestParam String month) {

       return payrollService.getMonthandemployeeId(employeeId,month);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/payroll/payslip/pdf")
    public ResponseEntity<byte[]> downloadPayslip(
            @RequestParam String employeeId,
            @RequestParam String month) throws Exception {

        byte[] pdf = payrollService.generatepayslip(employeeId, month);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=payslip-" + employeeId + "-" + month + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }



}
