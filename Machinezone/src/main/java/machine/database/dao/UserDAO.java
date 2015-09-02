package machine.database.dao;

import java.util.List;

import machine.database.entity.User;
import machine.database.entity.UserCount;

public interface UserDAO extends BaseDAO<User>
{
    public List<UserCount> findCountUser();
    public List<User> findLast3();
}
