package machine.database.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class BaseDAOImpl<Entity> implements BaseDAO<Entity> {
	protected SessionFactory factory;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Entity e) {
		this.factory.getCurrentSession().saveOrUpdate(e);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Entity e) {
		this.factory.getCurrentSession().saveOrUpdate(e);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Entity e){
		this.factory.getCurrentSession().delete(e);	
	}

	@Override
	@Transactional(readOnly = false)
	public void merge(Entity e){
		this.factory.getCurrentSession().merge(e);
	}

	/*
	 * A size of zero means return all records
	 */
	public static void setPaginationParams(Query query, int start, int size) {
		query.setFirstResult(start);
		if (size > 0) {
			query.setMaxResults(size);
		}
	}

	/*
	 * A size of zero means return all records
	 */
	public static void setPaginationParams(Criteria c, int start, int size) {
		c.setFirstResult(start);
		if (size > 0) {
			c.setMaxResults(size);
		}
	}

	public SessionFactory getSessionFactory() {
		return factory;
	}

	@Override
	@Transactional(readOnly = false)
	public void create(List<Entity> es) {
		if(es != null) {
			Session session = factory.getCurrentSession();
			for(Entity e : es) {
				session.saveOrUpdate(e);
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(List<Entity> es) {
		if(es != null) {
			Session session = factory.getCurrentSession();
			for(Entity e : es) {
				session.update(e);
			}
		}
	}
}
