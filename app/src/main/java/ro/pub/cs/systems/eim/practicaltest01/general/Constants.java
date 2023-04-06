package ro.pub.cs.systems.eim.practicaltest01.general;

public interface Constants {

    final public static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    final public static String FIRST_NUMBER = "firstNumber";
    final public static String SECOND_NUMBER = "secondNumber";
    final public static String THIRD_NUMBER = "thirdNumber";

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.ping"
    };

    final public static int NUMBER_OF_CLICKS_THRESHOLD = 5;
    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;

    final public static String PROCESSING_THREAD_TAG = "[Processing Thread]";

    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[Message]";

    final public String ALL_TERMS_STRING = "allTermsString";
    String NEXT_TERM_VALUE = "nextTermValue";
    String TOAST_VALUE = "toastValue";
    String CURRENT_TIME = "currentTime";
    String NUMBER_OF_CHECKED_BOXES = "numberOfCheckedBoxes";
    String SCORE = "score";
}