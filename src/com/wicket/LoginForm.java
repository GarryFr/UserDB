package com.wicket;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import domain.User;
import repository.UserRepository;

public class LoginForm extends Form{

	private String username;
	private String password;
	private String loginStatus;

    public LoginForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel(this));
		
		add(new TextField("username"));
		add(new PasswordTextField("password"));
		add(new Label("loginStatus"));
	}

	public final void onSubmit() {
		if ("test".equals(username) && "test".equals(password)) {
          setResponsePage(ListUser.class);
		} else {
			loginStatus = "Wrong username or password !";
		}
	}
    

	}
