/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.parser;

import com.mycompany.testmaven.Model;
import com.mycompany.testmaven.Rule;
import com.mycompany.testmaven.dto.ExpressionDTO;
import com.mycompany.testmaven.dto.RuleDTO;
import com.mycompany.testmaven.expression.AndExpression;
import com.mycompany.testmaven.expression.Expression;
import com.mycompany.testmaven.expression.FactExpression;
import com.mycompany.testmaven.expression.OrExpression;
import com.mycompany.testmaven.mapper.ExpressionMapper;
import com.mycompany.testmaven.mapper.FactMapper;
import com.mycompany.testmaven.mapper.RuleMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import static java.util.Objects.isNull;
import java.util.Properties;
import java.util.Set;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Robert.Tenadze
 */
public class DBParser implements IParser {

    SqlSession sqlSession;
    static SqlSessionFactory sqlSessionFactory;

    public DBParser(String dbProperties) throws IOException {
        sqlSession = getConnection(dbProperties).openSession(false);
    }

    @Override
    public Model parse(String fileName) throws IOException, Exception {

        try {
            Collection<RuleDTO> rulesDTO = getRules();
            Collection<Rule> rules = new ArrayList<>();

            for (RuleDTO rule : rulesDTO) {
                rules.add(new Rule(createExpression(rule.expression), rule.Result_Fact));
            }
            Set<String> knownFacts = new HashSet<>(getKnownFact());
            Model model = new Model(rules, knownFacts);
            sqlSession.commit();
            return model;
        } catch (Exception ex) {
            sqlSession.rollback();
            throw new Exception("Ошибка парсинга! Все изменения будут отменены.", ex);
        }
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
    
    private Expression createExpression(ExpressionDTO expression) throws Exception {
        switch (expression.type) {
            case "fact":
                return new FactExpression(expression.value);
            case "and":
                return new AndExpression(getChildren(expression.id));
            case "or":
                return new OrExpression(getChildren(expression.id));
            default:
                throw new Exception("Неверный формат выражения!");
        }
    }

    private Collection<Expression> getChildren(int id) throws Exception {
        Collection<ExpressionDTO> list = getDtoChildren(id);
        ArrayList<Expression> expressions = new ArrayList<>();
        for (ExpressionDTO expressionDTO : list) {
            expressions.add(createExpression(expressionDTO));
        }
        return expressions;
    }

    
    public Collection<RuleDTO> getRules() {
        return sqlSession.getMapper(RuleMapper.class).getRules();
    }

    public Collection<ExpressionDTO> getDtoChildren(int id) {
        return sqlSession.getMapper(ExpressionMapper.class).getChild(id);
    }

    public Collection<String> getKnownFact() {
        return sqlSession.getMapper(FactMapper.class).getKnownFact();
    }

    @Override
    public void close() throws Exception {
        sqlSession.close();
    }

}
