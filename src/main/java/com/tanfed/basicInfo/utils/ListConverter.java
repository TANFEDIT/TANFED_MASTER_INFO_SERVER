package com.tanfed.basicInfo.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<String>, String> {

	private static final String SEPARATOR = ",";
	
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return null;
		}
		return attribute.stream().map(i -> i).collect(Collectors.joining(SEPARATOR));
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty()) {
			return null;
		}
		return Arrays.stream(dbData.split(SEPARATOR)).map(i -> i).collect(Collectors.toList());
	}

}
