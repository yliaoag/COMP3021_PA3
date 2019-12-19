import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private List<Position> path = new ArrayList<>();
    public Path(){}

    public void add(Position newPos){
        path.add(newPos);
    }

    public int size(){ return path.size();}
    public List<Position> getPath(){ return path;}
    public boolean contain(Position pos) { return path.contains(pos); }
    public String display() {
        String str = "";
        String string = "";
        for (Position item : path) {
            //System.out.println(item.row);
            //System.out.println(item.col);
            string = "<" + item.row + "," + item.col + ">";
            str += string;
            if (path.indexOf(item)!=path.size()-1) str += "->";
        }
        return str;
    }
    public void reverse(){
        Collections.reverse(path);
    }
    public void clear(){ path.clear();}

}

