package org.example.framework.database.core;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.util.StringUtils;

import java.sql.*;

/**
 * MySQL 数据库字段加密处理器
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({String.class})
public class DbFieldCryptoHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (StringUtils.hasLength(parameter)) {
            // aes 加密数据
            ps.setString(i, DbEncryptHelper.encrypt(parameter));
        } else {
            ps.setNull(i, Types.VARBINARY);
        }
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (!StringUtils.hasLength(result)) {
            return null;
        }
        // aes 解密数据
        return DbEncryptHelper.decrypt(result);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (!StringUtils.hasLength(result)) {
            return null;
        }
        // aes 解密数据
        return DbEncryptHelper.decrypt(result);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (!StringUtils.hasLength(result)) {
            return null;
        }
        // aes 解密数据
        return DbEncryptHelper.decrypt(result);
    }
}
