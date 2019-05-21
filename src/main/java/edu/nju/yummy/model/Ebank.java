package edu.nju.yummy.model;

import javax.persistence.*;

@Entity
@Table(name = "ebank", schema = "yummy", catalog = "")
public class Ebank {
    private String email;
    private double balance = 5000;
    private String paypassword;

    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ebank that = (Ebank) o;

        if (Double.compare(that.balance, balance) != 0) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = email != null ? email.hashCode() : 0;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Basic
    @Column(name = "paypassword")
    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }
}
