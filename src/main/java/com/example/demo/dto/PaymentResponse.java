
package com.example.demo.dto;

import java.util.List;

public class PaymentResponse {
    private List<PaymentDetail> pagos;
    private List<AccountDetail> cuentas;

    public List<PaymentDetail> getPagos() {
        return pagos;
    }

    public void setPagos(List<PaymentDetail> pagos) {
        this.pagos = pagos;
    }

    public List<AccountDetail> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<AccountDetail> cuentas) {
        this.cuentas = cuentas;
    }
}

