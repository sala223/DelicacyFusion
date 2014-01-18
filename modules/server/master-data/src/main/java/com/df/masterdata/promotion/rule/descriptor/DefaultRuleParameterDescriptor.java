package com.df.masterdata.promotion.rule.descriptor;

public class DefaultRuleParameterDescriptor implements RuleParameterDescriptor {
	private String name;

	private String defaultValue;

	private boolean isMandatory;

	private RuleParameterType type;

	private String description;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public RuleParameterType getType() {
		return type;
	}

	public void setType(RuleParameterType type) {
		this.type = type;
	}

}
