package businessLayer;

import dataAccessLayer.ConnectionFactory;
import dataAccessLayer.DbUtil;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    public void insert(Object object) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Class<?> objectClass = object.getClass();
        String sql = SqlStatements.createInsertStatementSql(objectClass, type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            Field[] fields = objectClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);

                preparedStatement.setObject((i + 1), value);
            }
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: insert" + e.getMessage());
        } finally {
            DbUtil.close(connection);
            DbUtil.close(preparedStatement);
        }

    }

    public void update(Object object, String whereCondition, Object valueOfCondition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Class<?> objectClass = object.getClass();
        String sql = SqlStatements.createUpdateStatementSql(objectClass, type.getSimpleName(), whereCondition);

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            Field[] fields = type.getDeclaredFields();
            int pkIndex = fields.length + 1;

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);

                if (field.getName().equals(whereCondition)) {
                    if (value.getClass().equals(valueOfCondition.getClass())) {
                        preparedStatement.setObject(pkIndex, valueOfCondition);
                    } else {
                        System.out.println("Error! Mismatch!");
                    }
                }
                preparedStatement.setObject((i + 1), value);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: update" + e.getMessage());
        }
    }

    public void delete(String whereCondition, Object valueOfCondition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = SqlStatements.createDeleteStatementSql(type.getSimpleName(), whereCondition);

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setObject(1, valueOfCondition);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: delete" + e.getMessage());
        }
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
            return list;
        } catch (SQLException | InstantiationException | IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T findByUniqueField(String whereCondition, Object valueCondition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = SqlStatements.createSelectQuery(whereCondition, type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, valueCondition);
            resultSet = preparedStatement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById" + e.getMessage());
        } finally {
            DbUtil.close(connection);
            DbUtil.close(preparedStatement);
            DbUtil.close(resultSet);
        }
        return null;
    }

    public List<T> selectAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = SqlStatements.createSelectAllQuery(type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
            DbUtil.close(preparedStatement);
            DbUtil.close(resultSet);
        }
        return null;
    }

    public void populateTable(List<T> objectList, JTable table) {
        int nbOfFields = type.getDeclaredFields().length;
        String[] fields = new String[nbOfFields];
        int i = 0;


        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();
            fields[i++] = fieldName;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(fields);

        for(T object : objectList){
            i=0;
            Object[] o = new Object[nbOfFields];
             for(Field field : type.getDeclaredFields()){
                 try {
                     PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                     Method method = propertyDescriptor.getReadMethod();
                     o[i++] = method.invoke(object);
                 } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                     e.printStackTrace();
                 }
             }
             model.addRow(o);
        }
        table.setModel(model);
    }
}