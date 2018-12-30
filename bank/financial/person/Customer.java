package financial.person;

import financial.bank.Bank;

public class Customer {

  private String name;
  private int birthYear;
  private Bank bank;
  private int amount;

  private Customer(String name, int birthYear, Bank bank) {
    this.name = name;
    this.birthYear = birthYear;
    this.bank = bank;
    this.amount = 0;
  }

  public static Customer makeCustomer (String name, int birthYear, String bankName) {

    Bank bank;
    try{
      bank = Bank.valueOf(bankName);
      String[] splittedName = name.split(" ");
      if((splittedName.length >= 2 && splittedName.length <= 4) && (birthYear >= 1918 && birthYear <= 1998)) {
        for(String namePart : splittedName) {
          if(!(namePart.matches("\\b([A-Z][a-z]{3,})\\b"))) {
              return null;
          }
        }
      } else {
        return null;
      }
    } catch(IllegalArgumentException e) {
      return null;
    }
    Customer newCustomer = new Customer(name, birthYear, bank);
    return newCustomer;
  }

  public String getName(){
    return this.name;
  }

  public Bank getBank(){
    return this.bank;
  }

  public int getAmount(){
    return this.amount;
  }

  public void decreaseAmount(int decrease){
    this.amount -= decrease;
  }

  public void increaseAmount(int increase){
    this.amount += increase;
  }

  public String toString(){
    return this.name + ": " + this.amount;
  }

}
