package com.tradeinfo.engine.sourcefinder.dao;

import com.tradeinfo.engine.sourcefinder.entity.TradeSource;
import org.springframework.data.repository.CrudRepository;

import javax.sql.DataSource;
import java.util.List;

public interface SourceRepository extends CrudRepository<TradeSource,Integer> {
    TradeSource findByTicker(String ticker);
}
