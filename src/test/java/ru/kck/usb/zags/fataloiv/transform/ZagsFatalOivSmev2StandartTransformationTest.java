package ru.kck.usb.zags.fataloiv.transform;

import org.apache.commons.io.IOUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import ru.usb.commons.utils.UsbXmlUtil;
import ru.usb.smev3.message.getRequest.GetRequestIncomeBusinessMessage;
import ru.usb.standard.message.outcome.StandardClientMessageData;
import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

public class ZagsFatalOivSmev2StandartTransformationTest {

    ZagsFatalOivSmev2StandartTransformation transformation;

    @Before
    public void setUp() {transformation = new ZagsFatalOivSmev2StandartTransformation();}

    @Test
    public void testTransforms() {
        testTransform("0");
        testTransform("1");
    }


    public void testTransform(String mesNum) {
        GetRequestIncomeBusinessMessage incomeBusinessMessage = EasyMock.createMock(GetRequestIncomeBusinessMessage.class);
        Element bodyElement = UsbXmlUtil.convertStreamToDocument(getClass().getClassLoader()
                .getResourceAsStream("samples/tests/smevRequest" + mesNum + ".xml")).getDocumentElement();
        EasyMock.expect(incomeBusinessMessage.getBody()).andReturn(bodyElement);

        EasyMock.replay(incomeBusinessMessage);
        StandardClientMessageData standardClientMessageData = transformation.transform(incomeBusinessMessage);
        EasyMock.verify(incomeBusinessMessage);

        Assert.assertNotNull(standardClientMessageData);
        try {
            String etalon = IOUtils.toString(getClass().getClassLoader()
                    .getResourceAsStream("samples/tests/standardRequest" + mesNum + ".xml"),"UTF-8");

        String result = UsbXmlUtil.convertDomTreeToString(standardClientMessageData.getBody(),true,false);
            System.out.println("===============================================================================================");
            System.out.println(result);
        assertEquals(result,etalon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}