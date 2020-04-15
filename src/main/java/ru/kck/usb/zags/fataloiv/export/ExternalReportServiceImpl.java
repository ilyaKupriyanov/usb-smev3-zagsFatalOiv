package ru.kck.usb.zags.fataloiv.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.kck.usb.zags.fataloiv.dao.Death;
import ru.kck.usb.zags.fataloiv.dao.DeathService;
import ru.kck.usb.zags.fataloiv.utils.DateStat;
import ru.kck.usb.zags.fataloiv.utils.ExportFormat;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Ilya V. Kupriyanov
 * @version 15/02/2020
 */

@Component
@Path("/data/")
public class ExternalReportServiceImpl implements ExternalReportDataService {
    private static final transient Log LOG = LogFactory.getLog(ExternalReportServiceImpl.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DeathService deathService;

    @Autowired
    private MSReportGenerator msReportGenerator;

    @Value("${zagsFatalOivExport.keyword:abcd}")
    private String keyword;

    @GET
    @Path("/export/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getReportData(@QueryParam("begindate") String regInfoDateBeginParam,
                                  @QueryParam("enddate") String regInfoDateEndParam,
                                  @QueryParam("sort") String sort,
                                  @QueryParam("format") String format,
                                  @QueryParam("keyword") String keywordParam) throws IOException {

        Sort.Direction direction = getDirection(sort);
        Date regInfoDateBegin = parseDate(regInfoDateBeginParam);
        Date regInfoDateEnd = parseDate(regInfoDateEndParam);
        ExportFormat exportFormat = getExportFormat(format);
        //check for csv conditions
        boolean allowCSV = exportFormat.equals(ExportFormat.CSV) && (keywordParam != null) && (keywordParam.equals(keyword));
        Response.ResponseBuilder responseBuilder;
        if (exportFormat.equals(ExportFormat.CSV) && !allowCSV) {
            responseBuilder = Response.ok("<html>FORBIDDEN RESOURCE!</html>").header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            return responseBuilder.build();
        }

        List<Death> deathList;
        deathList = deathService.exportWithCriteria((dateRefine(regInfoDateBegin, DateStat.BEGIN)), dateRefine(regInfoDateEnd,DateStat.END), direction);

        LOG.info("------------------ExternalReportServiceImpl read data count = " + deathList.size());

        if (exportFormat.equals(ExportFormat.XLSX)) {
            byte[] exportFile = msReportGenerator.getXlsxReportFile(deathList);
            responseBuilder = Response.ok(exportFile)
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8")
                    .header("Content-Disposition", "attachment; filename=export.xlsx");
        } else {
                String fileContent = msReportGenerator.getCSVReportFile(deathList);
                responseBuilder = Response.ok(fileContent)
                        .header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel; charset=utf-8")
                        .header("Content-Disposition", "attachment; filename=export.csv");
            }
        return responseBuilder.build();
    }

    @Override
    public Response getReportData() {
        return Response.ok().header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8").build();
    }

    private Date parseDate(String dateString) {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private Sort.Direction getDirection(String sort) {
        Sort.Direction direction;
        if (sort != null) {
            if (sort.equalsIgnoreCase("asc")) {
                return direction = Sort.Direction.ASC;
            } else if (sort.equalsIgnoreCase("desc")) {
                return direction = Sort.Direction.DESC;
            }
            return direction = Sort.Direction.ASC;
        } else {
            return direction = Sort.Direction.ASC;
        }
    }

    private static Date dateRefine(Date date, DateStat dateStat) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (dateStat == DateStat.BEGIN) {
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                return calendar.getTime();
            } else if (dateStat == DateStat.END) {
                calendar.set(Calendar.MILLISECOND, 999);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.HOUR, 23);
                return calendar.getTime();
            } else return date;
        }
        return null;
    }

    private ExportFormat getExportFormat(String format) {
        ExportFormat exportFormat;
        if (format != null) {
            if (format.equalsIgnoreCase("xlsx")) {
                exportFormat = ExportFormat.XLSX;
            } else if (format.equalsIgnoreCase("csv")) {
                exportFormat = ExportFormat.CSV;
            } else {
                exportFormat = ExportFormat.XLSX;
            }
        } else {
            exportFormat = ExportFormat.XLSX;
        }
        return exportFormat;
    }

}


