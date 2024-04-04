package robotService.core;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;
import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;

import robotService.repositories.SupplementRepository;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{

    private SupplementRepository supplements;
    private Map<String, Service> services;

    public ControllerImpl() {
        this.supplements = new SupplementRepository();
        this.services = new HashMap<>();
    }

    @Override
    public String addService(String type, String name) {

        switch (type){
            case "MainService":
                services.put(name,new MainService(name));
                break;
            case "SecondaryService":
                services.put(name,new SecondaryService(name));
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_SERVICE_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SERVICE_TYPE,type);
    }

    @Override
    public String addSupplement(String type) {

        switch (type){
            case "PlasticArmor":
                this.supplements.addSupplement(new PlasticArmor());
                break;
            case  "MetalArmor":
                this.supplements.addSupplement(new MetalArmor());
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE,type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Supplement neededSupplement = supplements.findFirst(supplementType);
        if (neededSupplement == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND,supplementType));
        }
        Service service = this.services.get(serviceName);
        service.addSupplement(neededSupplement);
        this.supplements.removeSupplement(neededSupplement);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE,supplementType,serviceName);
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        Service service = this.services.get(serviceName);
        boolean isSuitable = service.getClass().getSimpleName()
                .equalsIgnoreCase("MainService" )
                && robotType.equalsIgnoreCase("MaleRobot")
                || service.getClass().getSimpleName()
                .equalsIgnoreCase("SecondaryService")
                && robotType.equalsIgnoreCase("FemaleRobot");

        Robot robot;

        switch (robotType){
            case "MaleRobot":
                robot = new MaleRobot(robotName,robotType, price);
                break;
            case "FemaleRobot":
                robot = new FemaleRobot(robotName,robotType,price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_ROBOT_TYPE);
        }
        if (!isSuitable){
            return String.format(ConstantMessages.UNSUITABLE_SERVICE);
        }
        service.addRobot(robot);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE,robotType,serviceName);
    }

    @Override
    public String feedingRobot(String serviceName) {
        Service service = services.get(serviceName);
        service.feeding();
        return String.format(ConstantMessages.FEEDING_ROBOT,service.getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {
        Service service = services.get(serviceName);
        double robotsPriceSum = service.getRobots().stream()
                .mapToDouble(Robot::getPrice).sum();
        double supplementsPriceSum = service.getSupplements().stream()
                .mapToDouble(Supplement::getPrice).sum();
        return String.format(ConstantMessages.VALUE_SERVICE,
                                                serviceName,
                                                robotsPriceSum + supplementsPriceSum);
    }

    @Override
    public String getStatistics() {
        return services.values().stream()
                .map(Service::getStatistics)
                .collect(Collectors.joining(System.lineSeparator())).trim();
    }
}
