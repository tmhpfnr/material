/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Modifications Copyright 2017 appreciated
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.uitest;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

/**
 * @author Vaadin Ltd
 */
public class Forms extends VerticalLayout implements View {

    private boolean readOnly = true;

    public Forms() {
        setSpacing(true);
        setMargin(true);

        Label title = new Label("Forms");
        title.addStyleName(ValoTheme.LABEL_H1);
        addComponent(title);

        final FormLayout form = new FormLayout();

        form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName(MaterialTheme.BUTTON_BORDERLESS);
        addComponent(form);

        HorizontalLayout wrapper = new HorizontalLayout(form);
        wrapper.setMargin(true);

        Label section = new Label("Personal Info");
        section.addStyleName(ValoTheme.LABEL_H2);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(section);
        StringGenerator sg = new StringGenerator();

        TextField name = new TextField("Name");
        name.setValue(sg.nextString(true) + " " + sg.nextString(true));
        name.setWidth("50%");
        form.addComponent(name);

        AbstractDateField<LocalDate, DateResolution> birthday = new TestDateField("Birthday");
        birthday.setValue(LocalDate.of(1980, 1, 31));
        form.addComponent(birthday);

        TextField username = new TextField("Username");
        username.setValue(sg.nextString(false) + sg.nextString(false));
        form.addComponent(username);

        RadioButtonGroup sex = new RadioButtonGroup<String>("Sex");
        sex.setItems("Female", "Male");
        sex.setValue("Male");
        sex.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        form.addComponent(sex);

        section = new Label("Contact Info");
        section.addStyleName(ValoTheme.LABEL_H3);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(section);

        TextField email = new TextField("Email");
        email.setValue(
                sg.nextString(false) + "@" + sg.nextString(false) + ".com");
        email.setWidth("50%");
        form.addComponent(email);

        TextField location = new TextField("Location");
        location.setValue(sg.nextString(true) + ", " + sg.nextString(true));
        location.setWidth("50%");
        location.setComponentError(new UserError("This address doesn't exist"));
        form.addComponent(location);

        TextField phone = new TextField("Phone");
        phone.setWidth("50%");
        form.addComponent(phone);

        HorizontalLayout wrap = new HorizontalLayout();
        wrap.setSpacing(true);
        wrap.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        wrap.setCaption("Newsletter");
        CheckBox newsletter = new CheckBox("Subscribe to newsletter", true);
        wrap.addComponent(newsletter);

        ComboBox<String> period = new ComboBox<>();
        period.setTextInputAllowed(false);
        period.setItems("Daily", "Weekly", "Montly");
        period.setEmptySelectionAllowed(false);
        period.setValue("Weekly");
        period.addStyleName(ValoTheme.COMBOBOX_SMALL);
        period.setWidth("10em");
        wrap.addComponent(period);
        form.addComponent(wrap);

        section = new Label("Additional Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(section);

        TextField website = new TextField("Website");
        website.setPlaceholder("http://");
        website.setWidth("100%");
        form.addComponent(website);

        TextArea shortbio = new TextArea("Short Bio");
        shortbio.setValue(
                "Quis aute iure reprehenderit in voluptate velit esse. Cras mattis iudicium purus sit amet fermentum.");
        shortbio.setWidth("100%");
        shortbio.setRows(2);
        form.addComponent(shortbio);

        final RichTextArea bio = new RichTextArea("Bio");
        bio.setWidth("100%");
        bio.setValue(
                "<div><p><span>Integer legentibus erat a ante historiarum dapibus.</span> <span>Vivamus sagittis lacus vel augue laoreet rutrum faucibus.</span> <span>A communi observantia non est recedendum.</span> <span>Morbi fringilla convallis sapien, id pulvinar odio volutpat.</span> <span>Ab illo tempore, ab est sed immemorabili.</span> <span>Quam temere in vitiis, legem sancimus haerentia.</span></p><p><span>Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Cum sociis natoque penatibus et magnis dis parturient.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Tityre, tu patulae recubans sub tegmine fagi  dolor.</span></p><p><span>Curabitur blandit tempus ardua ridiculus sed magna.</span> <span>Phasellus laoreet lorem vel dolor tempus vehicula.</span> <span>Etiam habebis sem dicantur magna mollis euismod.</span> <span>Hi omnes lingua, institutis, legibus inter se differunt.</span></p></div>");
        form.addComponent(bio);

        bio.setReadOnly(true);

        Button edit = new Button("Edit", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (readOnly) {
                    bio.setReadOnly(false);
                    form.removeStyleName(ValoTheme.FORMLAYOUT_LIGHT);
                    event.getButton().setCaption("Save");
                    event.getButton().addStyleName(ValoTheme.BUTTON_PRIMARY);
                } else {
                    bio.setReadOnly(true);
                    form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
                    event.getButton().setCaption("Edit");
                    event.getButton().removeStyleName(ValoTheme.BUTTON_PRIMARY);
                }
                readOnly = !readOnly;
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);
        footer.addComponent(edit);

        Label lastModified = new Label("Last modified by you a minute ago");
        lastModified.addStyleName(ValoTheme.LABEL_LIGHT);
        footer.addComponent(lastModified);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
