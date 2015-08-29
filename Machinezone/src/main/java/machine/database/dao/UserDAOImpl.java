package machine.database.dao;

import java.util.List;
import machine.database.entity.User;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO
{
    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserList() {
        String sql = "select * from user";
        Query query = this.factory.getCurrentSession().createSQLQuery(sql)
                .addEntity(User.class);
//        query.setString("pkg", _pkgName);
        return (List<User>) query.list();
    }
  

}
