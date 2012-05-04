package com.chris.service.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.chris.hibernate.dao.base.GenericDAO;



/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *     &lt;bean id="userManager" class="sanwei.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="sanwei.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="sanwei.entity.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * <p>
 * If you're using iBATIS instead of Hibernate, use:
 * 
 * <pre>
 *     &lt;bean id="userManager" class="sanwei.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="sanwei.dao.ibatis.GenericDaoiBatis"&gt;
 *                 &lt;constructor-arg value="sanwei.model.User"/&gt;
 *                 &lt;property name="dataSource" ref="dataSource"/&gt;
 *                 &lt;property name="sqlMapClient" ref="sqlMapClient"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
@Transactional
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {

    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass())
     * from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected GenericDAO<T, PK> dao;

    public GenericManagerImpl() {
    }

    public GenericManagerImpl(GenericDAO<T, PK> genericDao) {
        this.dao = genericDao;
    }

    public GenericManagerImpl(SessionFactory sessionFactory) {
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return dao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void flush() {
        dao.flush();
    }
    
    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        dao.remove(id);
    }



    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
        return dao.isPropertyUnique(propertyName, newValue, oldValue);
    }

    public void uniqueByKeys(boolean flag, T entity, List<String> keys) {
        // TODO Auto-generated method stub
        
    }

  
}
