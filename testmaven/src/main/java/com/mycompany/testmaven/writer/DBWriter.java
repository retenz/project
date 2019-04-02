/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.writer;

import com.mycompany.testmaven.Model;
import com.mycompany.testmaven.Rule;
import com.mycompany.testmaven.dto.ExpressionDTO;
import com.mycompany.testmaven.dto.RuleDTO;
import com.mycompany.testmaven.expression.AndExpression;
import com.mycompany.testmaven.expression.Expression;
import com.mycompany.testmaven.expression.FactExpression;
import com.mycompany.testmaven.mapper.ExpressionMapper;
import com.mycompany.testmaven.mapper.FactMapper;
import com.mycompany.testmaven.mapper.RuleMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import static java.util.Objects.isNull;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Robert.Tenadze
 */
public class DBWriter implements IWriter {

    SqlSession sqlSession;
    static SqlSessionFactory sqlSessionFactory;

    @Override
    public void write(Model model, String fileName) throws Exception {
        deleteALL();
        for (String fact : model.getKnownFacts()) {
            sqlSession.getMapper(FactMapper.class).insertKnownFact(fact);
        }
        for (Rule rule : model.getRules()) {
            RuleDTO ruleDTO = new RuleDTO();
            ruleDTO.Expression_ID = insertExpression(rule.getExpression(), null);
            ruleDTO.Result_Fact = rule.getRuleResult();
            sqlSession.getMapper(RuleMapper.class).insertRule(ruleDTO);
        }
        sqlSession.commit();
    }

    public int insertExpression(Expression ex, Integer parentID) {
        ExpressionDTO exDTO = new ExpressionDTO();
        ArrayList<Expression> expressions = (ArrayList<Expression>) ex.getExpressions();
        exDTO.parentID = parentID;
        if (expressions.size() == 1) {
            exDTO.setValue(((FactExpression) expressions.get(0)).getFact());
            exDTO.setType("fact");
            choseInsert(exDTO);
            return exDTO.id;
        }

        if (ex instanceof AndExpression) {
            exDTO.setType("and");
        } else {
            exDTO.setType("or");
        }
        choseInsert(exDTO);
        for (Expression expression : ex.getExpressions()) {
            insertExpression(expression, exDTO.id);
        }
        return exDTO.id;
    }

    public void choseInsert(ExpressionDTO exDTO) {
        if (exDTO.parentID != null) {
            sqlSession.getMapper(ExpressionMapper.class).insertChildExpression(exDTO);
        } else {
            sqlSession.getMapper(ExpressionMapper.class).insertExpression(exDTO);
        }
    }

    public void deleteALL() {
        sqlSession.getMapper(RuleMapper.class).deleteALL();
        sqlSession.getMapper(ExpressionMapper.class).deleteALL();
        sqlSession.getMapper(FactMapper.class).deleteALL();
    }

    public DBWriter(String dbProperties) throws IOException {
        sqlSession = getConnection(dbProperties).openSession(false);
    }

    public SqlSessionFactory getConnection(String DBProperties) throws FileNotFoundException, IOException {

        if (isNull(sqlSessionFactory)) {
            Reader reader;
            reader = Resources.getResourceAsReader("myBatis.xml");
            Properties DBproperties = new Properties();
            DBproperties.load(new FileInputStream(DBProperties));
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, DBproperties);
        }
        return sqlSessionFactory;
    }

    @Override
    public void close() throws Exception {
        sqlSession.close();
    }

}
