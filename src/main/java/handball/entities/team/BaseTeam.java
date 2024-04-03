package handball.entities.team;

import handball.common.ExceptionMessages;

public abstract class BaseTeam implements Team{

    public static final int DEFAULT_ADVANTAGE_INCREASED = 0;

    private String name;
    private String country;
    private int advantage;


    public BaseTeam(String name, String country, int advantage) {
        this.setName(name);
        this.setCountry(country);
        this.setAdvantage(advantage);
    }

    @Override
    public void setName(String name) {
        if (null == name || name.trim().isEmpty() ){
            throw new NullPointerException(ExceptionMessages.TEAM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }


    private void setCountry(String country) {
        if (null == country || country.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.TEAM_COUNTRY_NULL_OR_EMPTY);
        }
        this.country = country;
    }

    protected void setAdvantage(int advantage) {
        if (advantage <= 0){
            throw new IllegalArgumentException(ExceptionMessages.TEAM_ADVANTAGE_BELOW_OR_EQUAL_ZERO);
        }
        this.advantage = advantage;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAdvantage() {
        return this.advantage;
    }
}
