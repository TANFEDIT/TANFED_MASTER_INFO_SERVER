package com.tanfed.basicInfo.model;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsHead {

	private Set<String> mainHeadList;
	private List<String> subHeadList;
}
