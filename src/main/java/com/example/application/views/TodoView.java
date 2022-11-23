package com.example.application.views;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("")
public class TodoView extends VerticalLayout {
    List<Todo> todos = new ArrayList<>();
    VirtualList<Todo> list = new VirtualList<>();

    static record Todo(String task, boolean done) {
    };

    TodoView() {
        var task = new TextField();
        var button = new Button("Add", click -> {
            todos.add(new Todo(task.getValue(), false));
            task.clear();
            refreshList();
        });

        list.setRenderer(new ComponentRenderer<Component, Todo>(TodoLayout::new));
        list.setItems(todos);

        add(new HorizontalLayout(task, button), list);
    }

    void refreshList() {
        list.setItems(todos);
    }

    void updateStatus(Todo todo, boolean done) {
        todos.set(todos.indexOf(todo), new Todo(todo.task, done));
        refreshList();
    }

    class TodoLayout extends HorizontalLayout {
        TodoLayout(Todo todo) {
            var done = new Checkbox(todo.done);
            var delete = new Button("Delete");

            done.addValueChangeListener(v -> updateStatus(todo, v.getValue()));
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
            delete.addClickListener(click -> {
                todos.remove(todo);
                refreshList();
            });

            add(done, new Span(todo.task), delete);
            setAlignItems(Alignment.CENTER);
        }
    }

}
