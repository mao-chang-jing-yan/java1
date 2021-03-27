import data.Entity;
import data.KG;
import data.Triple;

import java.util.LinkedList;
import java.util.List;

public abstract class MyThread extends Thread {
    public KG kg;
    public List<String[]> a;


    MyThread(KG kg, List<String[]> a) {
        this.kg = kg;
        this.a = a;
    }

    public void run() {
        System.out.println("Throwing in " + "MyThread");
//        throw new RuntimeException();
        run1();
    }

    public abstract void run1();
}

class MyT implements Runnable {

    private String name;
    private int num = 0;
    private int start = 0;
    private int end = 0;

    private void setName(String th1) {
        this.name = th1;
    }

    public KG kg;
    public List<String[]> a;


    MyT(KG kg, List<String[]> a) {
        this.kg = kg;
        this.a = a;
    }

    MyT() {
    }

    @Override
    public void run() {
        num++;
        System.out.println("线程 - " + name + " " + start + " " + end);
        for (int i = start; i < this.end; i++) {
            String parentName = this.a.get(i)[3];
            String nextName = this.a.get(i)[0];
            String relationShape = this.a.get(i)[1];
            System.out.print("线程 - " + name + " - " + i + " " + parentName + " - " + nextName + " - " + relationShape + "\n");
            int weight = 0;
//            Entity entity = new Entity("", "", "", "");
            Entity head = new Entity(parentName, "", "", "");
            Entity tail = new Entity(nextName, "", "", "");
//            kg.addNode(new Node(head));
//            kg.addNode(new Node(tail));
            Triple triple = new Triple("", head, tail, relationShape, "");

            synchronized (MyT.class) {
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
        int threadNUM = 10;
        size = 100;
        int l = size / threadNUM;
        for (int j = 0; j < threadNUM; j++) {
            int m = l * j;
            int n = m + l;
            if (j == threadNUM - 1) {
                n = size;
            }

            MyT myT = new MyT(kg, strings);
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
