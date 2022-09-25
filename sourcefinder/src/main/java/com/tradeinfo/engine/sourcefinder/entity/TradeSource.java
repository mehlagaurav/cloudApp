package com.tradeinfo.engine.sourcefinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
@Entity
public class TradeSource {
    @Id
    private String id;
    private String sourceName;
    private float isin;
    private String marketSegment;
    private String ticker;
}
