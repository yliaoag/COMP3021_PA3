public class Pokemon extends Cell{
    private String name;
    private String type;
    private int power;
    private int reqBall;

    public Pokemon(int row, int col){
        super(new Position(row, col));
    }
    public Pokemon(String name, String type, Position pos, int pwr, int balls){
        super(pos);
        this.name = name;
        this.type = type;
        this.power = pwr;
        this.reqBall = balls;
    }

    public int getReqBall() { return reqBall;}

    public int getPower() { return power;}

    public String getType(){ return type;}


}

