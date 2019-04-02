/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.parser;

import com.mycompany.testmaven.Model;
import com.mycompany.testmaven.Rule;
import com.mycompany.testmaven.exception.InputDataException;
import com.mycompany.testmaven.expression.AndExpression;
import com.mycompany.testmaven.expression.Expression;
import com.mycompany.testmaven.expression.FactExpression;
import com.mycompany.testmaven.expression.OrExpression;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Robert.Tenadze
 */
public class TxtParser implements IParser{

    @Override
    public void close() throws Exception {}

    private enum FactParsingContext {
        EXPRESSION_FACT,
        RESULT_FACT,
        KNOWN_FACT
    }

    private enum ExpressionState {
        WAIT_FACT,
        WAIT_OP_OR_DASH,
        OR,
        AND
    }

    private enum FactState {
        WAIT_FACT,
        FACT_WITHOUT_LETTER,
        FACT,
        AFTER_FACT
    }

    private enum LineState {
        RULE,
        FACTS,
        FINISH
    }

    int charIndex;
    int bracketsCount = 0;
    String line;
    int lineNumber;

    // читает, парсит и проверяет весь файл на верность заполнения
    @Override
    public Model parse(String fileName) throws Exception {
        ArrayList<Rule> rules = new ArrayList<>();
        HashSet<String> knownFacts = new HashSet<>();
        LineState state = LineState.RULE;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {

            for (lineNumber = 1; (line = reader.readLine()) != null; lineNumber++) {
                switch (state) {
                    case RULE:
                        if (line.equals("----------------------------------------------------------------")) {
                            state = LineState.FACTS;
                            break;
                        }

                        Rule rule = parseRule();
                        rules.add(rule);
                        break;
                    case FACTS:
                        for (charIndex = 0; charIndex < line.length(); charIndex++) {
                            knownFacts.add(parseFact(FactParsingContext.KNOWN_FACT));
                        }
                        state = LineState.FINISH;
                        break;
                    case FINISH:
                        throw new InputDataException("Были введены лишние данные после строки с известными фактами!\n"
                                + "Строка с известными фактами должна стоять сразу после строки-разделитель и\n"
                                + "быть последней строкой в файле.\n");
                }
            }

            if (state != LineState.FINISH) {
                throw new InputDataException("Ошибка в формате входных данных.\n"
                        + "Файл состоит из логических выражений.\n"
                        + "Далее идёт строка-разделитель, которая состоит из 64 '-'.\n"
                        + "Последняя строка - строка с известными фактами.\n");
            }
            return new Model(rules, knownFacts);
        } catch (FileNotFoundException ex) {
            throw new InputDataException("Файла с таким именем не найдено!\n"
                    + "Проверьте верность введённого пути.", ex);
        }
    }

    private Rule parseRule() throws Exception {

        charIndex = 0;
        String resultFact;
        Expression expression = parseExpression();
        if (!line.substring(++charIndex, charIndex + 2).equals("->")) {
            throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");
        }
        charIndex += 2;
        resultFact = parseFact(FactParsingContext.RESULT_FACT);
        return new Rule(expression, resultFact);
    }

    private Expression parseExpression() throws Exception {

        ExpressionState state = ExpressionState.WAIT_FACT;
        ArrayList<Expression> andList = new ArrayList<>();
        ArrayList<Expression> orList = new ArrayList<>();
        Expression thisExpression = null;

        for (; charIndex < line.length(); charIndex++) {
            char c = line.charAt(charIndex);
            switch (state) {
                case WAIT_FACT:
                    if (c == '(') {
                        bracketsCount++;
                        //пропускаем элемент "(", добавляя единцу к счётчику скобок
                        charIndex++;
                        thisExpression = parseExpression();
                        state = ExpressionState.WAIT_OP_OR_DASH;
                        break;
                    }
                    thisExpression = new FactExpression(parseFact(FactParsingContext.EXPRESSION_FACT));
                    state = ExpressionState.WAIT_OP_OR_DASH;
                    break;
                case AND:
                    if (c == '&') {
                        andList.add(thisExpression);
                        state = ExpressionState.WAIT_FACT;
                        break;
                    }
                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");

                case OR:
                    if (c == '|') {
                        fillThisExpression(andList, orList, thisExpression);
                        andList = new ArrayList<>();
                        state = ExpressionState.WAIT_FACT;
                        break;
                    }
                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");

                case WAIT_OP_OR_DASH:
                    if (Character.isWhitespace(c)) {
                        break;
                    }
                    if (c == '&') {
                        state = ExpressionState.AND;
                        break;
                    }
                    if (c == '|') {
                        state = ExpressionState.OR;
                        break;
                    }
                    if (c == ')') {
                        bracketsCount--;
                        return createExpression(andList, orList, thisExpression);
                    }
                    if (c == '-') {
                        //данный элемент обрабатывается повторно в методе parseRule()
                        charIndex--;
                        return createExpression(andList, orList, thisExpression);
                    }
                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");
            }
        }
        return null;
    }

    private void fillThisExpression(ArrayList<Expression> andList, ArrayList<Expression> orList, Expression thisExpression) {
        if (!andList.isEmpty()) {
            andList.add(thisExpression);
            orList.add(new AndExpression(andList));
        } else {
            orList.add(thisExpression);
        }
    }

    private Expression createExpression(ArrayList<Expression> andList, ArrayList<Expression> orList, Expression thisExpression) {
        if (!orList.isEmpty()) {
            fillThisExpression(andList, orList, thisExpression);
            return new OrExpression(orList);
        }
        if (!andList.isEmpty()) {
            andList.add(thisExpression);
            return new AndExpression(andList);
        }
        return thisExpression;
    }

    private String parseFact(FactParsingContext context) throws Exception {
        FactState factState = FactState.WAIT_FACT;
        StringBuilder stringBuilder = new StringBuilder();
        for (; charIndex < line.length(); charIndex++) {
            char c = line.charAt(charIndex);
            switch (factState) {
                case WAIT_FACT:
                    if (Character.isWhitespace(c)) {
                        break;
                    }

                    if (c == '_') {
                        stringBuilder.append(c);
                        factState = FactState.FACT_WITHOUT_LETTER;
                        break;
                    }
                    if (Character.isLetter(c)) {
                        stringBuilder.append(c);
                        factState = FactState.FACT;
                        break;
                    }

                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");//ощибка
                case FACT_WITHOUT_LETTER:
                    if (c == '_') {
                        stringBuilder.append(c);
                        break;
                    }
                    if (Character.isLetter(c)) {
                        stringBuilder.append(c);
                        factState = FactState.FACT;
                        break;
                    }
                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");//ошибка
                case FACT:
                    if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                        stringBuilder.append(c);
                        break;
                    }
                    if (Character.isWhitespace(c)) {
                        factState = FactState.AFTER_FACT;
                    }
                // and fall on
                case AFTER_FACT:
                    if (Character.isWhitespace(c)) {
                        break;
                    }
                    if (context == FactParsingContext.KNOWN_FACT && c == ',') {
                        return stringBuilder.toString();
                    }
                    if (context == FactParsingContext.EXPRESSION_FACT && (c == '&' || c == '|' || c == '-' || c == ')')) {
                        //этот элемент обрабатывается повторно в parseExpression()
                        charIndex--;
                        return stringBuilder.toString();
                    }
                    throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");//ошибка
            }
        }

        if (factState == FactState.WAIT_FACT || factState == FactState.FACT_WITHOUT_LETTER) {
            throw new InputDataException("Строка №" + lineNumber + " не соответствует формату строки с выражением!");//ошибка
        }
        return stringBuilder.toString();
    }
}
