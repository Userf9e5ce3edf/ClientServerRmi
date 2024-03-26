package DAO;

import datasourceManagement.MySQLManager;

import java.util.LinkedList;
import java.util.List;

public abstract class DAOGenerique<T> {
    public MySQLManager mySQLManager = MySQLManager.getInstance();
    public abstract T create(T obj);
    public abstract T update(T obj);
    public abstract void delete(T obj);
    public abstract void saveAll(List<T> obj);
    public abstract T findById(String cle);
    public abstract List<T> findAll();
    public abstract List<T> findByName(String name);
}
