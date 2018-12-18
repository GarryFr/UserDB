package com.wicket;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import domain.User;
import repository.UserRepository;

public class ListUser extends WebPage implements Serializable {

	@SuppressWarnings("serial")
	public ListUser() {

		add(new FindUserForm("userForm"));
		UserRepository userRepo = new UserRepository();

		IModel<List<User>> listModel = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return userRepo.selectAll();
			}
		};

		ListView<User> listUser = new ListView<User>("users", listModel) {

			protected void populateItem(ListItem<User> item) {
				final User user = item.getModelObject();
				item.add(new Label("name", new PropertyModel(item.getModel(), "name")));
				item.add(new Label("id", new PropertyModel(item.getModel(), "id")));
				Link<Long> deleteLink = new Link<Long>("remove", new Model<Long>(user.getId())) {
					@Override
					public void onClick() {
						userRepo.delete(user.getId());
					}
				};
				item.add(deleteLink);
				item.add(EditUser.link("edit", user.getId()));
			}
		};
		add(listUser);

		User user = new User();
		Form<?> form = new Form("form");
		TextField<String> id = new TextField<String>("id", new PropertyModel<String>(user, "id"));
		id.setOutputMarkupId(true);
		TextField<String> fName = new TextField<String>("name", new PropertyModel<String>(user, "name"));
		fName.setOutputMarkupId(true);
		
		Button button = new Button("submit") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				userRepo.insert(new User(user.getId(), user.getName()));
				new ListUser();
			}
		};

		add(form);
		form.add(id);
		form.add(fName);
		form.add(button);

	}

	public class FindUserForm extends Form {

		private String foundFullName;

		public FindUserForm(String id) {
			super(id);
			setDefaultModel(new CompoundPropertyModel(this));
			add(new Label("foundFullName"));
			add(new TextField("queryFullName", new PropertyModel<String>(newUser, "name")));
		}

		User newUser = new User();
		UserRepository userRepo = new UserRepository();

		@Override
		public void onSubmit() {
			super.onSubmit();
			User user = userRepo.findFullname(newUser.getName());
			foundFullName = user.getName();
		}
	};
}
