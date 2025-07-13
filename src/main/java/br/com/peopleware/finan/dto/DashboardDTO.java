package br.com.peopleware.finan.dto;

import java.math.BigDecimal;

public class DashboardDTO {
    private BigDecimal totalContasPatrimoniais;
    private BigDecimal totalContasReceber;
    private BigDecimal totalContasPagar;

    public DashboardDTO() {}

    public DashboardDTO(BigDecimal totalContasPatrimoniais, BigDecimal totalContasReceber, BigDecimal totalContasPagar) {
        this.totalContasPatrimoniais = totalContasPatrimoniais;
        this.totalContasReceber = totalContasReceber;
        this.totalContasPagar = totalContasPagar;
    }

    public BigDecimal getTotalContasPatrimoniais() {
        return totalContasPatrimoniais;
    }

    public BigDecimal getTotalContasReceber() {
        return totalContasReceber;
    }

    public BigDecimal getTotalContasPagar() {
        return totalContasPagar;
    }
}
