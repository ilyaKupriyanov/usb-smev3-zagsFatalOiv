package ru.kck.usb.zags.fataloiv.transform;

import org.apache.commons.io.IOUtils;
import ru.usb.commons.utils.UsbXmlUtil;
import ru.usb.scenario.smev3.standard.StandardToSmevTransformation;
import ru.usb.smev3.autogenerated.types.RejectCode;
import ru.usb.smev3.message.SmevMessageData;
import ru.usb.smev3.message.SmevOutcomeRejectParams;
import java.io.IOException;

/**
 * @author Ilya V. Kupriyanov
 * @version 02/02/2020
 */

public class ZagsFatalOivStandart2SmevTransformation extends StandardToSmevTransformation {
    @Override
    protected String getXsltFileName() {
        return "xslt/standart2smev.xslt";
    }

    @Override
    protected SmevMessageData buildOutcomeMessage(String body) {
        if (body.contains("NoData")) {
            return new SmevMessageData(new SmevOutcomeRejectParams(RejectCode.NO_DATA, "NO_DATA"));
        } else {
//            UsbXmlUtil.validateXml(body.getBytes(StandardCharsets.UTF_8),
//                getClass().getClassLoader().getResourceAsStream("xsd/commons/zagsFatalOiv-commons.xsd"),
//                getClass().getClassLoader().getResourceAsStream("xsd/zagsFatalOiv-root.xsd"));
            try {
                UsbXmlUtil.validateXml(IOUtils.toInputStream(body, "UTF-8"),
                    getClass().getClassLoader().getResourceAsStream("xsd/commons/zags-fataloiv-types.xsd"),
                    getClass().getClassLoader().getResourceAsStream("xsd/zags-fataloiv-ru-root.xsd"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new SmevMessageData(body);
        }
    }
}