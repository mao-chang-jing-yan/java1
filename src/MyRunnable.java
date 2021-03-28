import data.Entity;
import data.KG;
import data.Triple;
import jdk.nashorn.internal.objects.annotations.Function;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.State.TERMINATED;

public abstract class MyRunnable implements Runnable {
    private Map<String ,Object> data;
    private String name = "";

    public void setName(String name) {
        this.name = name;
    }

    MyRunnable(Map<String ,Object> data) {
        this.data = data;
    }
    MyRunnable() { }

    MyRunnable(Map<String ,Object> data, String name) {
        this.data = data;
        this.name = name;
    }
    public void addData(String name, Object value){
        this.data.put(name, value);
    }
    public Object getData(){
        return this.data;
    }
    public Object getDataItem(String name){
        return this.data.get(name);
    }

    public void run() {
        System.out.println("Thread - " + name + " Throwing in " + "MyThread");
//        throw new RuntimeException();
            run1();
    }

    public abstract void run1();

    public static Map<String, String> getMapStringStringObjectToMap(Object obj) throws IllegalAccessException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Map<String, String> cast = (Map<String, String>) (obj);
        System.out.println(cast.get("0"));

        return cast;
    }

    public String getName(){
        return this.name;
    };
}

class MyR implements Runnable {

    private String name;
    private int num = 0;
    private int start = 0;
    private int end = 0;

    private void setName(String th1) {
        this.name = th1;
    }

    public KG kg;
    public List<String[]> a;


    MyR(KG kg, List<String[]> a) {
        this.kg = kg;
        this.a = a;
    }

    MyR() {
    }

    @Override
    public void run() {
        num++;
        System.out.println("线程 - " + name + " " + start + " " + end);
        for (int i = start; i < this.end; i++) {
            String parentName = this.a.get(i)[3];
            String nextName = this.a.get(i)[0];
            String relationShape = this.a.get(i)[1];
//            System.out.print("线程 - " + name + " - " + i + " " + parentName + " - " + nextName + " - " + relationShape + "\n");
            int weight = 0;
//            Entity entity = new Entity("", "", "", "");
            Entity head = new Entity(parentName, "", "", "");
            Entity tail = new Entity(nextName, "", "", "");
//            kg.addNode(new Node(head));
//            kg.addNode(new Node(tail));
            Triple triple = new Triple("", head, tail, relationShape, "");

            synchronized (MyR.class) {
                this.kg.addTriple(triple);
            }
        }
    }

    public void setStartEnd(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws InterruptedException {
        KG kg = new KG();
        List<String[]> strings = File.readCSV("/Users/xiaoshen/IdeaProjects/java1/src/file/personrelkg.csv");
        List<Thread> list = new LinkedList<Thread>();

        int size = strings.size();
        int threadNUM = 50;
        size = 10009;
        int l = size / threadNUM;
        for (int j = 0; j < threadNUM; j++) {
            int m = l * j;
            int n = m + l;
            if (j == threadNUM - 1) {
                n = size;
            }

            MyR myT = new MyR(kg, strings);
//            myT.setName("th1");
            myT.setStartEnd(m, n);
            myT.setName("" + j);
            Thread thread = new Thread(myT);
            thread.start();
            list.add(thread);
        }

        for (Thread t : list
        ) {
            t.join();
        }
        kg.printGraph();


//        Thread thread = new Thread(myT, "uihu");
//        thread.start();
//        thread = new Thread(myT, "uihu");
//        thread.start();
    }

}

class MyThreads{
    private final List<Thread> threadList;
    private final Map<Thread , Object> threadMap;

    public MyThreads() {
        this.threadMap = new HashMap<Thread, Object>();
        this.threadList = new LinkedList<Thread>();
    }
    public Thread addThread(ThreadFun f){
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
    public void addThread(ThreadFun f, int num){
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
        ThreadFun threadFun = new ThreadFun() {
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
abstract class ThreadFun{
    public ThreadFun() { }
    public abstract void fun();
}