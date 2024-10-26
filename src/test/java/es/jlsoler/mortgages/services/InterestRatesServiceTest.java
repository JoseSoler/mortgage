package es.jlsoler.mortgages.services;

import es.jlsoler.mortgages.model.MortgageRate;
import es.jlsoler.mortgages.service.InterestRatesService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InterestRatesServiceTest {

    //Subject under Test
    private final InterestRatesService service = new InterestRatesService();

    @Test
    void shouldReturnListOfRates() {
        //given
        //An in-memory list of 5 mortgages rates
        int numOfExpectedRates = 5;

        //when
        List<MortgageRate> rates = service.listMortgageRates().getBody();

        //then
        assertNotNull(rates);
        assertEquals(numOfExpectedRates, rates.size());
    }
}
