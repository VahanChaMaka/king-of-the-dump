package ru.grishagin.ui.menu;

public class MenuAction {
    private String title;
    private Runnable action;

    public MenuAction(String title, Runnable action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public Runnable getAction() {
        return action;
    }
}
