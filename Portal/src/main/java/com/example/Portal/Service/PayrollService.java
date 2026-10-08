package com.example.Portal.Service;


import com.example.Portal.Client.AttendanceClient;
import com.example.Portal.Client.EmployeeClient;
import com.example.Portal.Dto.AttadanceRecord;
import com.example.Portal.Dto.EmployeeRecord;
import com.example.Portal.Dto.PayrollResponse;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Payroll;
import com.example.Portal.Entity.PayrollStatus;
import com.example.Portal.Kafka.KafkaEventProduce;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.PayrollRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private AttendanceClient attendanceClient;

    @Autowired
    private KafkaEventProduce kafkaEventProduce;

    public void generatePayroll(String month) {

        YearMonth ym = YearMonth.parse(month);
        int workingDays = ym.lengthOfMonth();

        // Fetch employees and attendance once
        List<EmployeeRecord> employees = employeeClient.getAllEmployess();
        List<AttadanceRecord> attendance =
                attendanceClient.getAttendance(ym.atDay(1), ym.atEndOfMonth());

        System.out.println("Total attendance records: " + attendance.size());

        // Debug attendance
        attendance.forEach(a ->
                System.out.println(
                        a.getEmployeeId() + " - " +
                                a.getDate() + " - " +
                                a.getStatus()
                )
        );
        Map<String, Long> attendanceMap =
                attendance.stream()
                        .filter(a -> a.getEmployeeId() != null)
                        .filter(a -> a.getStatus() != null)
                        .filter(a ->
                                a.getStatus().equalsIgnoreCase("PRESENT") ||
                                        a.getStatus().equalsIgnoreCase("LEAVE"))
                        .collect(Collectors.groupingBy(
                                AttadanceRecord::getEmployeeId,
                                Collectors.counting()
                        ));

        for (EmployeeRecord dto : employees) {

            // Skip if payroll already generated
            Optional<Payroll> existing =
                    payrollRepository.findByEmployeeEmployeeIdAndMonth(
                            dto.getEmployeeId(), month
                    );


            if (existing.isPresent()) {
                System.out.println("Payroll already exists for " + dto.getEmployeeId());
                continue;
            }
//            if (payrollRepository.findByEmployeeIdAndMonth(dto.getEmployeeId(), month)) {
//                continue;
//            }
            long paidDays = attendanceMap.getOrDefault(dto.getEmployeeId(), 0L);

            if (paidDays == 0) {
                System.out.println("Skipping employee: " + dto.getEmployeeId());
                continue;
            }
            // Fetch employee from local DB
            Employee employee = employeeRepository
                    .findByEmployeeId(dto.getEmployeeId())
                    .orElseThrow(() ->
                            new RuntimeException("Employee not found: " + dto.getEmployeeId()));

            System.out.println("Processing Employee: " + dto.getEmployeeId());



            // Correct paidDays calculation
//            long paidDays = attendance.stream()
//                    .filter(a -> a.getEmployeeId() != null)
//                    .filter(a -> dto.getEmployeeId()
//                            .equalsIgnoreCase(a.getEmployeeId()))
//                    .filter(a -> a.getStatus() != null)
//                    .filter(a ->
//                            a.getStatus().equalsIgnoreCase("PRESENT") ||
//                                    a.getStatus().equalsIgnoreCase("LEAVE"))
//                    .count();

            System.out.println("PaidDays: " + paidDays);

            // Base salary
            BigDecimal baseSalary =
                    employee.getBaseSalary() != null
                            ? employee.getBaseSalary()
                            : BigDecimal.ZERO;

            // Gross calculation
            BigDecimal gross = baseSalary
                    .multiply(BigDecimal.valueOf(paidDays))
                    .divide(BigDecimal.valueOf(workingDays),
                            2,
                            RoundingMode.HALF_UP);

            // PF = 12%
            BigDecimal pf = gross.multiply(BigDecimal.valueOf(0.12));

            // Tax = 10% if gross > 25000
            BigDecimal tax = gross.compareTo(BigDecimal.valueOf(25000)) > 0
                    ? gross.multiply(BigDecimal.valueOf(0.10))
                    : BigDecimal.ZERO;

            BigDecimal deductions = pf.add(tax);
            BigDecimal net = gross.subtract(deductions);

            // Create payroll record
            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setEmployeeId(employee.getEmployeeId());
            payroll.setName(employee.getName());
            payroll.setEmail(employee.getEmail());
            payroll.setDepartment(employee.getDepartment());
            payroll.setMonth(month);

            payroll.setBaseSalary(baseSalary);
            payroll.setPaidDays((int) paidDays);
            payroll.setWorkingDays(workingDays);

            payroll.setGross(gross);
            payroll.setPf(pf);
            payroll.setTax(tax);
            payroll.setTotalDeductions(deductions);
            payroll.setNet(net);

            payroll.setStatus(PayrollStatus.GENERATED);
            payroll.setCreatedAt(LocalDateTime.now());

            payrollRepository.save(payroll);

            kafkaEventProduce.sendPayrollGenerateEvent(payroll.getEmployeeId(),payroll.getEmail()
            ,payroll.getNet(),payroll.getMonth());


        }
    }

    public List<PayrollResponse> getMonth(String month){
        return payrollRepository.findAll().stream().map(PayrollResponse::toEntity).collect(Collectors.toList());
    }

    public List<PayrollResponse> getMonthandemployeeId(String employeeId,String month){
        return payrollRepository.findAll().stream().map(PayrollResponse::toEntity).collect(Collectors.toList());
    }
//public List<PayrollResponse> getMonth(String month) {
//    return payrollRepository.findByMonth(month)
//            .stream()
//            .map(p -> new PayrollResponse(
//                    p.getEmployeeId(),
//                    p.getName(),
//                    p.getEmail(),
//                    p.getDepartment(),
//                    p.getMonth(),
//                    p.getBaseSalary(),
//                    p.getPaidDays(),
//                    p.getWorkingDays(),
//                    p.getGross(),
//                    p.getPf(),
//                    p.getTax(),
//                    p.getTotalDeductions(),
//                    p.getNet(),
//                    p.getStatus(),
//                    p.getCreatedAt()
//            ))
//            .collect(Collectors.toList());
//}



    public byte[] generatepayslip(String employeeId,String month){
        Payroll payroll = payrollRepository
                .findByEmployeeEmployeeIdAndMonth(employeeId, month)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        String employyeId = payroll.getEmployee().getEmployeeId();
        BigDecimal pf=payroll.getPf();
        BigDecimal tax=payroll.getTax();
        BigDecimal deduct=payroll.getTotalDeductions();
        BigDecimal net=payroll.getNet();
        Document document=new Document();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PdfWriter.getInstance(document,byteArrayOutputStream);
        document.open();
        document.add(new Paragraph(employyeId));
        document.add(new Paragraph(String.valueOf(pf)));
        document.add(new Paragraph(String.valueOf(tax)));
        document.add(new Paragraph(String.valueOf(deduct)));
        document.add(new Paragraph(String.valueOf(net)));
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
