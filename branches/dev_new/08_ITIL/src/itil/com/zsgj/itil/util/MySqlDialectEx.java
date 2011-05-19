package com.zsgj.itil.util;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;

/**
 * ����mysql����������jdbc Type��ӳ��
 * <br>1��LONGVARCHAR(jdbc Type=-1)--text
 * @author wchao
 *
 */
public class MySqlDialectEx extends MySQLDialect {
    public MySqlDialectEx() {
        super();
        registerHibernateType(Types.LONGVARCHAR, "text");
    }
}