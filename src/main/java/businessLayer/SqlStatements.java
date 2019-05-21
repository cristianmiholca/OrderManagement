package businessLayer;

import java.lang.reflect.Field;

public class SqlStatements {

    public static String createSelectAllQuery(String tableName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("* ");
        stringBuilder.append("FROM ");
        stringBuilder.append(tableName);

        return stringBuilder.toString();
    }

    public static String createSelectQuery(String field, String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("* ");
        stringBuilder.append("FROM ");
        stringBuilder.append(tableName);
        stringBuilder.append(" WHERE " + field + "=?");

        return stringBuilder.toString();
    }

    public static String createInsertStatementSql(Class<?> type, String tableName) {
        /*Example:
            INSERT INTO tableName(field1, field2,...,fieldn) VALUES(?,?,...,?);
         */
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();

            if (fields.length() > 1) {
                fields.append(",");
                values.append(",");
            }
            fields.append(fieldName);
            values.append("?");
        }
        String sql = "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + values.toString() + ")";

        return sql;
    }

    public static String createUpdateStatementSql(Class<?> type, String tableName, String primaryKey) {
        /*Example
            UPDATE tableName SET field1=?, field2=?, ... , fieldn=? WHERE primaryKey=?";
         */
        StringBuilder fieldsString = new StringBuilder();
        StringBuilder whereString = new StringBuilder();

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length - 1; i++) {
            Field field = fields[i];
            String fieldName = field.getName();

            if (fieldName.equals(primaryKey)) {
                whereString.append(" WHERE ");
                whereString.append(fieldName);
                whereString.append("=");
                whereString.append("?");
            }

                fieldsString.append(fieldName);
                if (fieldsString.length() > 1) {
                    fieldsString.append("=");
                    fieldsString.append("?");
                    fieldsString.append(", ");
                }
        }

        if (fields.length > 0) {
            fieldsString.append(fields[fields.length - 1].getName());
            fieldsString.append("=");
            fieldsString.append("?");
        }

        String sql = "UPDATE " + tableName + " SET " + fieldsString.toString() + whereString.toString();

        return sql;
    }

    public static String createDeleteStatementSql(String tableName, String field) {
        /*Example
            DELETE FROM tableName WHERE field=?"
         */

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ");
        sql.append("FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(field);
        sql.append("=");
        sql.append("?");

        return sql.toString();
    }

}
