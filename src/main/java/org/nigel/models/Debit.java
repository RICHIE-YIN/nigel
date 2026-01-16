package org.nigel.models;

public class Debit {
    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public int getCardCVV() {
        return CardCVV;
    }

    public void setCardCVV(int cardCVV) {
        CardCVV = cardCVV;
    }

    public String getCardExpiration() {
        return CardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        CardExpiration = cardExpiration;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public String getCardHolderFullName() {
        return CardHolderFullName;
    }

    public void setCardHolderFullName(String cardHolderFullName) {
        CardHolderFullName = cardHolderFullName;
    }

    public double getCardAmount() {
        return CardAmount;
    }

    public void setCardAmount(double cardAmount) {
        CardAmount = cardAmount;
    }

    private String CardNumber;
    private int CardCVV;
    private String CardExpiration;
    private String HomeAddress;
    private String CardHolderFullName;
    private double CardAmount;

    //
    public Debit() {}
    public Debit(String CardNumber, int CardCVV, String CardExpiration, String HomeAddress, String CardHolderFullName, double CardAmount) {
        this.CardNumber = CardNumber;
        this.CardCVV = CardCVV;
        this.CardExpiration = CardExpiration;
        this.HomeAddress = HomeAddress;
        this.CardHolderFullName = CardHolderFullName;
        this.CardAmount = CardAmount;
    }
    //
    public String toFormat() {
        // name|HomeAddress|cardNumber|cardExpiration|cardCVV|cardAmount
        return this.CardHolderFullName + "|" + this.HomeAddress + "|" + this.CardNumber + "|" + this.CardExpiration + "|" + this.CardCVV + "|" + this.CardAmount;
    }

}