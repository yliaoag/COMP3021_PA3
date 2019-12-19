import java.util.ArrayList;
import java.util.List;

public class Player {
    private Position position;
    private int numBalls;
    private List<Pokemon> pokemonList = new ArrayList<>();
    private List<Station> stationList = new ArrayList<>();
    public Path path = new Path();

    public Player(Position start){
        position = start;
        path.add(new Position(start.row, start.col));
        numBalls = 0;
    }

    public void setPosition(Position currPos){
        this.position.row = currPos.row;
        this.position.col = currPos.col;
    }

    public Position getPosition(){
        return this.position;
    }

    public boolean addPokemon(Pokemon pokemon) {
        if (numBalls >= pokemon.getReqBall() && !pokemonList.contains(pokemon)){
            pokemonList.add(pokemon);
            numBalls -= pokemon.getReqBall();
            return true;
        }
        else return false;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void addStep(Position newStep){
        path.add(newStep);
    }

    public void addBall(Station supply){
        if (!stationList.contains(supply)){
            stationList.add(supply);
            numBalls += supply.getNumBalls();
        }
    }

    public int getNumBalls() {
        return numBalls;
    }

    public String totalScore(){ return Integer.toString(getNumBalls() + pokemonList.size()*5 + getNumType()*10 + getMaxPower() - (path.size()-1));}

    public String scoreboard(){ return getNumBalls()+":"+pokemonList.size()+":"+getNumType()+":"+getMaxPower();}



    public int getNumType(){
        List<String> type = new ArrayList<String>();
        for (Pokemon item : pokemonList){
            if (!type.contains(item.getType())){
                type.add(item.getType());
            }
        }
        return type.size();
    }
    public int getMaxPower(){
        int max = 0;
        for (Pokemon item: pokemonList){
            if (item.getPower() > max) max = item.getPower();
        }
        return max;
    }
}

