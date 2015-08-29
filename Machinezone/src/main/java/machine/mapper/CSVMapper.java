package machine.mapper;

import org.springframework.stereotype.Component;
import machine.database.entity.User;

@Component("CSVMapper")
public class CSVMapper implements Mapper
{
    //gender, name, registered, nationality
    @Override
    public String convert(User user) {
        String result = "";
        if(user.getGender() != null) {
            result += user.getGender();
        }
        result += ",";
        if(user.getName() != null) {
            result += user.getName();
        }
        result += ",";
        if(user.getRegistered() != null) {
            result += user.getRegistered();
        }
        result += ",";
        if(user.getNationality() != null) {
            result += user.getNationality();
        }
        result += "\n";
        return result;
    }
}