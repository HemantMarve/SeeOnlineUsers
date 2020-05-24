package com.vigus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.registry.infomodel.User;

import org.springframework.stereotype.Component;

import com.vigus.entity.UserEntity;
@Component
public class UserDao {
	public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
public boolean validate(String user,String pass, UserEntity u) throws SQLException {
	Connection con=CreateConnection.getConnection();
	if(con==null||CreateConnection.getConnection().isClosed())
	{
		CreateConnection.setConnection();
		con=CreateConnection.getConnection();
	}
	try
	{
		PreparedStatement stmt=con.prepareStatement("select * from vigususer where user=? and password=?");	
		PreparedStatement st=con.prepareStatement("update vigususer set lastactive=? where user=?");		
		stmt.setString(1,user);  
		stmt.setString(2,pass);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
        	u.setUser(rs.getString(1));
        	u.setAge(rs.getString(3));
        	u.setTime(getCurrentTimeStamp().toString());
        	st.setTimestamp(1,getCurrentTimeStamp());
        	st.setString(2,user);
        	st.executeUpdate();
        	return true;
         }
    	
        }	
	catch(Exception e)
	{ 
		System.out.println(e);
		con.close();
	}
     return false;
}
public void invalidate(UserEntity u) throws SQLException {
	Connection con=CreateConnection.getConnection();
	if(con==null||CreateConnection.getConnection().isClosed())
	{
		CreateConnection.setConnection();
		con=CreateConnection.getConnection();
	}
	try
	{
		
		PreparedStatement st=con.prepareStatement("update vigususer set lastactive=? where user=?");		
        	st.setTimestamp(1,getCurrentTimeStamp());
        	st.setString(2,u.getUser());
        	st.executeUpdate();
        	con.close();
        }	
	catch(Exception e)
	{ 
		System.out.println(e);
		con.close();
		
	}

}
public List<UserEntity> getPast(HashMap<String, UserEntity> h) throws SQLException {
	Connection con=CreateConnection.getConnection();
	List<UserEntity> l=new LinkedList<>();
	if(con==null||CreateConnection.getConnection().isClosed())
	{
		CreateConnection.setConnection();
		con=CreateConnection.getConnection();
	}
	try
	{
		
		PreparedStatement st=con.prepareStatement("select * from vigususer");		
        	ResultSet rs=st.executeQuery();
        	while(rs.next())
        	{
        		UserEntity u=new UserEntity();
        		u.setUser(rs.getString(1));
        		u.setAge(rs.getString(3));
        		u.setTime(rs.getTimestamp(4).toString());
        		if(!h.containsKey(u.getUser()))
        		{
        			l.add(u);
        		}
        	}
        
        }	
	catch(Exception e)
	{ 
		System.out.println(e);
		con.close();
		
	}

	return l;
}
public boolean insert(UserEntity u,String password) throws SQLException {
	Connection con=CreateConnection.getConnection();
	if(con==null||CreateConnection.getConnection().isClosed())
	{
		CreateConnection.setConnection();
		con=CreateConnection.getConnection();
	}
	List<UserEntity> l=new LinkedList<>();
	PreparedStatement stmt=con.prepareStatement("select * from vigususer where user=?");		
	stmt.setString(1,u.getUser());  
    ResultSet rs=stmt.executeQuery();
    if(rs.next())
    {
    	return false;
    }

	try
	{
		PreparedStatement st=con.prepareStatement("insert into vigususer (user,password,age) values(?,?,?)");
		st.setString(1, u.getUser());
		st.setString(2,password);
		st.setString(3, u.getAge());
        st.executeUpdate();
        }	
	catch(Exception e)
	{ 
		System.out.println(e);
		con.close();
	}
	return true;
	
}


   

}
