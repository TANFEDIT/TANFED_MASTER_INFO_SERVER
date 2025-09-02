package com.tanfed.basicInfo.response;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GstRateData {

	@Column
	private Double cgstRate;

	@Column
	private Double sgstRate;

	@Column
	private Double igstRate;

	@Column
	private Double rcmRate;
}
