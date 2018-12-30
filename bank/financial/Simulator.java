package financial;

import financial.bank.ATM;
import financial.bank.Bank;
import financial.person.Customer;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

public class Simulator {

  private ATM atm;
  private ArrayList<Customer> customers;
  private PrintWriter pwLog;

  public Simulator(String bankName, int initAmount, String outputFileName) throws FileNotFoundException {
      this.atm = ATM.makeATM(bankName, initAmount);
      this.customers = new ArrayList<>();
      this.pwLog = new PrintWriter(outputFileName);
      pwLog.flush();
  }

  private Customer getCustomerbyName(String customersName) {
    int i = 0;
    for( ; i < customers.size(); ++i) {
      if(customersName.equals(customers.get(i).getName())) {
        return customers.get(i);
      }
    }
    return null;
  }

  public void insertCustomer(String customerName, int birthYear, String bankName) {
    Customer oneCustomer = getCustomerbyName(customerName);
    if(oneCustomer == null) {
      Customer newCustomer = Customer.makeCustomer(customerName, birthYear, bankName);
      if(!(newCustomer == null)) {
        customers.add(newCustomer);
      }
    }
  }

  public void withdrawCash(String customerName, int amount) {
    Customer oneCustomer = getCustomerbyName(customerName);
    int feeOfAmount = 0;
    feeOfAmount = atm.calculateFee(oneCustomer.getBank(), amount);
    int amountPlusFee = 0;
    amountPlusFee = amount + feeOfAmount;
    if(oneCustomer != null && oneCustomer.getAmount() >= amountPlusFee && atm.getAmount() > amount && amount > 0) {
      atm.decreaseAmount(amount);
      oneCustomer.decreaseAmount(amountPlusFee);
      pwLog.flush();
      pwLog.println(oneCustomer.toString());
    }
  }

  public void depositCash(String customerName, int amount) {
    if(getCustomerbyName(customerName) != null && amount > 0) {
      atm.increaseAmount(amount);
      getCustomerbyName(customerName).increaseAmount(amount);
      pwLog.flush();
      pwLog.println(getCustomerbyName(customerName).toString());
    }
  }

  public void simulate(String inputFileName) throws Exception {
    try(Scanner sc = new Scanner(new FileReader(inputFileName))) {
      while(sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] splittedLine = line.split(":");
        if(splittedLine.length == 2) {
          String[] splittedHalfLine = splittedLine[1].split(",");
          switch(splittedLine[0]) {
            case("REG") :
              if(splittedHalfLine.length == 3) {
                try {
                  insertCustomer(splittedHalfLine[0], Integer.parseInt(splittedHalfLine[1]), splittedHalfLine[2]);
                } catch (Exception e) {
                  break;
                }
              }
              break;
            case("GET") :
              if(splittedHalfLine.length == 2) {
                try {
                  withdrawCash(splittedHalfLine[0], Integer.parseInt(splittedHalfLine[1]));
                } catch (Exception e) {
                  break;
                }
              }
              break;
            case("PUT") :
              if(splittedHalfLine.length == 2) {
                try {
                  depositCash(splittedHalfLine[0], Integer.parseInt(splittedHalfLine[1]));
                } catch (Exception e) {
                  break;
                }
              }
              break;
          }
        }
      }
      pwLog.flush();
      sc.close();
    }
  }

  public void close() {
    pwLog.close();
  }

}
