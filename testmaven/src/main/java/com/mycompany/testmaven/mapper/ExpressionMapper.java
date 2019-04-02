/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.mapper;

import com.mycompany.testmaven.dto.ExpressionDTO;
import com.mycompany.testmaven.expression.Expression;
import java.util.Collection;

/**
 *
 * @author Robert.Tenadze
 */
public interface ExpressionMapper {

    public Expression getExpression(int id);
    public Collection<ExpressionDTO> getChild(int id);
    public void insertExpression(ExpressionDTO dTO);
    public void insertChildExpression(ExpressionDTO dTO);
    public void deleteALL();
}
