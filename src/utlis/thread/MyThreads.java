package utlis.thread;

import data.KG;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.State.TERMINATED;

public class MyThreads{
    private final List<Thread> threadList;
    private final Map<Thread , Object> threadMap;

    public MyThreads() {
        this.threadMap = new HashMap<Thread, Object>();
        this.threadList = new LinkedList<Thread>();
    }
    public Thread addThread(ThreadFunc f){
        MyRunnable myRunnable =  new MyRunnable() {
            @Override
            public void run1() {
                f.fun();
            }
        };
        myRunnable.setName(threadList.size()+"");
        Thread thread = new Thread(myRunnable);
        this.threadList.add(thread);
        return thread;
    }
    public void addThread(ThreadFunc f, int num){
        for (int i = 0; i < num; i++) {
            addThread(f);
        }
    }

    public void runWithJoin(){
        for (Thread t: this.threadList
        ) {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run(){
        for (Thread t: this.threadList
        ) {
            t.start();
        }
    }

    public void rmThreads(){
        this.threadList.removeIf(t -> t.getState() == TERMINATED);
    }



    public static void main(String[] args) {

        KG kg = new KG();
        MyThreads myThreads = new MyThreads();
        ThreadFunc threadFun = new ThreadFunc() {
            @Override
            public void fun() {
//                kg.printGraph();
//                System.out.println(kg);
                System.out.println(kg.toJson());
            }
        };
        myThreads.addThread(threadFun, 100);
        myThreads.runWithJoin();
        myThreads.rmThreads();
    }

}
