package entity;

import java.sql.Date;
import java.util.List;

public class Customer {
    private Integer id;
    private final String name;
    private final String surname;
    private final Date birth_date;

    private List<DepositCard> depositCards;
    private List<CreditCard> creditCards;

    public Customer(String name, String surname, Date birth_date) {
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
    }

    public Customer(Integer id, String name, String surname, Date birth_date) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirth_date() {
        return birth_date;
    }


    public void setDepositCards(List<DepositCard> depositCards) {
        this.depositCards = depositCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth_date=" + birth_date +
                ", depositCards=" + depositCards +
                ", creditCards=" + creditCards +
                '}';
    }
}
