package es.jlsoler.mortgages.exceptions;

/**
 * Runtime Exception raised when Mortgage Proposal input is not valid
 */
public class InvalidMortgageProposalException extends RuntimeException {
    public InvalidMortgageProposalException(String message) {
        super(message);
    }
}
