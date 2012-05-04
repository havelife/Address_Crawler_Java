package com.chris.hibernate.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * 
 * <p>
 * Extend this interface if you want typesafe (no casting necessary) DAO's for
 * your domain objects.
 * 
 * @author Javen
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 */
public interface GenericDAO<T, PK extends Serializable> {

    /**
     * 取得对象的主键名.
     */
    String getIdName();

    /**
     * 按id获取对象.
     */
    T get(PK id);

    /**
     * 按Criteria查询唯一对象.
     * 
     * @param criterions
     *            数量可变的Criterion.
     */
    boolean exists(PK id);

    /**
     * 保存新增或修改的对象.
     */
    T save(T object);

    /**
     * 按id删除对象.
     */
    void remove(PK id);

    /**
     * 删除对象.
     */
    void remove(final T object);

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    <X> X findUnique(final String hql, final Object... values);

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    <X> X findUnique(final String hql, final Map<String, ?> values);

    /**
     * 执行HQL进行批量修改/删除操作.
     */
    int batchExecute(final String hql, final Object... values);

    /**
     * 执行HQL进行批量修改/删除操作.
     * 
     * @return 更新记录数.
     */
    int batchExecute(final String hql, final Map<String, ?> values);

    public int batchExecuteSQL(final String hql, final Object... values);

    public int batchExecuteSQL(final String hql, final Map<String, ?> values);

    public List findBySQL(final String queryString, final Object[] paramValues);
    
    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    Query createQuery(final String queryString, final Object... values);

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    Query createQuery(final String queryString, final Map<String, ?> values);

    public SQLQuery createSQLQuery(final String queryString, final Object... values);
    
    public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values);
    
    
    /**
     * 根据Criterion条件创建Criteria.
     * 
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * 
     * @param criterions
     *            数量可变的Criterion.
     */
    Criteria createCriteria(final Criterion... criterions);

    /**
     * 为Query添加distinct transformer.
     */
    Query distinct(Query query);

    /**
     * 为Criteria添加distinct transformer.
     */
    Criteria distinct(Criteria criteria);

    /**
     * Flush当前Session.
     */
    void flush();

    // ---------------------------- 查询对象集合 ----------------------------//

    /**
     * 获取全部对象.
     */
    List<T> getAll();

    /**
     * 获取全部对象,支持排序.
     */
    List<T> getAll(String orderBy, boolean isAsc);

    /**
     * 获取不重复的所有记录
     */
    List<T> getAllDistinct();

    /**
     * 按属性查找对象列表,匹配方式为相等.
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    /**
     * 按属性查找对象列表,匹配方式为相等.
     */
    List<T> findBy(final String propertyName, final Object value);

    /**
     * 按id列表获取对象.
     */
    List<T> findByIds(List<PK> ids);

    /**
     * 按HQL查询对象列表.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    <X> List<X> find(final String hql, final Object... values);

    /**
     * 按HQL查询对象列表.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    <X> List<X> find(final String hql, final Map<String, ?> values);

    /**
     * 按Criteria查询对象列表.
     * 
     * @param criterions
     *            数量可变的Criterion.
     */
    List<T> find(final Criterion... criterions);

    // ------------------ 老模式
    /**
     * @param hqlString
     * @param start
     * @param length
     * @return
     */
    List<T> findByNum(final String hqlString, final int start, final int length);

    /**
     * 根据HQL、参数、范围长度、获取结果集
     * 
     * @param queryString
     * @param paramValue
     *            参数值
     * @param start
     *            结果集开始
     * @param length
     *            结果集长度
     * @return
     */
    List<T> findByNum(final String hqlString, final Object paramValue,
	    final int start, final int length);

    List<T> findByNum(final String hqlString, final List<Object> list,
	    final int start, final int length);

    /**
     * 根据hql查询总数
     * 
     * @param hqlString
     *            select count(*) .....
     * @return
     */
    long getAmount(String hqlString);

    long getAmount(String hqlString, Object paramValue);

    long getAmount(final String hqlString, final List<Object> list);

    /**
     * 按Criteria查询唯一对象.
     * 
     * @param criterions
     *            数量可变的Criterion.
     */
    T findUnique(final Criterion... criterions);

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    T findUniqueBy(final String propertyName, final Object value);

    /**
     * 根据HQL获取单个数据
     * 
     * @param queryString
     * @return
     */
    T findOne(final String hqlString);

    T findOne(String hqlString, final Object paramValue);

    T findOne(final String hqlString, final List<Object> list);

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    boolean isPropertyUnique(final String propertyName, final Object newValue,
	    final Object oldValue);

    public SessionFactory getSessionFactory();

    public HibernateTemplate getHibernateTemplate();

    public Session getSession();
}