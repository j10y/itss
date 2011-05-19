package com.zsgj.itil.util;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;

/**
 * 从新mysql驱动，增加jdbc Type的映射
 * <br>1，LONGVARCHAR(jdbc Type=-1)--text
 * @author wchao
 *
 */
public class MySqlDialectEx extends MySQLDialect {
    public MySqlDialectEx() {
        super();
        registerHibernateType(Types.LONGVARCHAR, "text");
    }
}