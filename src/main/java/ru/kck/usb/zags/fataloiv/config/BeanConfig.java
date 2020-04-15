package ru.kck.usb.zags.fataloiv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kck.usb.zags.fataloiv.dao.DeathService;
import ru.kck.usb.zags.fataloiv.endpoint.ZagsFatalOivInternalProducerTransformer;
import ru.kck.usb.zags.fataloiv.endpoint.ZagsFatalOivProducerTransformer;
import ru.usb.scenario.smev3.internal.IInternalTransformerRegistry;
import ru.usb.scenario.smev3.standard.producer.IProducerTransformerRegistry;


/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

@Configuration
public class BeanConfig {

    private final String TRANSFORMER_TYPE_INTERNAL="internal";

    @Value("${zagsFatalOivProducerTransformer.mode:producer}")
    private String transformerType;

    @Bean
    public Object getEndpoint(IProducerTransformerRegistry producerTransformerRegistry,
                              IInternalTransformerRegistry internalTransformerRegistry,
                              DeathService deathService) {
        if (TRANSFORMER_TYPE_INTERNAL.equals(transformerType)) {
            return new ZagsFatalOivInternalProducerTransformer(internalTransformerRegistry,deathService);
        }
        return new ZagsFatalOivProducerTransformer(producerTransformerRegistry);
    }

}
