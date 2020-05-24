package com.vigus.controller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.infomodel.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vigus.dao.UserDao;
import com.vigus.entity.UserEntity;
@Controller
public class VigusController {
	@Autowired
	public UserDao ud;
	public static HashMap<String,UserEntity> h=new HashMap<>();
    public static List<String> l=new LinkedList<>(); 
	@RequestMapping("/")
	public String home()
	{
		return "login";
	}
	@RequestMapping("/present.htm")
	public String present(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("user")!=null)
		{
			request.getSession().setAttribute("active",h);
			return "main";
		}
		return "login";
	}
	@RequestMapping("/loginvalidation.htm")
	public String loginValidation(HttpServletRequest request,@RequestParam("user") String user,@RequestParam("pass") String pass) throws SQLException
	{
		
		System.out.println(user+" "+pass);
		UserEntity u=new UserEntity();
		if(ud.validate(user,pass,u))
		{
			
			request.getSession().setAttribute("user",u);
			h.put(user,u);
			request.getSession().setAttribute("active",h);
			return "main";
		
		}
		else
			request.setAttribute("loginerror","Incorrect Credentials");
	return "login";	
	}
	@RequestMapping("/invalidate.htm")
	public String inValidate(HttpServletRequest request) throws SQLException
	{
		UserEntity user=(UserEntity) request.getSession().getAttribute("user");
	
		ud.invalidate(user);
		
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		h.remove(user.getUser());
	return "login";	
	}
	@RequestMapping("/pastactive.htm")
	public String pastActive(HttpServletRequest request) throws SQLException
	{
		if(request.getSession().getAttribute("user")!=null)
		{
			List<UserEntity> l=ud.getPast(h);
			request.getSession().setAttribute("past",l);
			 return "past";
		}
		else
			return "login";
		       
		
	}
	@RequestMapping("/register.htm")
	public String register()
	{
		return "register";
	}
	@RequestMapping("/submitregistration.htm")
	  public String submitRegister(HttpServletRequest request,@RequestParam("user") String user,@RequestParam("password") String password,@RequestParam("age") String age) throws SQLException
	  {
		UserEntity u=new UserEntity();
		u.setUser(user);
		u.setAge(age);
		boolean status=ud.insert(u,password);
		if(status)
		return loginValidation(request, user,password);
		else
		{
			request.setAttribute("registererror","Incorrect Credentials");
			return "register";
		}
		
	  }

	


}
