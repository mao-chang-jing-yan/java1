package data;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class KG {
    private List<Node> nodes; //Node就是Entity
    private List<Triple> triples;
    private final LinkedHashMap<Node, List<Node>> edges;//描述了每个节点及其相邻节点（对应与该节点相连的多条边）。可由triples生成//相当于图论中的List<Edge> edges;
    private final LinkedHashMap<Node, List<Integer>> directions; //方向
//    private LinkedHashMap<Node, List<Triple>> relationShape; //关系


    public KG() {
        this.nodes = new LinkedList<Node>();
        this.triples = new LinkedList<Triple>();
        this.edges = new LinkedHashMap<Node, List<Node>>();
        this.directions = new LinkedHashMap<Node, List<Integer>>();
    }

    public KG(List<Node> nodes, List<Triple> triples, LinkedHashMap<Node, List<Node>> edges, LinkedHashMap<Node, List<Integer>> directions) {
        this.nodes = nodes;
        this.triples = triples;
        this.edges = edges;
        this.directions = directions;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Triple> getTriples() {
        return triples;
    }

    public void setTriples(List<Triple> triples) {
        this.triples = triples;
    }

    public LinkedHashMap<Node, List<Node>> getEdges() {
        return edges;
    }

//    public void setEdges(LinkedHashMap<Node, List<Node>> edges) {
//        this.edges = edges;
//    }

    public LinkedHashMap<Node, List<Integer>> getDirections() {
        return directions;
    }

    //    获取节点的边和方向
    public Map<Node, Integer> getNodesEdgeDirections(Node node) {
        List<Node> list = this.edges.get(node);
        List<Integer> integers = this.directions.get(node);
        Map<Node, Integer> map = new LinkedHashMap<Node, Integer>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i), integers.get(i));
            }
        }

        return map;
    }

    public List<Triple> getEdgeRelationShape(Node node, Node nextNode) {
        List<Triple> t1 = new LinkedList<Triple>();
        for (Triple t2 : this.triples) {
            if (t2.getHead().equals(new Entity(node)) & t2.getTail().equals(new Entity(nextNode)))
            {
                t1.add(t2);
            }
        }
        return t1;
    }


    @Override
    public String toString() {
        return "KG{" +
                "nodes=" + nodes.toString() +
                ", triples=" + triples.toString() +
                ", edges=" + edges.toString() +
                ", directions=" + directions.toString() +
                '}';
    }

    public JSONObject toJson() {
        Map<String, Object> map1 = this.toMap();
        return new JSONObject(map1);
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("nodes", nodes);
        map1.put("triples", triples);
        map1.put("edges", edges);
        map1.put("directions", directions);
        return map1;
    }

    //    添加节点
    public void addNode(Node node, List<Node> edges, List<Integer> directions) {
        if (this.getNodesSum(node).equals(0)) {
            this.nodes.add(node);
            this.edges.put(node, edges);
            this.directions.put(node, directions);
        }
    }

    //    添加节点
    public void addNode(Node node) {
        List<Node> edges = new LinkedList<Node>();
        List<Integer> directions = new LinkedList<Integer>();
        this.addNode(node, edges, directions);
    }


    //    删除节点
    public void deleteNode(Node node) {
        this.deleteEdgeDirections(node);
        this.nodes.remove(node);
        this.edges.remove(node);
        this.directions.remove(node);
    }

    //    删除节点的所有边
    public void deleteEdgeDirections(Node node) {
//        for (Map.Entry<Node, List<Node>> entry : this.edges.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
//            if (entry.getKey().equals(node)) {
        Map<Node, Integer> nodesEdgeDirections = getNodesEdgeDirections(node);
        for (Map.Entry<Node, Integer> entry : nodesEdgeDirections.entrySet()
        ) {
            Node nextNode = entry.getKey();
            deleteEdgeDirection(nextNode, node, 0);
            deleteTriple(nextNode, node, "", 0);

        }
        if (this.edges.get(node) != null) {
            this.edges.get(node).clear();
        }
        if (this.edges.get(node) != null) {
            this.directions.get(node).clear();
        }
//            }
//        }
    }

    //    获取两个节点的其中一条边 node-->edge
    public Map<Node, Integer> getNodesEdgeIndex(Node node, Node edge) {
        Map<Node, Integer> res = new HashMap<Node, Integer>();
        for (Map.Entry<Node, List<Node>> entry : this.edges.entrySet()
        ) {
            if (entry.getKey().equals(node)) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i).equals(edge)) {
                        res.put(node, i);
                        return res;
                    }
                }
            }
        }
        return null;
    }

    //    获取某个节点的数目
    public Integer getNodesSum(Node node) {
        int SUM = 0;
        for (Node n : this.nodes) {
            if (n.equals(node)) {
                SUM++;
            }
        }
        return SUM;
    }

    //    获取某个边的数目
    public Integer getTripleSum(Triple triple) {
        int SUM = 0;
        for (Triple t : this.triples) {
            if (
//                    t.getTail().equals(triple.getTail()) &
//                            t.getHead().equals(triple.getHead())
//                            t.getRela().equals(triple.getRela())
                    t.equals(triple)

            ) {
                SUM++;
            }
        }
        return SUM;
    }


    //    添加一条边
    public void addEdgeDirection(Node node, Node edge, int direction) {
        Map<Node, Integer> nodesEdgeIndex = this.getNodesEdgeIndex(node, edge);
        if (nodesEdgeIndex == null) {
            this.edges.get(node).add(edge);
            this.directions.get(node).add(direction);
        }
    }

    //    添加一条边
    public void addTriple(Triple triple) {
        Entity headEntity = triple.getHead();
        Entity tailEntity = triple.getTail();
        Node head = new Node(headEntity);
        Node tail = new Node(tailEntity);

        addNode(head);
        addNode(tail);

        if (getTripleSum(triple) > 0) {
            return;
        }
        this.triples.add(triple);

        addEdgeDirection(head, tail, 1);
        addEdgeDirection(tail, head, 0);
    }

    //    删除节点的一条边
    public void deleteEdgeDirection(Node node, Node edge, int direction) {
        Map<Node, Integer> nodesEdgeIndex = this.getNodesEdgeIndex(node, edge);
        if (nodesEdgeIndex != null) {
            int index = nodesEdgeIndex.get(node);
            this.edges.get(node).remove(index);
            this.directions.get(node).remove(index);
        }
    }

    //    删除一条边 & 两端节点没有边时删除节点（node，nextNode, relationShape）
    public void deleteTriple(Node node, Node nextNode, String relationShape, int direction) {
        Triple triple = new Triple("", new Entity(node), new Entity(nextNode), relationShape, "");
        deleteTriple(triple);
    }

    //    删除一条边 & 两端节点没有边时删除节点
    public void deleteTriple(Triple triple) {
//        Entity headEntity = triple.getHead();
//        Entity tailEntity = triple.getTail();
//        Node head = new Node(headEntity);
//        Node tail = new Node(tailEntity);

//        deleteEdgeDirection(head, tail, 0);
//        deleteEdgeDirection(tail, head, 0);

//        Map<Node, Integer> headsEdgeDirections = getNodesEdgeDirections(head);
//        Map<Node, Integer> tailsEdgeDirections = getNodesEdgeDirections(tail);

//        if (headsEdgeDirections.size() == 0) {
//            deleteNode(head);
//        }
//        if (tailsEdgeDirections.size() == 0) {
//            deleteNode(tail);
//        }

        this.triples.remove(triple);
    }


    public void printGraph() {
        int NUM = 0;
        for (Map.Entry<Node, List<Node>> entry : this.edges.entrySet()
        ) {
            System.out.println(entry.getKey().toString());
            for (Node n : entry.getValue()
            ) {
                System.out.println("12 -  " + n.toString());
            }
            NUM = NUM + entry.getValue().size();

        }

        System.out.println("this.edges.size()      = " + this.edges.size());
        System.out.println("this.directions.size() = " + this.directions.size());

        System.out.println("this.nodes.size()      = " + this.nodes.size());

        System.out.println("this.triples.size()    = " + this.triples.size());
        System.out.println("this.edges-edge.size() = " + NUM);

    }

    public void printNodes() {

        for (Node node : this.nodes
        ) {
            System.out.println(node.toJson());

        }
//        System.out.println(this.nodes.toString());

    }

    public void printTriple() {
        for (Map.Entry<Node, List<Node>> entry : this.edges.entrySet()
        ) {
            System.out.println("1 ===> " + entry.getKey().toJson());
            System.out.println("2 ===> " + entry.getValue().size() + "  " + entry.getValue().toString());
            System.out.println("3 ===> " + this.directions.get(entry.getKey()).size() +
                    "  " + this.directions.get(entry.getKey()).toString());

        }
        System.out.println(this.edges.size());
    }


}

