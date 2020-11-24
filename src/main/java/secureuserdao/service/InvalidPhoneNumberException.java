package secureuserdao.service;

public class InvalidPhoneNumberException extends IllegalStateException {

    private static final long serialVersionUID = 1793695531006082394L;

    private final String phoneNo;

    public InvalidPhoneNumberException(final String phoneNo) {
        super(phoneNo);
        this.phoneNo = phoneNo;
    }

    @Override
    public String getMessage() {
        return String.format("invalid Phone Number [%s] Provided", phoneNo);
    }
}
