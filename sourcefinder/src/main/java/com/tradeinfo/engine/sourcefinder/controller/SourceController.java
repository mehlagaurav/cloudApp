package com.tradeinfo.engine.sourcefinder.controller;

import com.tradeinfo.engine.sourcefinder.dao.SourceRepository;
import com.tradeinfo.engine.sourcefinder.entity.TradeSource;
import com.tradeinfo.engine.sourcefinder.mapper.SchemaMapper;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class SourceController {
    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SchemaMapper schemaMapper;
    @Value("classpath:tradesource.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = schemaMapper.buildWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    @PostMapping("/addTradeSources")
    public ResponseEntity<String> addSources(@RequestBody List<TradeSource> sources) {
        sourceRepository.saveAll(sources);
        return new ResponseEntity<>(sources.size() + " new Sources added", HttpStatus.OK);
    }

    @GetMapping("/getAllSources")
    public ResponseEntity<List<TradeSource>> getSources() {
        return new ResponseEntity<>((List<TradeSource>) sourceRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("getAllSourcesViaGraphql")
    public ResponseEntity<Object> getAllSources(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PostMapping("getSourceByTickerViaGraphql")
    public ResponseEntity<Object> getAllSourceByTicker(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

}
