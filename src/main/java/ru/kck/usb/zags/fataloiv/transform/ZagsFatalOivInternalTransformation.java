package ru.kck.usb.zags.fataloiv.transform;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import ru.kck.usb.zags.fataloiv.dao.Death;
import ru.kck.usb.zags.fataloiv.dao.DeathList;
import ru.kck.usb.zags.fataloiv.dao.DeathService;
import ru.usb.commons.exceptions.UsbException;
import ru.usb.commons.utils.UsbXmlUtil;
import ru.usb.scenario.ITransformation;
import ru.usb.smev3.message.SmevMessageData;
import ru.usb.smev3.message.getRequest.GetRequestIncomeBusinessMessage;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

public class ZagsFatalOivInternalTransformation implements ITransformation<GetRequestIncomeBusinessMessage, SmevMessageData> {
    private static final transient Log LOG = LogFactory.getLog(ZagsFatalOivInternalTransformation.class);
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("ru.kck.usb.zags.zagsFatalOiv");
    public static final String ZAGS_FATAL_OIV_NS = "urn://x-artefacts-zags-fataloiv/root/112-41/4.0.0";
    public static final String ZAGS_FATAL_OIV_REQUEST = "Request";

    protected String getInternalXsltFileName() {
        return "xslt/internal-transform.xslt";
    }

    protected final TransformerFactory transformerFactory;
    private DeathService deathService;

    public ZagsFatalOivInternalTransformation(DeathService deathService) {
        this.transformerFactory = TransformerFactory.newInstance();
        this.deathService = deathService;
    }

    @Override
    public SmevMessageData transform(GetRequestIncomeBusinessMessage data) {
        Element body = data.getBody();
        LOG.info("----------------FNS Death----------------");
        String messageId = data.getHeader().getIdentity().getMessageId();
        if (!deathService.isExist(messageId)) {
            String bodyXML = UsbXmlUtil.convertDomTreeToString(body,false,false);
            List<Death> list = parseToObject(bodyXML,messageId);
            if (list != null && !list.isEmpty()) {
                for (Death death : list) {
                    death.setRecordDate(new Date());
                }
                deathService.insert(list);
            }
        } else{
            LOG.info(String.format("FNS Death Package(smevMessageId=%s) REJECTED!", messageId));
        }
        return new SmevMessageData(getResponse(body,deathService.getInsertStatus()).getBytes());
    }

    private String getResponse(Element body, boolean insertStatus) {
        String requestId = UsbXmlUtil.getChildNodeText(body,"//*[local-name()='FATALOIVRequest']/@ИдСвед");
        String attrIdSved = BUNDLE.getString("response.tag.idsved");
        String attrKodObr = BUNDLE.getString("response.tag.kodobr");
        String kodObr = insertStatus ? "01" : "02";
        return String.format("<tns:FATALOIVResponse xmlns:tns=\"%s\" %s=\"%s\" %s=\"%s\"/>", ZAGS_FATAL_OIV_NS, attrIdSved, requestId, attrKodObr, kodObr);
    }

    private List<Death> parseToObject(String bodyXML, String smevMessageId) {
        DeathList list = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DeathList.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            String bodyTransformed = xmlTransform(bodyXML, smevMessageId, getInternalXsltFileName());
            InputStream is = new ByteArrayInputStream(bodyTransformed.getBytes(StandardCharsets.UTF_8));
            list = (DeathList) um.unmarshal(is);
        } catch (Exception e) {
            LOG.error(String.format("FNS Death. Parse ERROR Exception. Details:%s;%s", e.getMessage(), e.getStackTrace()));
        }
        if (list != null) {
            return list.getDeathList();
        }
        return Collections.emptyList();
    }

    public String xmlTransform(String data, String smevMessageId, String xsltFileName) {
        LOG.debug("Transform data: ["+data+"]");
        Source xmlSource = new StreamSource(IOUtils.toInputStream(data,StandardCharsets.UTF_8));
        Source xsltSource = new StreamSource(getClass().getClassLoader().getResourceAsStream(xsltFileName));
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        try {
            long transform = System.currentTimeMillis();
            Transformer trans = transformerFactory.newTransformer(xsltSource);
            trans.setParameter("smevMessageId", smevMessageId);
            trans.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            trans.transform(xmlSource,result);

            LOG.info("Transform in " + (System.currentTimeMillis() - transform) + " ms.");
        } catch (Exception e) {
            throw new UsbException(e);
        }
        return writer.toString().replaceAll("[\\s]{2,}", " ");
    }

}
