package machine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import machine.database.dao.UserDAO;
import machine.database.entity.User;
import machine.loader.CallResult;
import machine.loader.MachineClient;
import machine.mapper.Mapper;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("loader")
public class Loader
{
    @Autowired
    private MachineClient client;
    @Autowired
    private UserDAO userDAO;
//    @Autowired
//    @Qualifier("JSONMapper")
//    private Mapper jsonMapper;
//    @Autowired
//    @Qualifier("CSVMapper")
//    private Mapper csvMapper;
    

    public void loadDataAPI2DB()
    {
        int tokenCapacity = 1;
        int limitRate = 1;
        int requestTotal = 6;
        int maxFail = 20;

        TokenBucket bucket =
            TokenBuckets.builder().withCapacity(tokenCapacity)
                .withFixedIntervalRefillStrategy(limitRate, 1, TimeUnit.SECONDS).build();

        for (int i = 0; i < requestTotal; i++) {
            if (maxFail == 0) {
                System.out.println("Too many API connection exceptions...");
                break;
            }
            try {
                // get one token
                bucket.consume(1);

                // calling API...
                CallResult callResult = client.call("");

                // save to DB
                User user = new User();
                user.setGender("f");
                user.setNationality("China");
                user.setRegistered(new Date());
                userDAO.create(user);
            } catch (Exception ex) {
                System.out.println(ex);
                i--;
                maxFail--;
            }
        }
    }

    public void loadDataDB2File(Mapper mapper, String filePath) throws IOException
    {
        List<User> users = userDAO.findUserList();
        FileWriter file = new FileWriter(filePath);
        for (User _user : users) {
            try {
                String mapped = mapper.convert(_user);
                
                file.write(mapped);
                

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.flush();
        file.close();
    }
}
