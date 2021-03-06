package datastructures;
import java.util.ArrayList;
import java.util.List;

public class Chain {
    private List<GEdge> chain;

    public Chain () {
        chain = new ArrayList<>();
    }

    public List<GEdge> getChain() {
        return chain;
    }

    public void addEdge(GEdge e) {
        chain.add(e);
    }

    public GEdge getEdge(int i) {
        return chain.get(i);
    }

    public int getSize() {
        return chain.size();
    }

    @Override
    public String toString() {
        String s = "Chain{";
        for(GEdge e : chain) {
            s += "(" + (e.getA().getI()+1) + "," + (e.getB().getI()+1) + ") ";
        }
        s += "}";
        return  s;
    }
}
