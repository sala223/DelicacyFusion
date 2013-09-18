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

@Embeddable
public class RuleDescriptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "RULE_CLASS", length = 128, nullable = false)
    private String ruleClass;

    @ElementCollection
    @CollectionTable(name = "RULE_PARAMETER", joinColumns = @JoinColumn(name = "RULE_ID"))
    @MapKey(name = "name")
    private Map<String, RuleParameter> parameters = new HashMap<String, RuleParameter>();

    public void addParameter(RuleParameter parameter) {
	parameters.put(parameter.getName(), parameter);
    }

    public RuleParameter getParameter(String parameter) {
	return parameters.get(parameter);
    }

    public String[] getParameterNames() {
	return parameters.keySet().toArray(new String[0]);
    }

    public RuleParameter[] getParameters() {
	return parameters.values().toArray(new RuleParameter[0]);
    }
}
