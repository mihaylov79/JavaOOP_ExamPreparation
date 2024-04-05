package bank.entities.bank;

import bank.common.ConstantMessages;
import bank.common.ExceptionMessages;
import bank.entities.client.Client;
import bank.entities.loan.Loan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseBank implements Bank{

    private String name;
    private int capacity;
    private Collection<Loan>loans;
    private Collection<Client> clients;

    public BaseBank(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.loans = new ArrayList<>();
        this.clients = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (null == name || name.trim().isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.BANK_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;

    }

    @Override
    public Collection<Client> getClients() {
        return this.clients;
    }

    @Override
    public Collection<Loan> getLoans() {
        return this.loans;
    }

    @Override
    public void addClient(Client client) {
        if (this.clients.size() >= this.capacity){
            throw new IllegalStateException(ExceptionMessages.NOT_ENOUGH_CAPACITY_FOR_CLIENT);
        }
        clients.add(client);

    }

    @Override
    public void removeClient(Client client) {
        clients.remove(client);

    }

    @Override
    public void addLoan(Loan loan) {
        loans.add(loan);

    }

    @Override
    public int sumOfInterestRates() {
        return loans.stream().mapToInt(Loan::getInterestRate).sum();
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s, Type: %s",this.name,getClass().getSimpleName()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Clients: %s", this.clients.isEmpty()
                                ? "none" : clients.stream().map(Client::getName)
                                .collect(Collectors.joining(", "))).trim());
        sb.append(System.lineSeparator());
        sb.append(String.format("Loans: %s, Sum of interest rates: %s",loans.size(),this.sumOfInterestRates()));
        //sb.append(System.lineSeparator());
        return sb.toString();
    }
}
