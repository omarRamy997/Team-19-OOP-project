package com.example.eventmanagement;

public class Wallet {
    private double balance;

    public Wallet(double initialBalance) {
        if (initialBalance < 0) {
            this.balance = 0;
            System.out.println("Initial balance cannot be negative. Wallet set to 0.");
        } else {
            this.balance = initialBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasSufficientFunds(double amount) {
        return balance >= amount;
    }

    public void addFunds(double amount) {
        if (amount > 0) {
            this.balance += amount;
            // System.out.println("Added $" + amount + " to the wallet. New balance: $" + this.balance); // Console output, GUI should use alerts
        } else {
            // System.out.println("Invalid amount. Please enter a positive value to add funds."); // Console output
        }
    }

    public boolean deductFunds(double amount) {
        if (amount > 0) {
            if (hasSufficientFunds(amount)) {
                this.balance -= amount;
                // System.out.println("Deducted $" + amount + " from the wallet. New balance: $" + this.balance); // Console output
                return true;
            } else {
                // System.out.println("Insufficient funds. Cannot deduct $" + amount + "."); // Console output
                return false;
            }
        } else {
            // System.out.println("Invalid amount. Please enter a positive value to deduct funds."); // Console output
            return false;
        }
    }

    @Override
    public String toString() {
        return "Wallet{balance=" + String.format("%.2f", balance) + "}";
    }
}

