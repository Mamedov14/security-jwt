package ru.java.securityjwt.exception;

public class UserServiceException extends RuntimeException {
    protected CODE code;

    protected UserServiceException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected UserServiceException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

    public enum CODE {
        USER_NOT_FOUND_DATABASE("The user is not in the database"),
        INVALID_ACCESS("Invalid access"),
        TOKEN_IS_NOT_AUTHORIZED("Token received is not authorized"),
        TOKEN_IS_EXPIRED("Token is expired, try to Login again"),
        TOKEN_IS_UNSUPPORTED("Token received is unsupported, send a valid token"),
        ILLEGAL_ARGUMENTS_RECEIVED("Illegal arguments received, send a valid request"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public UserServiceException get() {
            return new UserServiceException(this, this.codeDescription);
        }

        public UserServiceException get(String msg) {
            return new UserServiceException(this, this.codeDescription + " : " + msg);
        }

        public UserServiceException get(Throwable e) {
            return new UserServiceException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public UserServiceException get(Throwable e, String msg) {
            return new UserServiceException(this, e, this.codeDescription + " : " + msg);
        }
    }
}
