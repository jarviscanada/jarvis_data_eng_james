package ca.jrvs.apps.trading.model.domain;

public class Account implements Entity<Integer> {

    private Integer Id;
    private Integer traderId;
    private Double amount;

    @Override
    public Integer getId() {
        return Id;
    }

    @Override
    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
