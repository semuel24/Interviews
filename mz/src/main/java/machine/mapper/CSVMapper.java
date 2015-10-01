package machine.mapper;

import org.springframework.stereotype.Component;

import machine.database.entity.User;
import machine.database.entity.UserCount;

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
    
    
    //nationality, gender, amount
    @Override
    public String convert(UserCount uc) {
    	String result = "";
    	if(uc.getNationality() != null) {
            result += uc.getNationality();
        }
    	result += ",";
        if(uc.getGender() != null) {
            result += uc.getGender();
        }
        result += ",";
        if(uc.getAmount() != null) {
            result += uc.getAmount();
        }
        result += "\n";
        return result;
    }
}