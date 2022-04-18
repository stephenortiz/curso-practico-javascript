
package com.pollosbucanero.wsclient.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class PreciosBase {

    private String itm;
    private Date eftj;
    private BigDecimal uprc;
    private String uom;

    public String getItm() {
        return itm;
    }

    public void setItm(String itm) {
        this.itm = itm;
    } 

    public Date getEftj() {
        return eftj;
    }

    public void setEftj(Date eftj) {
        this.eftj = eftj;
    }

    public BigDecimal getUprc() {
        return uprc;
    }

    public void setUprc(BigDecimal uprc) {
        this.uprc = uprc;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
    
}
