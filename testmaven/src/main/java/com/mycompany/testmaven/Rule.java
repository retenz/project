/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven;

import com.mycompany.testmaven.expression.AndExpression;
import com.mycompany.testmaven.expression.Expression;
import com.mycompany.testmaven.expression.FactExpression;
import com.mycompany.testmaven.expression.OrExpression;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author Robert.Tenadze
 */
@XmlRootElement(name = "rule")
@XmlType(propOrder = {"expression", "ruleResult"})
public class Rule {

    
    @XmlElements({@XmlElement(name = "fact", type = FactExpression.class),
    @XmlElement(name="and", type = AndExpression.class),
    @XmlElement(name="or",type = OrExpression.class)
    })
    private Expression expression;
    @XmlAttribute(name="ruleResult")
    private String ruleResult;

    private Rule() {}
    public Rule(Expression expression, String ruleResult) {
        this.expression = expression;
        this.ruleResult = ruleResult;
    }

    public void calculate(Set<String> knownFacts) {
        if (expression.calculate(knownFacts)) {
            knownFacts.add(ruleResult);
        }
    }

    public Expression getExpression() {
        return expression;
    }

    public String getRuleResult() {
        return ruleResult;
    }
}
