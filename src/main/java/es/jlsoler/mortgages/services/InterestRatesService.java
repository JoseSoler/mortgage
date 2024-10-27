package es.jlsoler.mortgages.services;

import es.jlsoler.mortgages.api.InterestRatesApiDelegate;
import es.jlsoler.mortgages.model.MortgageRate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InterestRatesService implements InterestRatesApiDelegate {

    private final List<MortgageRate> rates = List.of(
            MortgageRate.builder()
                    .interestRate(10.5F)
                    .maturityPeriod(56)
                    .lastUpdate(new Date()).build(),
            MortgageRate.builder()
                    .interestRate(3.5F)
                    .maturityPeriod(160)
                    .lastUpdate(new Date()).build(),
            MortgageRate.builder()
                    .interestRate(3.5F)
                    .maturityPeriod(100)
                    .lastUpdate(new Date()).build(),
            MortgageRate.builder()
                    .interestRate(1.5F)
                    .maturityPeriod(100000)
                    .lastUpdate(new Date()).build(),
            MortgageRate.builder()
                    .interestRate(0.5F)
                    .maturityPeriod(3456000)
                    .lastUpdate(new Date()).build()

    );


    @Override
    public ResponseEntity<List<MortgageRate>> listMortgageRates() {
        return ResponseEntity.ok(rates);
    }
}
