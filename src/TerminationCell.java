public class TerminationCell extends Cell {

    public final Type type;

    public TerminationCell(int x, int y, Type type){
        super(new Position(x, y));
        this.type = type;
    }
    public enum Type{
        Begin, Destination
    }
}

