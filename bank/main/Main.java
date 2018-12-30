package main;

import financial.Simulator;
import financial.bank.Bank;
import financial.bank.ATM;
import financial.person.Customer;

public class Main {

  public static void main(String[] args) throws Exception {

    String bankName = args[0];
    String inputFile = args[1];
    String outputFile = args[2];

    if(args.length != 3) {
      System.out.println("There are not three parameters, please try again");
      System.exit(0);
    }else {
      try{
        Simulator simulator = new Simulator(bankName, 1000000, outputFile);
        simulator.simulate(inputFile);
        simulator.close();
      }catch(Exception e){
        System.out.println("Error: " + e);
      }
    }
  }

}
