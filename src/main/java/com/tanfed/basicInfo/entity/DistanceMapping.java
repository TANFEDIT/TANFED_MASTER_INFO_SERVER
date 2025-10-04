package com.tanfed.basicInfo.entity;

import java.util.ArrayList;
import java.util.List;

import com.tanfed.basicInfo.model.DistanceMapTableData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DistanceMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String officeName;
	
	private String toRegion;
	
	private String godownName;
	
	private String district;
	
	private String type;
	
	private String category;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dist_mapping")
	private List<DistanceMapTableData> tableData = new ArrayList<DistanceMapTableData>();
	
}
