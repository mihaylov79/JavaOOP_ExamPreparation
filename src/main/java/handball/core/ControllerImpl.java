package handball.core;

import handball.common.ConstantMessages;
import handball.common.ExceptionMessages;
import handball.entities.equipment.ElbowPad;
import handball.entities.equipment.Equipment;
import handball.entities.equipment.Kneepad;
import handball.entities.gameplay.Gameplay;
import handball.entities.gameplay.Indoor;
import handball.entities.gameplay.Outdoor;
import handball.entities.team.Bulgaria;
import handball.entities.team.Germany;
import handball.entities.team.Team;
import handball.repositories.EquipmentRepository;
import handball.repositories.Repository;

import java.util.HashMap;
import java.util.Map;

public class ControllerImpl implements Controller{


    private Map<String,Gameplay> gameplays;
    private Repository equipment;

    public ControllerImpl() {
        this.gameplays = new HashMap<>();
        this.equipment = new EquipmentRepository();
    }

    @Override
    public String addGameplay(String gameplayType, String gameplayName) {

        switch (gameplayType){
            case "Outdoor":
                gameplays.put(gameplayName, new Outdoor(gameplayName));
                break;
            case "Indoor":
                gameplays.put(gameplayName, new Indoor(gameplayName));
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_GAMEPLAY_TYPE);

        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_GAMEPLAY_TYPE, gameplayType);
    }

    @Override
    public String addEquipment(String equipmentType) {

        switch (equipmentType){
            case "ElbowPad":
             equipment.add(new ElbowPad());
             break;
            case "Kneepad":
             equipment.add(new Kneepad());
             break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_EQUIPMENT_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_EQUIPMENT_TYPE, equipmentType);
    }

    @Override
    public String equipmentRequirement(String gameplayName, String equipmentType) {
        Equipment byType = this.equipment.findByType(equipmentType);
        if (byType == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_EQUIPMENT_FOUND,equipmentType));
        }

        this.equipment.remove(byType);
        this.gameplays.get(gameplayName).addEquipment(byType);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_EQUIPMENT_IN_GAMEPLAY, equipmentType,gameplayName);
    }

    @Override
    public String addTeam(String gameplayName, String teamType, String teamName, String country, int advantage) {
        Gameplay gameplay = gameplays.get(gameplayName);
        boolean isOK = gameplay.getClass().getSimpleName().equalsIgnoreCase("Outdoor") && teamType.equalsIgnoreCase("Bulgaria")
                || gameplay.getClass().getSimpleName().equalsIgnoreCase("Indoor") && teamType.equalsIgnoreCase("Germany");

        switch (teamType){
            case "Bulgaria":
                if (!isOK){
                    return ConstantMessages.GAMEPLAY_NOT_SUITABLE;
                }else {
                    gameplays.get(gameplayName).addTeam(new Bulgaria(teamName,country,advantage));
                }
                break;
            case "Germany":
                if (!isOK){
                    return ConstantMessages.GAMEPLAY_NOT_SUITABLE;
                }else {
                    gameplays.get(gameplayName).addTeam(new Germany(teamName,country,advantage));
                }
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_TEAM_TYPE);

        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TEAM_IN_GAMEPLAY,teamType,gameplayName);
    }

    @Override
    public String playInGameplay(String gameplayName) {
        Gameplay game = gameplays.get(gameplayName);
        game.teamsInGameplay();
        return String.format(ConstantMessages.TEAMS_PLAYED, game.getTeam().size());
    }

    @Override
    public String percentAdvantage(String gameplayName) {
       int advantageSum = gameplays.get(gameplayName).getTeam().stream().mapToInt(Team::getAdvantage).sum();

        return String.format(ConstantMessages.ADVANTAGE_GAMEPLAY,gameplayName,advantageSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        this.gameplays.values().forEach(sb::append);
        return sb.toString().trim();
    }
}
