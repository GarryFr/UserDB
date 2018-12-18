package com.wicket;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.validation.validator.StringValidator;

import domain.User;
import repository.UserRepository;

public class EditUser extends WebPage {

	public EditUser(User user) {

		add(new EditUserForm("editUserForm", user));
	}

	public static Link<Void> link(final String name, final long id) {

		return new Link<Void>(name) {
			UserRepository userRepo = new UserRepository();
			User thisUser = userRepo.selectById(id);

			@Override
			public void onClick() {
				setResponsePage(new EditUser(thisUser));
			}
		};
	}

	static public final class EditUserForm extends Form<User> {

		public EditUserForm(final String formId, final User user) {
			super(formId, new CompoundPropertyModel<>(user));

			add(new TextField<String>("id", new PropertyModel<String>(user, "id")));
			add(new TextField<String>("name", new PropertyModel<String>(user, "name")));

		}

		@Override
		public void onSubmit() {
			super.onSubmit();
			User user = getModelObject();
			UserRepository userRepo = new UserRepository();
			userRepo.update(new User(user.getId(), user.getName()));
		}
	}
}
