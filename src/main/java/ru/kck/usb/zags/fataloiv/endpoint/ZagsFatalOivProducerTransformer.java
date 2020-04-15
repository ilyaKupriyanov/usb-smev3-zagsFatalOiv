package ru.kck.usb.zags.fataloiv.endpoint;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kck.usb.zags.fataloiv.transform.ZagsFatalOivStandart2SmevTransformation;
import ru.kck.usb.zags.fataloiv.transform.ZagsFatalOivSmev2StandartTransformation;
import ru.usb.commons.exceptions.UsbException;
import ru.usb.scenario.ITransformation;
import ru.usb.scenario.smev3.standard.MockResponse;
import ru.usb.scenario.smev3.standard.StandardClientMockMessageData;
import ru.usb.scenario.smev3.standard.producer.AProducerMockResponseDataProvider;
import ru.usb.scenario.smev3.standard.producer.AProducerTransformer;
import ru.usb.scenario.smev3.standard.producer.IProducerTransformerRegistry;
import ru.usb.smev3.message.SmevOutcomeStatusParams;
import ru.usb.smev3.message.getRequest.GetRequestIncomeBusinessMessage;
import ru.usb.standard.message.income.StandardClientIncomeMessage;
import ru.usb.standard.message.outcome.StandardClientMessageData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZagsFatalOivProducerTransformer extends AProducerTransformer {
    
    @Value("${zagsFatalOivProducerTransformer.receiverInformation:}")
    private String receiverInformation;

    @Value("${zagsFatalOivProducerTransformer.standartClientIdentifier:56bd4722-53d4-11ea-8d77-2e728ce88125}")
    private String standartClientIdentifier;

    @Value("${zagsFatalOivProducerTransformer.mockMode:false}")
    private boolean mockMode = false;

    @Autowired
    public ZagsFatalOivProducerTransformer(IProducerTransformerRegistry registry) {
        super(registry,
                "urn://x-artefacts-zags-fataloiv/root/112-41/4.0.0.FATALOIVRequest",
                new ZagsFatalOivSmev2StandartTransformation(),
                new ZagsFatalOivStandart2SmevTransformation(),
                new ITransformation<StandardClientIncomeMessage, SmevOutcomeStatusParams>() {
                    @Override
                    public SmevOutcomeStatusParams transform(StandardClientIncomeMessage data) {
                        throw new UsbException("Service does not support SIU state process");
                    }
                },
                new AProducerMockResponseDataProvider() {

                    int idx = 0;

                    @Override
                    public List<MockResponse> getResponses(StandardClientMockMessageData data) {
                        List<MockResponse> responses = new ArrayList<MockResponse>();
                        if (idx++ % 2 == 0) {
                            // Result
                            responses.add(new MockResponse(loadMockResponse(data, "result")));
                        } else {
                            // Reject
                            responses.add(new MockResponse(0L, loadMockResponse(data, "reject")));
                        }
                        return responses;
                    }

                    private byte[] loadMockResponse(StandardClientMockMessageData data, String responseType) {
                        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        String fileName = "mock/standartClientResponse." + responseType + ".xml";
                        String originalMessageId =data.getRequestOutcomeMessage().getHeader().getContentInfo().getContentId();
                        try {
                            IOUtils.copy(getClass().getClassLoader().getResourceAsStream(fileName), byteArrayOutputStream);
                            String content = new String(byteArrayOutputStream.toByteArray(), "UTF8");
                            return content.replaceFirst("<RefContentID>originalMessageId</RefContentID>", "<RefContentID>" + originalMessageId + "</RefContentID>").getBytes("UTF8");
                        } catch (IOException e) {
                            throw new UsbException(e);
                        }
                    }
                }
        );
    }

    @Override
    public String getReceiverInformation() {
        return receiverInformation;
    }

    @Override
    public String getStandartClientIdentifier() {
        return standartClientIdentifier;
    }

    @Override
    public boolean isMockMode() {
        return mockMode;
    }
}
