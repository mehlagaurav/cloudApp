package com.tradeinfo.engine.sourcefinder.mapper;

import com.tradeinfo.engine.sourcefinder.dao.SourceRepository;
import com.tradeinfo.engine.sourcefinder.entity.TradeSource;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchemaMapper {
    @Autowired
    private SourceRepository sourceRepository;

    public RuntimeWiring buildWiring() {
        DataFetcher<List<TradeSource>> fetchAllSources = data -> {
            return (List<TradeSource>) sourceRepository.findAll();
        };
        DataFetcher<TradeSource> fetchByTicker = data -> {
            return sourceRepository.findByTicker(data.getArgument("ticker"));
        };
        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting -> typeWriting.dataFetcher("getAllSources", fetchAllSources)
                                .dataFetcher("findSourceByTicker", fetchByTicker))
                .build();
    }

}
