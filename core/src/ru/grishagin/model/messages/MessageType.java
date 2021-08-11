package ru.grishagin.model.messages;

public interface MessageType {
    int DEATH = 0;
    int CLOSED = 1;
    int OPENED = 2;
    int ATTACK = 3;

    int UI_UPDATE = 100;

    int ORIENTATION_CHANGE = 200;
    int STOP_MVMNT = 201;
    int START_MVMNT = 202;
}
