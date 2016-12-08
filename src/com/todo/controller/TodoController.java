package com.todo.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.todo.jdo.PMF;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.todo.jdo.AdminJdo;
import com.todo.jdo.Data;

@Controller

public class TodoController {


	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("main");
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String todo(@RequestParam String username, @RequestParam String json,
			HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String todo = json;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		System.out.println(todo);
		
		AdminJdo storeAdminData = new AdminJdo();
		Query q = pm.newQuery(AdminJdo.class);
		q.setFilter("username == usernameParameter");
		q.declareParameters("String usernameParameter");
		AdminJdo store = null;
		try {
			
		store = pm.getObjectById(AdminJdo.class, username.trim());
		}
		catch (Exception e) {
			System.out.println(e);
		}
		//List<AdminJdo> checkEmailExist = (List<AdminJdo>) q.execute(username);
			
			
		if (store != null) 
		{
			try
			{
				store.setTodo(todo);
			}
			catch (Exception e) 
			{
				System.out.println(e);
			} 
			finally 
			{
				pm.close();
			}
				
		}
		 else {
			System.out.println("empty");
			storeAdminData.setUsername(username);
			storeAdminData.setTodo(todo);
			pm.makePersistent(storeAdminData);
			pm.close();
		}
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String login(@RequestBody String username, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
	
			System.out.println("inside");
			ArrayList em = new ArrayList();
			String queryStr = "SELECT FROM " + AdminJdo.class.getName();
			Query q2 = pm.newQuery(queryStr);
			List<AdminJdo> data = (List<AdminJdo>) q2.execute();

			try {
				for (AdminJdo e : data) {
					String name = e.getUsername();
					System.out.println("trying");
					System.out.println(name);
					AdminJdo store = pm.getObjectById(AdminJdo.class, name);
					if (username.contains(name)) {
						String todo = e.getTodo();
						System.out.println(todo);
						request.setAttribute(todo, todo);
						JSONArray jsondata = new JSONArray(todo);
						Gson gson = new GsonBuilder().create();
						JsonObject valInArray = gson.toJsonTree(jsondata).getAsJsonObject();
						String jsonData = gson.toJson(jsondata);
						return jsonData;
						// store.setTodo(todo);

					}
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				pm.close();
			}
		

		return "main";

	}

}
