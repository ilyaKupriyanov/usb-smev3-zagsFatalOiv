package ru.kck.usb.zags.fataloiv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kck.usb.zags.fataloiv.dao.Death;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExportUtils {

    @Value("${zagsFatalOivExport.dataWriteModeCSV:JSON}")
    private DataWriteMode dataWriteModeCSV;

    @Value("${zagsFatalOivExport.dataWriteModeXLSX:READABLE}")
    private DataWriteMode dataWriteModeXLSX;

    public int createRow(Death death, Sheet sheet, int rowIndex, int colIndex, XSSFCreationHelper helper) {
        Row rowData = sheet.getRow(rowIndex);
        if (rowData == null) {
            rowData = sheet.createRow(rowIndex);
        }

        Cell cell = rowData.createCell(0);
        cell.setCellValue(death.getRegInfoId());

        cell = rowData.createCell(1, colIndex);
        CellStyle cs = cell.getCellStyle();
        cs.setDataFormat(helper.createDataFormat().getFormat("yyyy-mm-dd"));
        cell.setCellValue(death.getRegInfoDate());
        cell.setCellStyle(cs);

        cell = rowData.createCell(2, colIndex);
        cell.setCellValue(death.getRegDate());

        cell = rowData.createCell(3);
        cell.setCellValue(death.getRegNumber());

        cell = rowData.createCell(4);
        cell.setCellValue(death.getZagsName());

        cell = rowData.createCell(5);
        cell.setCellValue(death.getZagsCode());

        cell = rowData.createCell(6);
        cell.setCellValue(formatXLSX(death.getDeathCerts()));

        cell = rowData.createCell(7);
        cell.setCellValue(formatXLSX(death.getDeathCertsRepeat()));

        cell = rowData.createCell(8);
        cell.setCellValue(death.getSurname());

        cell = rowData.createCell(9);
        cell.setCellValue(death.getFirstname());

        cell = rowData.createCell(10);
        cell.setCellValue(death.getMiddlename());

        cell = rowData.createCell(11);
        if (dataWriteModeXLSX.equals(DataWriteMode.READABLE)) {
            cell.setCellValue(performSex(death.getSex()));
        } else {
            cell.setCellValue(death.getSex());
        }
        cell = rowData.createCell(12);
        cell.setCellValue(death.getCitizenshipOKSM());

        cell = rowData.createCell(13);
        cell.setCellValue(death.getCitizenshipCountryName());

        cell = rowData.createCell(14);
        cell.setCellValue(formatXLSX(death.getBirthDate()));

        cell = rowData.createCell(15);
        cell.setCellValue(formatXLSX(death.getBirthPlace()));

        cell = rowData.createCell(16);
        cell.setCellValue(formatXLSX(death.getAddressRF()));

        cell = rowData.createCell(17);
        cell.setCellValue(formatXLSX(death.getAddressRFText()));

        cell = rowData.createCell(18);
        cell.setCellValue(formatXLSX(death.getAddressNotRF()));

        cell = rowData.createCell(19);
        cell.setCellValue(formatXLSX(death.getIdentityDocInfo()));

        cell = rowData.createCell(20);
        cell.setCellValue(formatXLSX(death.getDeathDate()));

        cell = rowData.createCell(21);
        cell.setCellValue(death.getDeathTime());

        cell = rowData.createCell(22);
        cell.setCellValue(formatXLSX(death.getDeathPlace()));

        cell = rowData.createCell(23);
        cell.setCellValue(formatXLSX(death.getDeathDocument()));

        cell = rowData.createCell(24);
        cell.setCellValue(formatXLSX(death.getDeathDocIssuer()));

        cell = rowData.createCell(25);
        cell.setCellValue(formatXLSX(death.getDeathDocFIOIP()));

        cell = rowData.createCell(26);
        cell.setCellValue(formatXLSX(death.getDeathDocFIOFL()));

        cell = rowData.createCell(27);
        cell.setCellValue(formatXLSX(death.getDeathActChangesInfo()));

        return rowIndex;

    }

    public String getCSVFileBytesFromSet(Set<String[]> content, String[] header) throws IOException {

        StringWriter sw = new StringWriter();
        CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader(header));
        for (String[] record : content) {
            printer.printRecord(record);
        }
        sw.flush();
        sw.close();
        String result = sw.toString();
        return result;
    }

    public Set<String[]> getSetFromDeathList(List<Death> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Set<String[]> contentSet = new LinkedHashSet<>();
        for (Death death : list) {
            String regInfoId = getValue(death.getRegInfoId());
            String regInfoDate = df.format(death.getRegInfoDate());
            String regDate = df.format(death.getRegDate());
            String regNumber = getValue(death.getRegNumber());
            String zagsName = getValue((death.getZagsName()));
            String zagsCode = getValue((death.getZagsCode()));
            String stateDesc = getValue(death.getStateDesc());//
            String stateCode = getValue(death.getStateCode());//
            String stateDate = df.format(death.getStateDate());//
            String deathCerts = getValue(formatCSV(death.getDeathCerts()));
            String deathCertsRepeat = getValue(formatCSV(death.getDeathCertsRepeat()));
            String deathCause = getValue(formatCSV(death.getDeathCause()));
            String surname = getValue(death.getSurname());
            String firstName = getValue(death.getFirstname());
            String middleName = getValue(death.getMiddlename());
            String sex;
            if (dataWriteModeCSV.equals(DataWriteMode.READABLE)) {
                sex = performSex(getValue(death.getSex()));
            } else {
                sex = getValue(death.getSex());
            }
            String citizenshipOKSM = getValue(death.getCitizenshipOKSM());
            String citizenshipCountryName = getValue(death.getCitizenshipCountryName());
            String birthDate = getValue(formatCSV(death.getBirthDate()));
            String birthPlace = getValue(formatCSV(death.getBirthPlace()));
            String addressRF = getValue(formatCSV(death.getAddressRF()));
            String addressRFText = getValue(formatCSV(death.getAddressRFText()));
            String addressNotRF = getValue(formatCSV(death.getAddressNotRF()));
            String identityDocInfo = getValue(formatCSV(death.getIdentityDocInfo()));
            String deathDate = getValue(formatCSV(death.getDeathDate()));
            String deathTime = getValue(death.getDeathTime());
            String deathPlace = getValue(formatCSV(death.getDeathPlace()));
            String aboutPerson = getValue(formatCSV(death.getAboutPerson()));//
            String aboutDeath = getValue(formatCSV(death.getAboutDeath()));//
            String childDeath = getValue(formatCSV(death.getChildDeath()));//
            String doctor = getValue(formatCSV(death.getDoctor()));//
            String applicantInfo = getValue(formatCSV(death.getApplicantInfo()));//
            String unindentPerson = (death.getUnindentPerson() != null) ? death.getUnindentPerson() ? "Да" : "Нет" : "";
            String deathDocument = getValue(formatCSV(death.getDeathDocument()));
            String deathDocIssuer = getValue(formatCSV(death.getDeathDocIssuer()));
            String deathDocFIOIP = getValue(formatCSV(death.getDeathDocFIOIP()));
            String deathDocFIOFL = getValue(formatCSV(death.getDeathDocFIOFL()));
            String deathActChangesInfo = getValue(formatCSV(death.getDeathActChangesInfo()));
            String versionDate = death.getVersionDate() == null ?  "" : getValue(df.format(death.getVersionDate()));//
            String versionNumber = getValue(death.getVersionNumber());//
            String recorddate = death.getRecordDate() == null ? "" : getValue(dft.format(death.getRecordDate()));//
            String smevMessageId = getValue(death.getSmevMessageId());//

            String[] record = {regInfoId,regInfoDate,regDate,regNumber,zagsName,zagsCode,
                    stateDesc, stateCode, stateDate,
                    deathCerts, deathCertsRepeat, deathCause, surname, firstName, middleName,
                    sex, citizenshipOKSM, citizenshipCountryName, birthDate, birthPlace, addressRF,
                    addressRFText, addressNotRF, identityDocInfo, deathDate, deathTime, deathPlace,
                    aboutPerson, aboutDeath, childDeath, doctor, applicantInfo,
                    unindentPerson, deathDocument, deathDocIssuer, deathDocFIOIP, deathDocFIOFL, deathActChangesInfo,
                    versionDate, versionNumber,  recorddate, smevMessageId};
            contentSet.add(record);
        }
     return contentSet;
    }

    public String[] getScvHeader() {
        String[] header = {"Идентификатор сведений, сформированный поставщиком","Дата, на которую сформированы сведения",
                "Дата составления записи акта о смерти","Номер записи акта о смерти",
                "Полное наименование органа ЗАГС, которым произведена государственная регистрация акта гражданского состояния",
                "Код органа ЗАГС","Наименование статуса записи акта о смерти","Код статуса записи акта о смерти","Дата начала действия статуса записи акта о смерти",
                "Сведения о выданном Свидетельстве о смерти", "Сведения о повторно выданных Свидетельствах о смерти",
                "Сведения о причинах смерти","Фамилия","Имя","Отчество", "Пол","Код страны гражданства","Полное наименование страны гражданства",
                "Дата рождения | Месяц и год рождения | Год рождения","Место рождения","Адрес места жительства в Российской Федерации",
                "Адрес места жительства  на территории Российской Федерации (текст)","Адрес места жительства за пределами Российской Федерации",
                "Сведения о документе, удостоверяющем личность","Дата смерти | Месяц и год смерти | Год смерти","Время смерти","Место смерти",
                "Сведения об умершем", "Сведения об обстоятельствах смерти",
                "Сведения о смерти ребенка, умершего в возрасте до года", "Сведения о враче", "Сведения о заявителе",
                "Признак умершего лица, личность которого не установлена",
                "Сведения о документе, подтверждающем факт смерти","Наименование органа, выдавшего документ",
                "Фамилия, имя, отчество индивидуального предпринимателя, осуществляющего медицинскую деятельность",
                "Фамилия, имя, отчество физического лица, предоставившего заявление",
                "Сведения о документе, на основании которого внесены  исправления и изменения в запись акта гражданского состояния или запись акта гражданского состояния аннулирована",
                "Дата версии записи", "Номер версии записи",  "Дата записи в БД", "smevMessageId"};
        return header;
    }

    private String getValue(String ceilContent) {
        if (ceilContent != null) {
            return ceilContent;
        } else return "";
    }

    private String formatCSV(String inputString) {
        if (inputString != null) {
            return format(inputString, dataWriteModeCSV);
        } else return null;
    }

    private String formatXLSX(String inputString) {
        if (inputString != null) {
            return format(inputString, dataWriteModeXLSX);
        } else return null;
    }

    private String format(String inputString, DataWriteMode dataWriteMode) {
            if (dataWriteMode.equals(DataWriteMode.READABLE)) {
                try {
                    ObjectMapper obj = new ObjectMapper();
                    Map<String, String> myMap = obj.readValue(inputString, HashMap.class);
                    return myMap.toString();
                } catch (IOException ex) {
                    return inputString;
                }
            }
            return inputString;
    }

    private String performSex(String sex) {
        if (sex != null) {
            switch (sex) {
                case "1": sex = "М";
                    break;
                case "2": sex = "Ж";
                    break;
                default: sex = "";
            }
        } else {
            sex = "";
        }
        return sex;
    }

}