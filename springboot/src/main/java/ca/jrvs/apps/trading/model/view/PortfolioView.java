package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.SecurityRow;

import java.util.List;

public class PortfolioView {

    private List<SecurityRow> securityRowList;

    public List<SecurityRow> getSecurityRowList() {
        return securityRowList;
    }

    public void setSecurityRowList(List<SecurityRow> securityRowList) {
        this.securityRowList = securityRowList;
    }
}
