package recruitment.itsf.domain.exception;

public class DomainException extends RuntimeException {

    private final String errorCode;

    public DomainException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

