package es.jlsoler.mortgages.services;

import es.jlsoler.mortgages.model.CheckResult;
import es.jlsoler.mortgages.model.MortgageProposal;
import es.jlsoler.mortgages.service.MortgageCheckService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Business rules that apply are
 * - a mortgage should not exceed 4 times the income
 * - a mortgage should not exceed the home value
 */
public class MortgageCheckServiceTest {

    //Subject Under Test
    private final MortgageCheckService service = new MortgageCheckService();


    @Test
    void shouldDenyMortgageWhenExceeds4TimesIncome() {
        //given
        MortgageProposal proposal = MortgageProposal.builder()
                .income(1500)
                .loanValue(90000)
                .homeValue(180000)
                .maturityPeriod(500)
                .interestRate(3.5F)
                .currency("EUR").build();

        //when
        CheckResult result = service.checkMortgage(proposal).getBody();

        //then
        assertNotNull(result);
        assertFalse(result.getFeasible());
    }

    @Test
    void shouldDenyMortgageWhenExceedsHomeValue() {
        //given
        MortgageProposal proposal = MortgageProposal.builder()
                .income(1500)
                .loanValue(300000)
                .homeValue(200000)
                .maturityPeriod(500)
                .interestRate(3.5F)
                .currency("EUR").build();

        //when
        CheckResult result = service.checkMortgage(proposal).getBody();

        //then
        assertNotNull(result);
        assertFalse(result.getFeasible());
    }

    @Test
    void shouldAcceptMortgage() {
        //given
        MortgageProposal proposal = MortgageProposal.builder()
                .income(1500)
                .loanValue(4500)
                .homeValue(180000)
                .maturityPeriod(500)
                .interestRate(3.5F)
                .currency("EUR").build();

        //when
        CheckResult result = service.checkMortgage(proposal).getBody();

        //then
        assertNotNull(result);
        assertTrue(result.getFeasible());
    }
}
