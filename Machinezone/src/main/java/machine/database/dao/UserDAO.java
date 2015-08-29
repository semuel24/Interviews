package machine.database.dao;

import java.util.List;
import machine.database.entity.User;

public interface UserDAO extends BaseDAO<User>
{
    public List<User> findUserList();
}
