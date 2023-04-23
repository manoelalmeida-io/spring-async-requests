package sptech.school.asyncrequests.service.customer;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import sptech.school.asyncrequests.domain.data.CustomerRepository;
import sptech.school.asyncrequests.service.customer.dto.CustomerExportXlsxDto;
import sptech.school.asyncrequests.service.customer.dto.CustomerMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerExportService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public void writeFileToOutputStream() throws IOException {
    Workbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("Customers");
    CreationHelper createHelper = workbook.getCreationHelper();

    List<CustomerExportXlsxDto> customers = this.customerRepository.findAll()
        .stream().map(this.customerMapper::toExportXlsxDto).toList();

    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("First Name");
    headerRow.createCell(1).setCellValue("Last Name");
    headerRow.createCell(2).setCellValue("Email");
    headerRow.createCell(3).setCellValue("Phone");
    headerRow.createCell(4).setCellValue("Agency");
    headerRow.createCell(5).setCellValue("Number");
    headerRow.createCell(6).setCellValue("Bank");
    headerRow.createCell(7).setCellValue("Created At");
    headerRow.createCell(8).setCellValue("Updated At");

    int rowNum = 1;

    for (CustomerExportXlsxDto customer : customers) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(customer.getFirstName());
      row.createCell(1).setCellValue(customer.getLastName());
      row.createCell(2).setCellValue(customer.getEmail());
      row.createCell(3).setCellValue(customer.getPhone());
      row.createCell(4).setCellValue(customer.getAgency());
      row.createCell(5).setCellValue(customer.getNumber());
      row.createCell(6).setCellValue(customer.getBank());
      row.createCell(7).setCellValue(createHelper.createRichTextString(customer.getCreatedAt().toString()));
      row.createCell(8).setCellValue(createHelper.createRichTextString(customer.getUpdatedAt().toString()));
    }

    for (int i = 0; i < 9; i++) {
      sheet.autoSizeColumn(i);
    }

    FileOutputStream outputStream = new FileOutputStream("customers.xlsx");
    workbook.write(outputStream);
    workbook.close();
  }
}
