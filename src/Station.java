public class Station extends Cell{
    private int numBalls;

    public Station(int row, int col){
        super(new Position(row, col));
    }

    public Station(Position pos, int num){
        super(pos);
        this.numBalls = num;
    }

    public int getNumBalls(){
        return this.numBalls;
    }
}


