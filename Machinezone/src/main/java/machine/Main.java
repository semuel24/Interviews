package machine;

import java.io.IOException;
import machine.mapper.CSVMapper;
import machine.mapper.JSONMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/machine-context.xml");
        
        JSONMapper jsonMapper = (JSONMapper) context.getBean("JSONMapper");
        CSVMapper csvMapper = (CSVMapper) context.getBean("CSVMapper");
        
        Loader loader = (Loader) context.getBean("loader");
        
        //Q1: load from API to DB
        loader.getUsers();

        //Q2: under SQL folder
        
        //Q3: load last 3 users from DB to JSON files
        loader.loadLast32File(jsonMapper, "/Users/yaoxu/Desktop/test/last3User.json");
        loader.loadLast32File( csvMapper, "/Users/yaoxu/Desktop/test/last3User.csv");
        
        
        //Q3: load user count
        loader.loadCountUser2File(jsonMapper, "/Users/yaoxu/Desktop/test/usercount.json");
        loader.loadCountUser2File( csvMapper, "/Users/yaoxu/Desktop/test/usercount.csv");
    }
}