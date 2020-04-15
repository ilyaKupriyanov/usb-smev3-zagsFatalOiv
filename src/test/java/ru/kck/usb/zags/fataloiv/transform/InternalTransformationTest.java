package ru.kck.usb.zags.fataloiv.transform;

import org.apache.commons.io.IOUtils;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import ru.kck.usb.zags.fataloiv.dao.DeathService;
import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;

public class InternalTransformationTest {

    ZagsFatalOivInternalTransformation transformation;
    DeathService service = EasyMock.createMock(DeathService.class);

    @Before
    public void setUp() {
        transformation = new ZagsFatalOivInternalTransformation(service);
    }

    @Test
    public void testXmlTransform1() throws IOException {
        mainTransform("smevRequest_1.xml","deathList_1.xml");
    }

    @Test
    public void testXmlTransform2() throws IOException {
        mainTransform("smevRequest_2.xml","deathList_2.xml");
    }

    @Test
    public void testXmlTransform3() throws IOException {
        mainTransform("smevRequest_3.xml","deathList_3.xml");
    }

    @Test
    public void testXmlTransform4() throws IOException {
        mainTransform("smevRequest_4.xml","deathList_4.xml");
    }

    @Test
    public void testXmlTransform_real() throws IOException {
      mainTransform("smevRequest_real.xml","deathList_real.xml");
    }

    private void mainTransform(String inputDataName, String expectedName) throws IOException {
        String inputData = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("samples/tests/internalTest/"+inputDataName), Charset.forName("UTF-8"));
        String expected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("samples/tests/internalTest/"+expectedName), Charset.forName("UTF-8"));
        String result = transformation.xmlTransform(inputData,"smevMessageId","xslt/internal-transform.xslt").trim();
        System.out.println("Result of transformation " + inputDataName);
        System.out.println("=============================================================================");
        System.out.println(result);
        assertEquals(expected,result);
    }

}
