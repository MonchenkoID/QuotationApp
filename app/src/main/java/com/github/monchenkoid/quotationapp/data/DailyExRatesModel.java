package com.github.monchenkoid.quotationapp.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "DailyExRates", strict = false)
public class DailyExRatesModel {

    @ElementList(name="Currency", inline=true)
    public List<CurrencyModel> currencyModels;

    public DailyExRatesModel(){}

    public List<CurrencyModel> getArticles() {
        return currencyModels;
    }
}
