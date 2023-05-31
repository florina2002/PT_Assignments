package org.example.BusinessLogic;

import org.example.Model.Task;
import org.example.Model.Server;

import java.util.ArrayList;

public interface Strategy {
    boolean addTask(ArrayList<Server> servers, Task c);
}