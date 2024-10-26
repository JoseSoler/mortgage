package es.jlsoler.mortgages.service;

import es.jlsoler.mortgages.api.MortgageCheckApiDelegate;
import es.jlsoler.mortgages.model.CheckResult;
import es.jlsoler.mortgages.model.MortgageProposal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MortgageCheckService implements MortgageCheckApiDelegate {

    @Override
    public ResponseEntity<CheckResult> checkMortgage(MortgageProposal mortgageProposal) {

        float loanWithInterests = mortgageProposal.getLoanValue() + (mortgageProposal.getLoanValue() * (mortgageProposal.getInterestRate() / 100));
        float monthlyCost = (loanWithInterests / mortgageProposal.getMaturityPeriod());

        if (mortgageProposal.getLoanValue() > mortgageProposal.getHomeValue()) {
            return ResponseEntity.ok(CheckResult.builder().feasible(false).reason("Loan exceeds home value").build());
        }

        if ((mortgageProposal.getLoanValue() / 4) > mortgageProposal.getIncome()) {
            return ResponseEntity.ok(CheckResult.builder().feasible(false).reason("Loan exceeds 4X the income").build());
        }

        return ResponseEntity.ok(CheckResult.builder()
                .feasible(true)
                .monthlyCost(monthlyCost)
                .currency("EUR")
                .build());
    }


}
