import java.util.ArrayList;
import java.util.List;

public class Map {
    private final int rows;
    private final int cols;

    final Cell [][] cells;

    private TerminationCell beginCell;
    private TerminationCell DestCell;


    private List<Position> stationList = new ArrayList<>();
    private List<Position> pokemons = new ArrayList<>();

    public Map(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[this.rows][this.cols];
        this.beginCell = new TerminationCell(0,0, TerminationCell.Type.Begin);
        this.DestCell = new TerminationCell(0,0, TerminationCell.Type.Destination);
    }

    public Map(int rows, int cols, Cell[][] cells){

        this.cells = cells;

        this.rows = rows;
        this.cols = cols;

        for (int i=0; i < this.rows; i++){
            for (int j=0; j < this.cols; j++){

                if (cells[i][j] instanceof TerminationCell){
                    TerminationCell tmp = (TerminationCell)cells[i][j];
                    if (tmp.type == TerminationCell.Type.Begin){
                        if (beginCell != null){
                            throw new IllegalArgumentException();
                        }else{
                            beginCell = tmp;
                        }
                    }
                    else if (tmp.type == TerminationCell.Type.Destination){
                        if (DestCell != null ){
                            throw new IllegalArgumentException();
                        }else {
                            DestCell = tmp;
                        }
                    }
                }
            }
        }
        if (beginCell == null || DestCell == null) {
            throw new IllegalArgumentException();
        }
    }

    public void setBeginCell(TerminationCell beginCell) {
        this.beginCell = beginCell;
    }

    public void setDestCell(TerminationCell destCell){
        this.DestCell = destCell;
    }

    public int getRows(){ return rows;}

    public int getCols(){ return cols;}

    public Position getBeginPos(){ return beginCell.position;}

    public Position getDestPos() { return DestCell.position; }

    public void addPokemon(Position pokemon) {

        pokemons.add(pokemon);

    }

    public int getNumPokemons(){ return pokemons.size(); }

    public void addStation(Position supply){
        stationList.add(supply);
    }
    public int getNumStation(){ return stationList.size(); }

    public List<Position> getPokemons(){ return pokemons;}

    public List<Position> getStationList() { return stationList; }

}

