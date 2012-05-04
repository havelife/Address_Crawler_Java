package com.chris.service.base;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.<br>
 * 
 * <p>
 * Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     * 
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     * 
     * @param id
     *            the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * 
     * @param id
     *            the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * 
     * @param object
     *            the object to save
     * @return the updated object
     */
    T save(T object);
    
    void flush();


    /**
     * Generic method to delete an object based on class and id
     * 
     * @param id
     *            the identifier (primary key) of the object to remove
     */
    void remove(PK id);



    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue);

    /**
     * 根据联合主键以判断数据是否重复，支持新增 编辑状态
     * 
     * @param flag
     *            是否在编辑状态
     * @param entity
     *            对象
     * @param keys
     *            主键集合
     */
    public void uniqueByKeys(boolean flag, T entity, List<String> keys);
}
