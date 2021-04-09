package com.group4.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ConversionDTO {
	@JsonProperty("VND_USD")
	private BigDecimal VND_USD;

	public ConversionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConversionDTO(BigDecimal vND_USD) {
		super();
		VND_USD = vND_USD;
	}

	public BigDecimal getVND_USD() {
		return VND_USD;
	}

	public void setVND_USD(BigDecimal vND_USD) {
		VND_USD = vND_USD;
	}
	
}
