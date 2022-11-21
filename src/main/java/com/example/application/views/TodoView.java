package com.example.application.views;

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

import java.util.ArrayList;
import java.util.List;

@Route("")
public class TodoView extends VerticalLayout {
    List<Todo> todos = new ArrayList<>();
    VirtualList<Todo> list = new VirtualList<>();

    TodoView() {
        var task = new TextField();
        var button = new Button("Add", click-> {
            todos.add(new Todo(task.getValue()));
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

    class TodoLayout extends HorizontalLayout {
        TodoLayout(Todo todo){
            var done = new Checkbox(todo.isDone());
            var delete = new Button("Delete");

            done.addValueChangeListener(v -> {
                todo.setDone(v.getValue());
            });
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
            delete.addClickListener(click -> {
                todos.remove(todo);
                refreshList();
            });

            add(done, new Span(todo.getTask()), delete);
            setAlignItems(Alignment.CENTER);
        }
    }

}
