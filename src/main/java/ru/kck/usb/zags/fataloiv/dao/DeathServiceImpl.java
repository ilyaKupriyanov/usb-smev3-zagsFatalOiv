package ru.kck.usb.zags.fataloiv.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.sdc.postgres.ABasePostgresService;
import ru.sdc.postgres.Criteria;
import ru.sdc.service.ICriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

@Service("deathService")
public class DeathServiceImpl extends ABasePostgresService<Death> implements DeathService {

    private static final Log LOG = LogFactory.getLog(DeathService.class);
    private static final String TABLE_NAME = "DEATH";
    public boolean insertStatus;

    @Autowired
    public DeathServiceImpl(@Qualifier("deathOperations") NamedParameterJdbcOperations jdbcOperations,
                            @Qualifier("deathTemplate") JdbcTemplate jdbcTemplate,
                            @Qualifier("deathSchema") String schema) {
        super(Death.class,TABLE_NAME,schema,jdbcOperations,jdbcTemplate);
    }

    @Override
    public List<Death> insert(List<Death> deathList) {

        ArrayList<SqlParameterSource>  listSources = new ArrayList<>();
        String packageId = deathList.size() > 0 ? deathList.get(0).getRegInfoId() : "<empty>";
        for (Death death : deathList) {
            listSources.add(getBeanPropertySource(death));
        }
        try {
            SimpleJdbcInsert insert = getInsert();
            if (!insert.isCompiled()){
                insert.setGeneratedKeyName("Id");
            }
            insert.executeBatch(listSources.toArray(new SqlParameterSource[0]));
            insertStatus = true;
            LOG.info(String.format("FNS Death Package(id=%s; size=%s) insert SUCCESSFULL!", packageId,deathList.size()));
        } catch (Exception ex) {
            insertStatus = false;
            LOG.error(String.format("FNS Death Package(id=%s; size=%s) insert ERROR! Error message: =%s", packageId,deathList.size(),ex.getMessage()));
            ex.printStackTrace();
            //throw ex;
        }
        return deathList;

    }

    @Override
    public boolean isExist(String messageId) {
        List<ICriteria> criteria = new ArrayList<>();
        criteria.add(Criteria.eq("smevMessageId",messageId));
        Long count = this.count(criteria);
        return count > 0;
    }

    @Override
    public List<Death> export() {
        return super.getAll();
    }

    @Override
    public List<Death> exportWithCriteria(Date regInfoDateBegin, Date regInfoDateEnd, Sort.Direction direction) {
        List<ICriteria> criteria = new ArrayList<>();
        if (regInfoDateBegin != null) {
            criteria.add(Criteria.greatThanEquals("regInfoDate","regInfoDateBegin", regInfoDateBegin));
        }
        if (regInfoDateEnd != null) {
            criteria.add(Criteria.lessThanEquals("regInfoDate","regInfoDateEnd", regInfoDateEnd));
        }
        Sort sort = new Sort(direction,"regInfoDate");
        return this.find(criteria,sort);
    }

    @Override
    public boolean getInsertStatus() {
        return insertStatus;
    }
}
