package handball.entities.team;

public class Germany extends BaseTeam{

    public static final int DEFAULT_ADVANTAGE_INCREASED = 145;
    public Germany(String name, String country, int advantage) {
        super(name, country, advantage);
    }


    @Override
    public void play() {
        this.setAdvantage(this.getAdvantage() + DEFAULT_ADVANTAGE_INCREASED);
    }
}
