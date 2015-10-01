package machine.mapper;

import machine.database.entity.User;
import machine.database.entity.UserCount;

public interface Mapper
{
	//convert user
    public String convert(User user);
    
    //convert user count
    public String convert(UserCount uc);
}
