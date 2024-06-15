package com.liamtseva.goal_tracking;

import android.content.DialogInterface;

//Після закриття діалогового вікна оновити користувацький інтерфейс
public interface DialogCloseListener {
    public void handleDialogClose(DialogInterface dialog);
}
