package com.github.monchenkoid.quotationapp.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Currency", strict = false)
public class CurrencyModel {

    @Element(name = "NumCode")
    public int numCode;

    @Element(name = "CharCode")
    public String charCode;

    @Element(name = "Scale")
    public int scale;

    @Element(name = "Name")
    public String name;

    @Element(name = "Rate")
    public double rate;

    public CurrencyModel() {
    }

    public String getName() {
        return name;
    }
}
