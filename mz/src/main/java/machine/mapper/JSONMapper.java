package machine.mapper;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import machine.database.entity.User;
import machine.database.entity.UserCount;

@Component("JSONMapper")
public class JSONMapper implements Mapper
{
    @Override
    public String convert(User user) {
        JSONObject obj = new JSONObject();
        obj.put("gender", user.getGender());
        obj.put("name", user.getName());
        obj.put("registered", user.getRegistered());
        obj.put("nationality", user.getNationality());
        return obj.toJSONString();
    }
    
    @Override
    public String convert(UserCount user) {
        JSONObject obj = new JSONObject();
        obj.put("nationality", user.getNationality());
        obj.put("gender", user.getGender());
        obj.put("count", user.getAmount());
        return obj.toJSONString();
    }
}
