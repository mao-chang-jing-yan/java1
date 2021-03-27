import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class File {
        public static List<String[]> readCSV(String path){
            List<String[]> res = new LinkedList<String[]>();
            try {
                //(文件完整路径),编码格式
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));//GBK
//                 reader.readLine();//显示标题行,没有则注释掉
//                 System.out.println(reader.readLine());
                String line = null;
                while((line=reader.readLine())!=null){
                    String[] item = line.split(",");//CSV格式文件时候的分割符,我使用的是,号
                    res.add(item);

//                    String last = item[item.length-1];//CSV中的数据,如果有标题就不用-1
//                    System.out.println(item.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return res;

        }

    public static void main(String[] args) {
        List<String[]> strings = File.readCSV("/Users/xiaoshen/IdeaProjects/java1/src/file/personrelkg.csv");
        for (String[] s: strings
             ) {
            System.out.println(s[0] + ",  " + s[1]);

        }
    }
}
