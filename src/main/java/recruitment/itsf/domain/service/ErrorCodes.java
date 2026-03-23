package recruitment.itsf.domain.service;

public final class ErrorCodes {

    private ErrorCodes() {
    }

    public static final String OPTION_ALREADY_EXISTS = "option.already.exists";
    public static final String OPTION_HD_REQUIRES_NETFLIX = "option.hd.requires.netflix";
    public static final String OPTION_MUSIC_NOT_ALLOWED = "option.music.not.allowed";
    public static final String OPTION_NETFLIX_NOT_ALLOWED = "option.netflix.not.allowed";
    public static final String OPTION_ROAMING_NOT_ALLOWED = "option.roaming.not.allowed";
    public static final String OPTION_TYPE_NULL = "option.isNull";
    public static final String SUBSCRIPTION_CLIENT_ID_REQUIRED = "subscription.clientId.required";
    public static final String SUBSCRIPTION_NOT_FOUND = "subscription.isNull";
    public static final String SUBSCRIPTION_TYPE_REQUIRED = "subscription.type.required";
}
