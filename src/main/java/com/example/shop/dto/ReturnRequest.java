package com.example.shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnRequest {

    /**
     * Maps the JSON field "isDamaged" to this boolean.
     */
    @JsonProperty("isDamaged")
    private boolean damaged;

    private String damageDescription;
    private Double repairPrice;
    private Double finalFee;

    public ReturnRequest() { }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public Double getRepairPrice() {
        return repairPrice;
    }

    public void setRepairPrice(Double repairPrice) {
        this.repairPrice = repairPrice;
    }
    public Double getFinalFee() {
        return finalFee;
    }
    public void setFinalFee(Double finalFee) {
        this.finalFee = finalFee;
    }
}
