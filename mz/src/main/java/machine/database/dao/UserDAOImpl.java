package machine.database.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import machine.database.entity.User;
import machine.database.entity.UserCount;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO
{   
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCount> findCountUser() {
    	String sql = "SELECT nationality, gender, count(*) as amount FROM leetcode.install_user group by nationality, gender;";
        Query query = this.factory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> objects = query.list();
        List<UserCount> result = new ArrayList<UserCount>();
        for (Object[] o : objects){
        	UserCount uc = new UserCount();
        	uc.setNationality((String)o[0]);
        	uc.setGender((String)o[1]);
        	uc.setAmount(((BigInteger)o[2]).intValue());
        	result.add(uc);
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<User> findLast3() {
    	String sql = "select * from install_user order by registered desc limit 0, 3";
        Query query = this.factory.getCurrentSession().createSQLQuery(sql)
                .addEntity(User.class);
        return (List<User>) query.list();
    }

}
