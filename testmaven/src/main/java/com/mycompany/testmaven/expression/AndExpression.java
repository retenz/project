/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.expression;

import java.util.Collection;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Robert.Tenadze
 */
@XmlRootElement(name = "and")
@XmlType(propOrder = {"expressions"})
public class AndExpression implements Expression {

    @XmlElements({@XmlElement(name = "fact", type = FactExpression.class),
    @XmlElement(name="and", type = AndExpression.class),
    @XmlElement(name="or",type = OrExpression.class)
    })
    private Collection<Expression> expressions;

    private AndExpression() {}
    public AndExpression(Collection<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean calculate(Set<String> knownFacts) {
        for (Expression ex : expressions) {
            if (!ex.calculate(knownFacts)) {
                return false;
            }
        }
        return true;
    }

    public Collection<Expression> getExpressions() {
        return expressions;
    }
    
}
