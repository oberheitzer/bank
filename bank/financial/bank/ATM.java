package financial.bank;

import financial.bank.Bank;
import java.lang.Math;

public class ATM {

  private Bank bank;
  private int amount;

  private ATM(Bank bank, int amount) {
    this.bank = bank;
    this.amount = amount;
  }

  public static ATM makeATM(String bankName, int amount) {

    Bank bank;
		try {
		 	bank = Bank.valueOf(bankName);
      if(amount > 0) {
        return new ATM(bank,amount);
  		}
		}	catch(IllegalArgumentException e){
      return null;
    }
    return new ATM(bank,amount);
  }

  public int getAmount() {
    return this.amount;
  }

  public void decreaseAmount(int decrease) {
    this.amount -= decrease;
  }

  public void increaseAmount(int increase) {
    this.amount += increase;
  }

  public int calculateFee(Bank bank, int value) {
    double fee = 0;
    if(this.bank == bank) {
      fee = Math.ceil(value * 0.01);
      if(fee < 200) {
        fee = 200;
      }
    } else {
      fee = Math.ceil(value * 0.03);
      if(fee < 500) {
        fee = 500;
      }
    }
    return (int) fee;
  }

}
