import data.*;

import java.text.MessageFormat;
import java.util.*;

public class java1 {
    public void main12(String[] args) {
        long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        System.out.println("开始 " + startMili + "\n");

        KG kg = new KG();
        int num = 0;

        List<String[]> strings = File.readCSV("/Users/xiaoshen/IdeaProjects/java1/src/file/personrelkg.csv");


        for (String[] s : strings
        ) {
            if (num > 20000) {
                break;
            }
            num++;
            String parentName = MessageFormat.format("{3}", (Object[]) s);
            String nextName = MessageFormat.format("{0}", (Object[]) s);
            String relationShape = MessageFormat.format("{2}", (Object[]) s);
            System.out.print(num + " - " + parentName + " - " + nextName + " - " + relationShape + "\r");
            int weight = 0;
//            Entity entity = new Entity("", "", "", "");
            Entity head = new Entity(parentName, "", "", "");
            Entity tail = new Entity(nextName, "", "", "");
//            kg.addNode(new Node(head));
//            kg.addNode(new Node(tail));
            Triple triple = new Triple("", head, tail, relationShape, "");

            kg.addTriple(triple);

            if (num < 10000) {
//                kg.deleteNode(new Node(head));
                kg.deleteEdgeDirections(new Node(head));
//                kg.deleteTriple(triple);
//                System.out.println(head.getEntityName());

            }

        }

//        kg.printNodes();
        kg.printGraph();
//        kg.printTriple();

        long endMili = System.currentTimeMillis();
//        System.out.println("结束 s"+endMili);
        System.out.println("\n\n总耗时为：" + (endMili - startMili) + "毫秒");
    }

    public static void main(String[] args) throws InterruptedException {

//        testMyRun();
        testThr();


    }

    public static void testThr() throws InterruptedException {
        long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        System.out.println("开始 " + startMili + "\n");

        List<String[]> strings = File.readCSV("/Users/xiaoshen/IdeaProjects/java1/src/file/personrelkg.csv");
        List<Thread> threadList = new LinkedList<Thread>();

        KG kg = new KG();
        int threadNUM = 10;
        int size = strings.size();
        size = 1000;
        for (int i = 0; i < threadNUM; i++) {
            int ld = size / threadNUM;
            int m = ld * i;
            int n = m + ld;
            if (i == threadNUM - 1) {
                n = size;
            }
            List<String[]> a = strings.subList(m, n);

            MyRunnable myRunnable = new MyRunnable() {
                @Override
                public void run1() {
                    int num = 0;
                    for (String[] s : a
                    ) {

                        if (s.length < 4){
                            continue;
                        }
                        num++;
                        String parentName = s[3];
                        String nextName = s[0];
                        String relationShape = s[1];
            System.out.print("线程 - " + this.getName() + "  " + num +" " + parentName + " - " + nextName + " - " + relationShape + "\n");
//                        int weight = 0;
//            Entity entity = new Entity("", "", "", "");
                        Entity head = new Entity(parentName, "", "", "");
                        Entity tail = new Entity(nextName, "", "", "");
//            kg.addNode(new Node(head));
//            kg.addNode(new Node(tail));
                        Triple triple = new Triple("", head, tail, relationShape, "");

                        synchronized (MyRunnable.class) {
                            kg.addTriple(triple);
                        }
                    }
                }
            };
            myRunnable.setName("thread-" + i);
            Thread t = new Thread(myRunnable);
            t.start();
            threadList.add(t);
        }

        for (Thread t : threadList) {
            t.join();
        }


//        kg.printNodes();
//        kg.printGraph();
//        kg.printTriple();
        System.out.println(kg.toJson().get("directions"));

        long endMili = System.currentTimeMillis();
//        System.out.println("结束 s"+endMili);
        System.out.println("\n\n总耗时为：" + (endMili - startMili) + " 毫秒");
        System.out.println("总耗时为：" + (endMili - startMili) / 1000 + " 秒");
    }

    public static void testMyRun() {
        List<String[]> s = new LinkedList<String[]>();
        String[] a = {"12323", "1234"};
        s.add(a);
        s.add(a);
        s.add(a);
        s.add(a);


        KG kg = new KG();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("kg", kg);
        data.put("0", s);

        MyRunnable myThread = new MyRunnable(data) {
            @Override
            public void run1() {
//                if(this.data instanceof HashMap){

                @SuppressWarnings("unchecked")
                List<String[]> s = (List<String[]>) this.getDataItem("0");

                KG kg1 = (KG) this.getDataItem("kg");
                Node node = new Node("", "45645", "", "");
                kg.addNode(node);

                System.out.println(a[0]);

//                }


            }
        };


        Thread thread = new Thread(myThread);

        thread.start();

        thread = new Thread(myThread);
        thread.start();

        kg.printGraph();

    }

}
