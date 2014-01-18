package com.df.masterdata.promotion.rule.descriptor;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.df.masterdata.promotion.rule.Rule;

public class DefaultRuleDescriptor implements JavaRuleDescriptor {

	private Class<? extends Rule> ruleClass;

	private String ruleQualifier;

	@JsonDeserialize(contentAs = DefaultRuleParameterDescriptor.class)
	private Map<String, RuleParameterDescriptor> parameters;

	private RuleType type;

	private String description;

	DefaultRuleDescriptor() {
	}

	public DefaultRuleDescriptor(String ruleQualifier) {
		this.ruleQualifier = ruleQualifier;
		this.parameters = new HashMap<String, RuleParameterDescriptor>();
	}

	public String getRuleQualifier() {
		return ruleQualifier;
	}

	public void setParameters(Map<String, RuleParameterDescriptor> parameters) {
		this.parameters = parameters;
	}

	public void addRuleParameter(RuleParameterDescriptor parameter) {
		this.parameters.put(parameter.getName(), parameter);
	}

	public RuleType getType() {
		return type;
	}

	public void setType(RuleType type) {
		this.type = type;
	}

	public Map<String, RuleParameterDescriptor> getParameters() {
		return parameters;
	}

	public String[] getParameterNames() {
		return parameters.keySet().toArray(new String[0]);
	}

	@Override
	public Class<? extends Rule> getRuleClass() {
		return ruleClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
