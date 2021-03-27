import data.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class java1 {
    public void main1(String[] args) {
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

        long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        System.out.println("开始 " + startMili + "\n");

        KG kg = new KG();
        int num = 0;

        int threadNUM = 1;
        int size;

        List<String[]> strings = File.readCSV("/Users/xiaoshen/IdeaProjects/java1/src/file/personrelkg.csv");

        size = strings.size();
        size = 1000;
        int l = size / threadNUM;
        for (int j = 0; j < threadNUM; j++) {
            int m = l * j;
            int n = m + l;
            if (j == threadNUM - 1){
                n = size;
            }
            List<String[]> s = strings.subList(m,n);
            MyThread myThread =  new MyThread(kg, s) {
                @Override
                public void run1() {
                    for (int i = 0; i < this.a.size(); i++) {
                        String parentName = this.a.get(i)[3];
                        String nextName = this.a.get(i)[0];
                        String relationShape = this.a.get(i)[1];
                        System.out.print(i + " - " + parentName + " - " + nextName + " - " + relationShape + "\r");
                        int weight = 0;
//            Entity entity = new Entity("", "", "", "");
                        Entity head = new Entity(parentName, "", "", "");
                        Entity tail = new Entity(nextName, "", "", "");
//            kg.addNode(new Node(head));
//            kg.addNode(new Node(tail));
                        Triple triple = new Triple("", head, tail, relationShape, "");

                        this.kg.addTriple(triple);
                    }
                }
            };
            myThread.start();
        }



//        kg.printNodes();
        kg.printGraph();
//        kg.printTriple();

        long endMili = System.currentTimeMillis();
//        System.out.println("结束 s"+endMili);
        System.out.println("\n\n总耗时为：" + (endMili - startMili) + "毫秒");
    }


}
