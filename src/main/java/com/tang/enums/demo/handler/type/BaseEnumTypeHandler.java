package com.tang.enums.demo.handler.type;

import com.tang.enums.demo.interfaces.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class BaseEnumTypeHandler<E extends Enum<E> & BaseEnum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public BaseEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, e.getValue());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        if (null == resultSet.getObject(columnName) && resultSet.wasNull()) {
            return null;
        }

        return valueOf(resultSet.getString(columnName));
    }

    private E valueOf(String value) {
        E[] constants = type.getEnumConstants();
        for (E each : constants) {
            if (Objects.equals(String.valueOf(each.getValue()), value)) {
                return each;
            }
        }
        return null;
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        if (null == resultSet.getObject(i) && resultSet.wasNull()) {
            return null;
        }

        return valueOf(resultSet.getString(i));
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        if (null == callableStatement.getObject(i) && callableStatement.wasNull()) {
            return null;
        }

        return valueOf(callableStatement.getString(i));
    }
}
