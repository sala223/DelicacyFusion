package com.df.masterdata.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.SequenceGenerator;

@Entity
public class Rule extends StoreAwareMasterData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_RULE_ID_SEQ")
    @SequenceGenerator(name = "PRO_RULE_ID_SEQ", sequenceName = "PRO_RULE_ID_SEQ")
    @Column(name = "ID")
    private long id;

    @Column(name="RULE_CLASS", length=128, nullable=false)
    private String ruleClass;
    
    @ElementCollection
    @CollectionTable(name = "RULE_PARAMETER", joinColumns = @JoinColumn(name = "RULE_ID"))
    @MapKey(name = "name")
    private Map<String, RuleParameter> parameters = new HashMap<String, RuleParameter>();

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void addParameter(RuleParameter parameter) {
	parameters.put(parameter.getName(), parameter);
    }

    public RuleParameter getParameter(String parameter) {
	return parameters.get(parameter);
    }

    public String[] getParameterNames() {
	return parameters.keySet().toArray(new String[0]);
    }
}
