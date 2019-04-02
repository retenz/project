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
@XmlRootElement(name = "or")
@XmlType(propOrder = {"expressions"})
public class OrExpression implements Expression {

    @XmlElements({@XmlElement(name = "fact", type = FactExpression.class),
    @XmlElement(name="and", type = AndExpression.class),
    @XmlElement(name="or",type = OrExpression.class)
    })
    Collection<Expression> expressions;

    private OrExpression() {}
    public OrExpression(Collection<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean calculate(Set<String> knownFacts) {
        for (Expression ex : expressions) {
            if (ex.calculate(knownFacts)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Collection<Expression> getExpressions() {
        return expressions;
    }
}
