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
        
        // load from API to DB
//        loader.loadDataAPI2DB();

        
        // load DB to JSON files
//        loader.loadDataDB2File(jsonMapper, "/Users/yaoxu/Desktop/test/text2.json");

        // load DB to CSV files
        loader.loadDataDB2File( csvMapper, "/Users/yaoxu/Desktop/test/text1.csv");
    }

}