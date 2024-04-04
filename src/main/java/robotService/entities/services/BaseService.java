package robotService.entities.services;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;
import robotService.entities.robot.Robot;
import robotService.entities.supplements.Supplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseService implements Service{

    private String name;
    private int capacity;
    private Collection<Supplement>supplements;
    private Collection<Robot>robots;

    public BaseService(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.supplements = new ArrayList<>();
        this.robots = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (null == name || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.SERVICE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;

    }

    @Override
    public Collection<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public void addRobot(Robot robot) {
        if (robots.size() >= this.capacity){
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_ROBOT);
        }
        robots.add(robot);

    }

    @Override
    public void removeRobot(Robot robot) {
        robots.remove(robot);

    }

    @Override
    public void addSupplement(Supplement supplement) {
        this.supplements.add(supplement);

    }

    @Override
    public void feeding() {
        robots.forEach(Robot::eating);

    }

    @Override
    public int sumHardness() {
        return supplements.stream().mapToInt(Supplement::getHardness).sum();
    }

    @Override
    public String getStatistics() {
        return String.format("%s %s:%n",this.name,
                                       this.getClass().getSimpleName())
                + String.format("Robots: %s%n",robots.isEmpty()?"none"
                    :robots.stream().map(Robot::getName).collect(Collectors.joining(" ")))
                + String.format("Supplements: %s Hardness: %s%n",this.supplements.size(),this.sumHardness()).trim();
    }
}
