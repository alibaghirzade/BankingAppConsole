package entity;

public class DepositCard {
    private Integer id;
    private Double balance;
    private final Integer customer_id;


    public DepositCard(Integer customer_id, Double balance) {
        this.customer_id = customer_id;
        this.balance = balance;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DepositCard{" +
                "id=" + id +
                ", balance=" + balance +
                ", customer_id=" + customer_id +
                '}';
    }
}
