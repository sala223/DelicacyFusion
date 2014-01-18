package com.df.masterdata.promotion.rule.descriptor;

public enum RuleParameterType {

	STRING, INTEGER, DATE, NUMERIC;

	public Object toJavaValue(String textValue) {
		return null;
	}

	public String toTextValue(Object javaValue) {
		return null;
	}

}
