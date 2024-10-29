package es.jlsoler.mortgages.services;

import es.jlsoler.mortgages.api.MortgageCheckApiDelegate;
import es.jlsoler.mortgages.exceptions.InvalidMortgageProposalException;
import es.jlsoler.mortgages.model.CheckResult;
import es.jlsoler.mortgages.model.MortgageProposal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MortgageCheckService implements MortgageCheckApiDelegate {

    @Override
    public ResponseEntity<CheckResult> checkMortgage(MortgageProposal mortgageProposal) {

        try {
            validateInput(mortgageProposal);
        } catch (InvalidMortgageProposalException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(CheckResult.builder().rejectReason(ex.getMessage()).build());
        }

        float loanWithInterests = mortgageProposal.getLoanValue() + (mortgageProposal.getLoanValue() * (mortgageProposal.getInterestRate() / 100));
        float monthlyCost = (loanWithInterests / mortgageProposal.getMaturityPeriod());
        log.info("Calculated LoanWithInterests is {} and Monthly loan cost is {} ", loanWithInterests, monthlyCost);

        if (mortgageProposal.getLoanValue() > mortgageProposal.getHomeValue()) {
            return ResponseEntity.ok(CheckResult.builder().feasible(false).rejectReason("Loan exceeds home value").build());
        }

        if ((mortgageProposal.getLoanValue() / 4) > mortgageProposal.getIncome()) {
            return ResponseEntity.ok(CheckResult.builder().feasible(false).rejectReason("Loan exceeds 4X the income").build());
        }

        return ResponseEntity.ok(CheckResult.builder()
                .feasible(true)
                .monthlyCost(monthlyCost)
                .currency("EUR")
                .rejectReason("Accepted")
                .build());
    }

    /**
     * Validates the Mortgage Proposal bean (An improvement here would be to use a bean validation library)
     *
     * @param mortgageProposal
     * @throws InvalidMortgageProposalException
     */
    private void validateInput(MortgageProposal mortgageProposal) throws InvalidMortgageProposalException {
        StringBuilder errorMessage = new StringBuilder();

        if (mortgageProposal.getLoanValue() == null) {
            errorMessage.append("\n-Loan Value is missing");
        }
        if (mortgageProposal.getIncome() == null) {
            errorMessage.append("\n-Income Value is missing");
        }
        if (mortgageProposal.getHomeValue() == null) {
            errorMessage.append("\n-Home Value is missing");
        }
        if (mortgageProposal.getMaturityPeriod() == null) {
            errorMessage.append("\n-Maturity Period is missing");
        }
        if (mortgageProposal.getInterestRate() == null) {
            errorMessage.append("\n-InterestRate is missing");
        }
        if (mortgageProposal.getCurrency() == null) {
            errorMessage.append("\n-Currency is missing");
        }
        if (!errorMessage.isEmpty()) {
            throw new InvalidMortgageProposalException(errorMessage.toString());
        }
    }
}
