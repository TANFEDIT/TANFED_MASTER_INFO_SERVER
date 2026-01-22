package com.tanfed.basicInfo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceMappingAbstractTable {

	private String godownName;
	private Integer zero_seven;
	private List<String> zero_seven_list;
	private Integer eight_twenty;
	private List<String> eight_twenty_list;
	private Integer twentyone_fifty;
	private List<String> twentyone_fifty_list;
	private Integer fiftyone_seventyfive;
	private List<String> fiftyone_seventyfive_list;
	private Integer seventysix_hundred;
	private List<String> seventysix_hundred_list;
	private Integer hundredone_onetwentyfive;
	private List<String> hundredone_onetwentyfive_list;
	private Integer onetwosix_onefifty;
	private List<String> onetwosix_onefifty_list;
	private Integer onefiftyone_oneseventyfive;
	private List<String> onefiftyone_oneseventyfive_list;
	private Integer oneseventysix_twohundred;
	private List<String> oneseventysix_twohundred_list;
	private Integer abovetwohundredone;
	private List<String> abovetwohundredone_list;
}
