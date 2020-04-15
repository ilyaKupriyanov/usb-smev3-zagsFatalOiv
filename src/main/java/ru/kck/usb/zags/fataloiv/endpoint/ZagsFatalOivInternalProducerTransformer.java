package ru.kck.usb.zags.fataloiv.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.kck.usb.zags.fataloiv.dao.DeathService;
import ru.kck.usb.zags.fataloiv.transform.ZagsFatalOivInternalTransformation;
import ru.usb.scenario.smev3.internal.AInternalTransformer;
import ru.usb.scenario.smev3.internal.IInternalTransformerRegistry;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

public class ZagsFatalOivInternalProducerTransformer extends AInternalTransformer {
    private static final transient Log LOG = LogFactory.getLog(ZagsFatalOivInternalProducerTransformer.class);

    public ZagsFatalOivInternalProducerTransformer(IInternalTransformerRegistry registry, DeathService deathService) {
        super(registry,
                ZagsFatalOivInternalTransformation.ZAGS_FATAL_OIV_NS+"."+ZagsFatalOivInternalTransformation.ZAGS_FATAL_OIV_REQUEST,
                new ZagsFatalOivInternalTransformation(deathService));
        LOG.info("-------ZagsFatalOivInternalProducerTransformer ++++++++++++ Register InternalTransformer------");
    }

}
