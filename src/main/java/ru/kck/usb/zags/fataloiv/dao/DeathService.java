package ru.kck.usb.zags.fataloiv.dao;

import org.springframework.data.domain.Sort;
import java.util.Date;
import java.util.List;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

public interface DeathService {
    List<Death> insert(List<Death> deathList);
    boolean isExist(String messageId);
    List<Death> export();
    boolean getInsertStatus();
    List<Death> exportWithCriteria(Date regInfoDateBegin, Date regInfoDateEnd, Sort.Direction direction);

}
