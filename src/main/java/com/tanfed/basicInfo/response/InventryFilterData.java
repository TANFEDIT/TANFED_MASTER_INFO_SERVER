package com.tanfed.basicInfo.response;

import java.util.List;

import com.tanfed.basicInfo.entity.ProductConfig;
import com.tanfed.basicInfo.entity.ProductMaster;
import com.tanfed.basicInfo.entity.Terms_Price_Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InventryFilterData {

	private List<ProductConfig> productConfig;
	private ProductConfig productConfigData;
	
	private List<Terms_Price_Config> termsPriceConfig;
	private Terms_Price_Config termsPriceConfigData;
	
	private List<ProductMaster> productMaster;
	private ProductMaster productMastersData;
}
