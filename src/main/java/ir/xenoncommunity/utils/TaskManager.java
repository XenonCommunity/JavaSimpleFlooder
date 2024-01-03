package ir.xenoncommunity.utils;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public List<Thread> tasks = new ArrayList<>();
    public void add(final Thread threadIn){
        tasks.add(threadIn);
    }
    public void remove(final int idIn){
        tasks.get(idIn).interrupt();
    }
    public void doTasks(){
        tasks.forEach(Thread::start);
    }
}
