package machine.database.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface BaseDAO<Entity> {

	@Autowired
	public abstract void setSessionFactory(SessionFactory factory);

	public abstract void create(Entity e);

	public abstract void update(Entity e);

	public abstract void delete(Entity e);

	public abstract void merge(Entity e);

	public abstract SessionFactory getSessionFactory();
	
	void create(List<Entity> es);
	
	void update(List<Entity> es);
}
