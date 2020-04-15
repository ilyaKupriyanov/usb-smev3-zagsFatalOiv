package ru.kck.usb.zags.fataloiv.endpoint;

import org.junit.Test;
import ru.usb.scenario.smev3.standard.producer.IProducerTransformer;
import ru.usb.scenario.smev3.standard.producer.IProducerTransformerRegistry;
import java.util.List;

/**
 * ZagsFatalOivProducerTransformerTest Class
 *
 * @author Ivan V. Kulynych
 * @version 30/10/2017
 */

public class ZagsFatalOivProducerTransformerTest {
    ZagsFatalOivProducerTransformer transformer;

    @Test
    public void setUp() {
        this.transformer = new ZagsFatalOivProducerTransformer(new IProducerTransformerRegistry() {
            @Override
            public void register(IProducerTransformer transformer){}

            @Override
            public void unregister(IProducerTransformer transformer){}

            @Override
            public IProducerTransformer get(String transformer){return null;}

            @Override
            public List<IProducerTransformer> registeredTransformers() {
                return null;
            }
        });
    }
}