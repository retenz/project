/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import com.mycompany.testmaven.Deduce;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert.Tenadze
 */
public class DeduceTest {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    PrintStream out = System.out;
    PrintStream err = System.err;

    public DeduceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @After
    public void tearDown() {
        System.setOut(out);
        System.setErr(err);
    }

    /**
     * Test of main method, of class Test1.
     */
    @Test
    public void testMainIdealInput() { //нет ошибок в вводе текста исходного файла (идеальный ввод)

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainIdealOption.txt"};
        Deduce.main(args);
        assertEquals("aa, ss, a, s, d, u, v, ggg, M", outContent.toString());
    }

    @Test
    public void testMainFirstStrErr() { //ошибка в исходном файле в первой строке

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainFirstStrErr.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!",
                errContent.toString());
    }

    @Test
    public void testMainNoStrSep() { //нет строки разделитель

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainNoStrSep.txt"};
        Deduce.main(args);
        assertEquals("Строка №6 не соответствует формату строки с выражением!",
                errContent.toString());
    }

//    @Test
//    public void testMainLogicOperationErr() { //Две логические операции идут подряд
//
//        String[] args = {"-t", "C:\\Users\\Robert.Tenadze\\Desktop\\example\\test1\\test\\src\\txt\\testMainNoStrSep.txt"};
//        Deduce.main(args);
//        assertEquals("Строка №1 не соответствует формату строки с выражением!", outContent.toString());
//    }
    @Test
    public void testMainNoStrExFacts() { //нет строки с известными фактами

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainNoStrExFacts.txt"};
        Deduce.main(args);
        assertEquals("Ошибка в формате входных данных.\n"
                + "Файл состоит из логических выражений.\n"
                + "Далее идёт строка-разделитель, которая состоит из 64 '-'.\n"
                + "Последняя строка - строка с известными фактами.\n",
                errContent.toString());
    }

    @Test
    public void testMainStrSepErr() { //ошибка в строке разделитель

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainStrSepErr.txt"};
        Deduce.main(args);
        assertEquals("Строка №6 не соответствует формату строки с выражением!",
                errContent.toString());
    }

    @Test
    public void testMainExFactsErr() { //ошибка в строке с известными данными

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainExFactsErr.txt"};
        Deduce.main(args);
        assertEquals("Строка №7 не соответствует формату строки с выражением!",
                errContent.toString());
    }

//    В исходном файле отсутствует строка с известными фактами или не соответствует её формату!\n"
//                + "Строка с известными фактами должна стоять сразу после строки-разделитель и\n"
//                + "быть последней строкой в файле.\n
    @Test
    public void testMainFileNotFound() { //файла с таким именем не существует

        String[] args = {"-t", "src\\test\\java\\resource\\NoFile"};
        Deduce.main(args);
        assertEquals("Файла с таким именем не найдено!\n"
                + "Проверьте верность введённого пути.",
                errContent.toString());
    }

    @Test
    public void testMainWithNoErr() { //нет ошибок в вводе текста исходного файла (проверка " ", "_"  и цифр)

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainWithNoErr.txt"};
        Deduce.main(args);
        assertEquals("aa, a, s, _d, u, v, ggg, AA_54, M, _ss", outContent.toString());
    }

    @Test
    public void testMainNoRuleResult() {  //нет результата выражения

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainNoRuleResult.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    @Test
    public void testMainNoExpression() { //нет самого выражения

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainNoExpression.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    @Test
    public void testMainLogicOperationErr() { //Две логические операции идут подряд

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainLogicOperationErr.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    @Test
    public void testMainOnlyLogicOperation() { //нет самого выражения

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainOnlyLogicOperation.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    @Test
    public void testMainOpInRsultFact() { //логическая операция в результате рула

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainOpInRsultFact.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    @Test
    public void testMainCommaInExpression() { //запятая в самом выражение

        String[] args = {"-t", "src\\test\\java\\resource\\txt\\testMainCommaInExpression.txt"};
        Deduce.main(args);
        assertEquals("Строка №1 не соответствует формату строки с выражением!", errContent.toString());
    }

    //тесты на работу опций
    @Test
    public void testMainEmptyArgs() { //массив параметров пуст

        String[] args = {""};
        Deduce.main(args);
        assertEquals(String.format("usage: Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла, полученного на вход. Может быть выбран только один режим работы.%n" +
                "В данном меню показаны все режимы работы программы с их описанием:%n" +
                " -c,--toXML <Путь до файла с исходной информацией, путь до файла в который будет осуществляться запись>   данная команда осуществляет ковертацию TXT файла в XML файл%n" +
                " -d,--db <Путь до файла с настройками базы данных>                                                        данная команда осуществляет чтение из базы данных и парсинг, полученной информации%n" +
                " -h,--help                                                                                                данная команда показывает все режимы работы программы%n" +
                " -t,--txt <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из TXT файла и парсинг, полученной информации%n" +
                " -x,--xml <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из XML файла и парсинг, полученной информации%n" +
                " -z,--toDB <Путь до файла с исходной информацией, путь до файла с настройками базы данных>                данная команда осуществляет ковертацию TXT файла в DB%n" +
                "Вы не выбрали режим работы программы!%n"),
                outContent.toString());
    }
    
    @Test
    public void testMainNullArgs() { //массив параметров пуст

        String[] args = {};
        Deduce.main(args);
        assertEquals(String.format("usage: Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла, полученного на вход. Может быть выбран только один режим работы.%n" +
                "В данном меню показаны все режимы работы программы с их описанием:%n" +
                " -c,--toXML <Путь до файла с исходной информацией, путь до файла в который будет осуществляться запись>   данная команда осуществляет ковертацию TXT файла в XML файл%n" +
                " -d,--db <Путь до файла с настройками базы данных>                                                        данная команда осуществляет чтение из базы данных и парсинг, полученной информации%n" +
                " -h,--help                                                                                                данная команда показывает все режимы работы программы%n" +
                " -t,--txt <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из TXT файла и парсинг, полученной информации%n" +
                " -x,--xml <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из XML файла и парсинг, полученной информации%n" +
                " -z,--toDB <Путь до файла с исходной информацией, путь до файла с настройками базы данных>                данная команда осуществляет ковертацию TXT файла в DB%n" +
                "Вы не выбрали режим работы программы!%n"),
                outContent.toString());
    }

    @Test
    public void testMainErrInOption() {

        String[] args = {"-z", "src\\test\\java\\resource\\xml\\testMainXmlWithNoErr.xml"};
        Deduce.main(args);
        assertEquals(String.format("usage: Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла, полученного на вход. Может быть выбран только один режим работы.%n" +
                "В данном меню показаны все режимы работы программы с их описанием:%n" +
                " -c,--toXML <Путь до файла с исходной информацией, путь до файла в который будет осуществляться запись>   данная команда осуществляет ковертацию TXT файла в XML файл%n" +
                " -d,--db <Путь до файла с настройками базы данных>                                                        данная команда осуществляет чтение из базы данных и парсинг, полученной информации%n" +
                " -h,--help                                                                                                данная команда показывает все режимы работы программы%n" +
                " -t,--txt <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из TXT файла и парсинг, полученной информации%n" +
                " -x,--xml <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из XML файла и парсинг, полученной информации%n" +
                " -z,--toDB <Путь до файла с исходной информацией, путь до файла с настройками базы данных>                данная команда осуществляет ковертацию TXT файла в DB%n" +
                "Режим работы программы был задан неверно!%n"),
                outContent.toString());
    }

    @Test
    public void testMainErrInArg() {

        String[] args = {"-x"};
        Deduce.main(args);
        assertEquals(String.format("usage: Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла, полученного на вход. Может быть выбран только один режим работы.%n" +
                "В данном меню показаны все режимы работы программы с их описанием:%n" +
                " -c,--toXML <Путь до файла с исходной информацией, путь до файла в который будет осуществляться запись>   данная команда осуществляет ковертацию TXT файла в XML файл%n" +
                " -d,--db <Путь до файла с настройками базы данных>                                                        данная команда осуществляет чтение из базы данных и парсинг, полученной информации%n" +
                " -h,--help                                                                                                данная команда показывает все режимы работы программы%n" +
                " -t,--txt <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из TXT файла и парсинг, полученной информации%n" +
                " -x,--xml <Путь до файла с исходной информацией>                                                          данная команда осуществляет чтение из XML файла и парсинг, полученной информации%n" +
                " -z,--toDB <Путь до файла с исходной информацией, путь до файла с настройками базы данных>                данная команда осуществляет ковертацию TXT файла в DB%n" +
                "Режим работы программы был задан неверно!%n"),
                outContent.toString());
    }

    @Test
    public void testMainErrInPath() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoOp1.xml"};
        Deduce.main(args);
        assertEquals("Файла с таким именем не найдено!\n"
                + "Проверьте верность введённого пути.",
                errContent.toString());
    }

    //Тесты на соответсвие xsd сехме
    @Test
    public void testMainXmlWithNoErr() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoErr.xml"};
        Deduce.main(args);
        assertEquals("as, asd, aggg", outContent.toString());
    }

    @Test
    public void testMainXmlWithNoAttribute() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoAttribute.xml"};
        Deduce.main(args);
        assertEquals("Указанный файл не соответсвует формату входных данных!\n"
                + "(org.xml.sax.SAXParseException; systemId: file:/C:/Users/Robert.Tenadze/Desktop/project/testmaven/src/test/java/resource/xml/testMainXmlWithNoAttribute.xml; "
                + "lineNumber: 13; columnNumber: 15; cvc-complex-type.4: Attribute 'ruleResult' must appear on element 'rule'.)", errContent.toString());
    }

    @Test
    public void testMainXmlWithNoFact() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoFact.xml"};
        Deduce.main(args);
        assertEquals("Указанный файл не соответсвует формату входных данных!\n"
                + "(org.xml.sax.SAXParseException; systemId: file:/C:/Users/Robert.Tenadze/Desktop/project/testmaven/src/test/java/resource/xml/testMainXmlWithNoFact.xml; "
                + "lineNumber: 21; columnNumber: 18; cvc-complex-type.2.4.b: The content of element 'knownFacts' is not complete. One of '{knownFact}' is expected.)", errContent.toString());
    }

    @Test
    public void testMainXmlWithNoRule() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoRule.xml"};
        Deduce.main(args);
        assertEquals("Указанный файл не соответсвует формату входных данных!\n"
                + "(org.xml.sax.SAXParseException; systemId: file:/C:/Users/Robert.Tenadze/Desktop/project/testmaven/src/test/java/resource/xml/testMainXmlWithNoRule.xml; lineNumber: 13; columnNumber: 13; "
                + "cvc-complex-type.2.4.b: The content of element 'rules' is not complete. One of '{rule}' is expected.)", errContent.toString());
    }

    @Test
    public void testMainXmlWithNoOp() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithNoOp.xml"};
        Deduce.main(args);
        assertEquals("Указанный файл не соответсвует формату входных данных!\n"
                + "(org.xml.sax.SAXParseException; systemId: file:/C:/Users/Robert.Tenadze/Desktop/project/testmaven/src/test/java/resource/xml/testMainXmlWithNoOp.xml; lineNumber: 14; columnNumber: 16; "
                + "cvc-complex-type.2.4.b: The content of element 'rule' is not complete. One of '{and, or, fact}' is expected.)", errContent.toString());
    }

    @Test
    public void testMainXmlWithErrInFact() {

        String[] args = {"-x", "src\\test\\java\\resource\\xml\\testMainXmlWithErrInFact.xml"};
        Deduce.main(args);
        assertEquals("Указанный файл не соответсвует формату входных данных!\n"
                + "(org.xml.sax.SAXParseException; systemId: file:/C:/Users/Robert.Tenadze/Desktop/project/testmaven/src/test/java/resource/xml/testMainXmlWithErrInFact.xml; "
                + "lineNumber: 15; columnNumber: 36; cvc-pattern-valid: Value '1as' is not facet-valid with respect to pattern '_*[A-Za-z]+[_A-Za-z0-9]*' for type 'valueType'.)",
                errContent.toString());
    }
}
