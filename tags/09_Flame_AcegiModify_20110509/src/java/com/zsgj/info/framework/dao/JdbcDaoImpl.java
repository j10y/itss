package com.zsgj.info.framework.dao;
//package com.digitalchina.info.framework.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//  
//public class JdbcDaoImpl implements JdbcDao{  
//      
//    private JdbcTemplate jdbcTemplate;  
//      
//    //NamedParameterJdbcTemplate��JdbcTemplate��װ��������������������  
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;  
//  
//    //SimpleJdbcTemplate��JdbcTemplate��װ,ĳЩ����Ҫ��java5���ϲŹ���  
//    private SimpleJdbcTemplate simpleJdbcTemplate;  
//      
//    //�򻯲������ݲ���  
//    private SimpleJdbcInsert inserBaseObject;  
//      
//    public void setDataSource(DataSource dataSource){  
//        this.jdbcTemplate = new JdbcTemplate(dataSource);  
//        this.namedParameterJdbcTemplate = new ParameterJdbcTemplate(dataSource);  
//        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);  
//        this.inserBaseObject = new SimpleJdbcInsert(dataSource)  
//        .withTableName("actors")  
//        .usingColumns("first_name","last_name")//������Щ�ֶ�  
//        .usingGeneratedKeyColumns("id");//�������ɵ�id  
//    }  
//  
//    /** 
//     * ��SimpleJdbcInsert����һ����¼ 
//     */  
//    public long inserOneBaseObject(BaseObject actor){  
//        Map<String,Object> parameters = new HashMap<String,Object>();  
//       // parameters.put("first_name",actor.getFirstName());  
//        //parameters.put("last_name",actor.getLastName());  
//        return inserBaseObject.executeAndReturnKey(parameters).longValue();  
//    }  
//      
//    /** 
//     * ͳ��firstName��ͬ������ 
//     * @param firstName 
//     * @return 
//     */  
//    public int findCountOfBaseObjectsByFirstName(String firstName){  
//        String sql="select count(0) from actors where first_name = :first_name";  
//        SqlParameterSource namedParameters = new MapSqlParameterSource("first_name",firstName);  
//         //Map namedParameter = Collections.singletonMap("first_name",firstName);  
//         //����һ��Bean��װ�ķ�ʽ  
//         //SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(exampleBaseObject);  
//        return this.namedParameterJdbcTemplate.queryForInt(sql, namedParameters);  
//    }  
//  
//    /** 
//     * ����SQL���� 
//     * @param sql 
//     */  
//    public void createTableBySQL(String sql) {  
//        this.jdbcTemplate.execute(sql);  
//    }  
//      
//    /** 
//     * �����¼�������Զ����ɵ�����Id(MySQL�в��У�Oracle����) 
//     * @param ps 
//     * @return 
//     */  
//    public KeyHolder insertBaseObject(final BaseObject actor){  
//        final String addSql = "insert into actors(first_name,last_name) values (?,?)";  
//        KeyHolder keyHolder = new GeneratedKeyHolder();  
//        this.jdbcTemplate.update(new PreparedStatementCreator(){  
//            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {  
//                PreparedStatement ps =  
//                    conn.prepareStatement(addSql, new String[]{"id"});//����id  
//               // ps.setString(1, actor.getFirstName());  
//               // ps.setString(2, actor.getLastName());  
//                return ps;  
//            }  
//              
//        });  
//        System.out.println(keyHolder.getKey());  
//        return keyHolder;  
//    }  
//      
//    /** 
//     * ����/����/ɾ������ 
//     * @param sql �в������ 
//     * @param obj ����ֵ���� 
//     */  
//    public int operateBaseObject(String sql,Object[] obj){  
//        return this.jdbcTemplate.update(sql, obj);  
//    }  
//
//    /** 
//     * ����SQL��ѯ��¼���� 
//     * @param sql 
//     * @return 
//     */  
//    public int findRowCountBySQL(String sql){  
//        return this.jdbcTemplate.queryForInt(sql);  
//    }  
//      
//    /** 
//     * ����Id����ָ������ 
//     * @param id 
//     * @return 
//     */  
//    public BaseObject findBaseObjectById(long id){  
//        BaseObject actor = (BaseObject) this.jdbcTemplate.queryForObject(  
//                "select id,first_name,last_name from actors where id = ?",  
//                new Object[]{new Long(id)},   
//                new RowMapper(){  
//  
//                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
//                        // BaseObject act = new BaseObject();  
//                        // act.setId(rs.getLong("id"));  
//                      /* act.setFirstName(rs.getString("first_name"));  
//                       act.setLastName(rs.getString("last_Name"));  */
//                      // return act;  
//                    	return null;
//                   }  
//                     
//               });  
//       return actor;  
//   }  
// 
// 
//   /** 
//    * ����Id����ָ������ 
//    * @param id 
//    * @return 
//     */  
//    public BaseObject findBaseObjectByIdSimple(long id){  
//        String sql = "select id,first_name,last_name from actors where id = ?";  
//          
//        ParameterizedRowMapper<BaseObject> mapper = new ParameterizedRowMapper<BaseObject>(){  
//           //notice the return type with respect to java 5 covariant return types  
//           public BaseObject mapRow(ResultSet rs, int rowNum) throws SQLException {  
//               /*BaseObject act = new BaseObject();  
//               act.setId(rs.getLong("id"));  
//               act.setFirstName(rs.getString("first_name"));  
//               act.setLastName(rs.getString("last_Name"));  
//               return act;  */
//        	   return null;
//           }  
//       };  
//         
//       return this.simpleJdbcTemplate.queryForObject(sql, mapper, id);  
//   }  
//     
//   /** 
//    * �������ж��� 
//    * @return 
//     */  
//    public List findAllBaseObjects(){  
//        return this.jdbcTemplate.query(  
//                "select id,first_name,last_name from actors",  
//                new BaseObjectMapper());  
//    }  
//      
//    /** 
//    * ����һ����̬�ڲ��࣬��Dao�ķ����б����� 
//    * @author logcd 
//    */  
//   private static final class BaseObjectMapper implements RowMapper{  
// 
//       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
//          // BaseObject act = new BaseObject();  
//         /*  act.setId(rs.getLong("id"));  
//           act.setFirstName(rs.getString("first_name"));  
//           act.setLastName(rs.getString("last_Name"));  */
//         //  return act;  
//    	   return null;
//       }  
//         
//   }  
//}
//   
//   
