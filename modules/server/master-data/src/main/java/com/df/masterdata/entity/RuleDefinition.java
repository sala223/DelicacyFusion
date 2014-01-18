package com.df.masterdata.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;

import org.codehaus.jackson.annotate.JsonIgnore;

@Embeddable
public class RuleDefinition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "QUALIFIER", length = 128, nullable = false)
	private String qualifier;

	@ElementCollection
	@CollectionTable(name = "RULE_PARAMETER", joinColumns = @JoinColumn(name = "RULE_ID"))
	@MapKey(name = "name")
	private Map<String, RuleParameter> parameters = new HashMap<String, RuleParameter>();

	RuleDefinition() {
	}

	public RuleDefinition(String qualifier) {
		this.qualifier = qualifier;
	}

	void setParameters(Map<String, RuleParameter> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(RuleParameter parameter) {
		parameters.put(parameter.getName(), parameter);
	}

	public RuleParameter getParameter(String parameter) {
		return parameters.get(parameter);
	}

	@JsonIgnore
	public String[] getParameterNames() {
		return parameters.keySet().toArray(new String[0]);
	}

	public Map<String, RuleParameter> getParameters() {
		return parameters;
	}

	public String getQualifier() {
		return this.qualifier;
	}

}
