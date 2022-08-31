package com.korotkov.main.service.email;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class EmailConfig {
    /*Email From*/
    public static final String FROM = "from";

    /*Email To*/
    public static final String TO = "to";

    /*Email Subject*/
    public static final String SUBJECT = "subject";

    /*Email BCC*/
    public static final String BCC_LIST = "bccList";

    /*Email CCC*/
    public static final String CCC_LIST = "ccList";

    /*Email Attachment*/
    public static final String ATTACHMENTS = "attachments";



    public static final int COUNT_EMAILS_FOR_EACH_EMAIL_ACCOUNT = 15;
    public static final int COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME = 1;

    public static final String EMAIL_USERNAME = "info.scoring.machine@gmail.com";
    public static final String EMAIL_PASSWORD = "qosmxdngsspcycwx";

    public static final String EMAIL_USERNAME_01 = "no.reply01.scoring.machine";
    public static final String EMAIL_PASSWORD_01 = "fuywqcuxzqhrabuf";

    public static final String EMAIL_USERNAME_02 = "no.reply02.scoring.machine";
    public static final String EMAIL_PASSWORD_02 = "zxdpypgidczjnaad";

    public static final String EMAIL_USERNAME_03 = "no.reply03.scoring.machine";
    public static final String EMAIL_PASSWORD_03 = "qmbwclvvkfzbbhlf";

    public static final String EMAIL_USERNAME_04 = "no.reply.scoring.machine";
    public static final String EMAIL_PASSWORD_04 = "jmkassfdtmhdgqcu";

    public static Map<String, Integer> MAP_SENDER_AND_COUNT = new HashMap<>();
    public static Map<String, LocalDateTime> MAP_SENDER_AND_LAST_SENT_TIME = new HashMap<>();
}
