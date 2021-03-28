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

    public static void main(String[] args) {
        List<String[]> s = new LinkedList<String[]>();
        String[] a = {"12323", "1234"};
        s.add(a);
        s.add(a);
        s.add(a);
        s.add(a);


        MyRunnable myThread = new MyRunnable(s) {
            @Override
            public void run1() {
//                if(this.data instanceof HashMap){
                @SuppressWarnings("unchecked")
                List<String[]> s = (List<String[]>) this.data;

//                }


            }
        };
        Thread thread = new Thread(myThread);
        thread.start();


        thread = new Thread(myThread);
        thread.start();
    }


}
