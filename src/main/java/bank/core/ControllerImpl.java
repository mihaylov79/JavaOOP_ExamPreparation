package bank.core;

import bank.common.ConstantMessages;
import bank.common.ExceptionMessages;
import bank.entities.bank.Bank;
import bank.entities.bank.BranchBank;
import bank.entities.bank.CentralBank;
import bank.entities.client.Adult;
import bank.entities.client.Client;
import bank.entities.client.Student;
import bank.entities.loan.Loan;
import bank.entities.loan.MortgageLoan;
import bank.entities.loan.StudentLoan;
import bank.repositories.LoanRepository;
import bank.repositories.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{

    private LoanRepository loans;
    private Map<String , Bank> banks;

    public ControllerImpl() {
        this.loans = new LoanRepository();
        this.banks = new HashMap<>();
    }

    @Override
    public String addBank(String type, String name) {
        Bank bank;
        switch (type){
            case "CentralBank":
                bank = new CentralBank(name);
                break;
            case "BranchBank":
                bank = new BranchBank(name);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_BANK_TYPE);
        }
        banks.put(name,bank);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_BANK_OR_LOAN_TYPE,type);
    }

    @Override
    public String addLoan(String type) {
        Loan loan ;
        switch (type){
            case "StudentLoan":
                loan = new StudentLoan();
                break;
            case "MortgageLoan":
                loan = new MortgageLoan();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_LOAN_TYPE);
        }
        loans.addLoan(loan);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_BANK_OR_LOAN_TYPE,type);
    }

    @Override
    public String returnedLoan(String bankName, String loanType) {
        Bank bank = this.banks.get(bankName);
       Loan neededLoan = this.loans.findFirst(loanType);
       if (neededLoan == null){
           throw new IllegalArgumentException(String.format(ExceptionMessages.NO_LOAN_FOUND,loanType));
       }
       bank.addLoan(neededLoan);
       loans.removeLoan(neededLoan);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CLIENT_OR_LOAN_TO_BANK,loanType,bankName);
    }

    @Override
    public String addClient(String bankName, String clientType, String clientName, String clientID, double income) {
        Bank bank = this.banks.get(bankName);
        boolean isSuitable = bank.getClass().getSimpleName().equals("BranchBank") && clientType.equals("Student")
                || bank.getClass().getSimpleName().equals("CentralBank") && clientType.equals("Adult");
        Client client;
        switch (clientType){
            case "Student":
                client = new Student(clientName,clientID,income);
                break;
            case "Adult":
                client = new Adult(clientName,clientID,income);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_CLIENT_TYPE);
        }
        if (!isSuitable){
            return String.format(ConstantMessages.UNSUITABLE_BANK);
        }else{
            bank.addClient(client);
        }
        //bank.addClient(client);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CLIENT_OR_LOAN_TO_BANK,clientType,bankName);
    }

    @Override
    public String finalCalculation(String bankName) {
        Bank bank = this.banks.get(bankName);
        double loansAmount = bank.getLoans().stream().mapToDouble(Loan::getAmount).sum();
        double clientsAmount = bank.getClients().stream().mapToDouble(Client::getIncome).sum();
        return String.format(ConstantMessages.FUNDS_BANK,bankName,loansAmount + clientsAmount);
    }

    @Override
    public String getStatistics() {

        return this.banks.values().stream().map(Bank::getStatistics).collect(Collectors.joining(System.lineSeparator())).trim();
    }
}
