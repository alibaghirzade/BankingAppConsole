package entity;

public class CreditCard {
    private Integer id;
    private Double balance;
    private final Integer over_draft_limit = 150000;
     private final Double interest = 0.25;
    private final Integer customer_id;

    public CreditCard(Integer account_id, Double balance) {
        this.customer_id = account_id;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOver_draft_limit() {
        return over_draft_limit;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Double getInterest() {
        return interest;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", balance=" + balance +
                ", over_draft_limit=" + over_draft_limit +
                ", interest=" + interest +
                ", customer_id=" + customer_id +
                '}';
    }
}
