package com.example.demo.Models;


import java.math.BigInteger;
import java.util.List;

public class Status {

    protected String value;
    protected int code;

    protected List plans;

    public List getList() {
        return plans;
    }

    public void setList(List plans) {
        this.plans = plans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String id;


    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the code property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setCode(int value) {
        this.code = value;
    }


}
