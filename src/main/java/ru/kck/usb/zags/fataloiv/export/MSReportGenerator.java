package ru.kck.usb.zags.fataloiv.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kck.usb.zags.fataloiv.dao.Death;
import ru.kck.usb.zags.fataloiv.utils.ExportUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;


/**
 * @author Ilya V. Kupriyanov
 * @version 15/02/2020
 */

@Component
public class MSReportGenerator {
    private static final transient Log LOG = LogFactory.getLog(MSReportGenerator.class);
    private static final String EXPORT_FILE_NAME = "deathInfo";

    @Autowired
    private ExportUtils exportUtils;

    public byte[] getXlsxReportFile(List<Death> items) {
        String templateName = String.format("/ru/kck/usb/zags/templates/%s.xlsx", EXPORT_FILE_NAME);
        InputStream inputStream = this.getClass().getResourceAsStream(templateName);

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            XSSFCreationHelper helper = workbook.getCreationHelper();

            int index = 2;
            for (Death death : items) {
                exportUtils.createRow(death, sheet, index, 0, helper);
                index++;
            }

            inputStream.close();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            LOG.error(e);
        }

        return null;
    }

    public String getCSVReportFile(List<Death> deathList) throws IOException {
        Set<String[]> contentSet = exportUtils.getSetFromDeathList(deathList);
        String[] header = exportUtils.getScvHeader();
        String fileContent = exportUtils.getCSVFileBytesFromSet(contentSet,header);
        return fileContent;
    }

}
