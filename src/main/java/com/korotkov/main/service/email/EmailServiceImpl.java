package com.korotkov.main.service.email;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.korotkov.main.config.PortalConstants.TIME_ZONE;
import static com.korotkov.main.service.email.EmailConfig.*;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender mailSender;
    private JavaMailSender mailSender01;
    private JavaMailSender mailSender02;
    private JavaMailSender mailSender03;
    private JavaMailSender mailSender04;
    private JavaMailSender mailSender05;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    @Qualifier("mailSender")
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    @Qualifier("mailSender01")
    public void setMailSender01(JavaMailSender mailSender01) {
        this.mailSender01 = mailSender01;
    }

    @Autowired
    @Qualifier("mailSender02")
    public void setMailSender02(JavaMailSender mailSender02) {
        this.mailSender02 = mailSender02;
    }

    @Autowired
    @Qualifier("mailSender03")
    public void setMailSender03(JavaMailSender mailSender03) {
        this.mailSender03 = mailSender03;
    }

    @Autowired
    @Qualifier("mailSender04")
    public void setMailSender04(JavaMailSender mailSender04) {
        this.mailSender04 = mailSender04;
    }

    private boolean tryToSendEmailWithUsername(String emailUsername, MimeMessagePreparator preparator) {
        boolean result = false;
        try {

            switch (emailUsername) {
                case EMAIL_USERNAME -> {
                    mailSender.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_01 -> {
                    mailSender01.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_02 -> {
                    mailSender02.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_03 -> {
                    mailSender03.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_04 -> {
                    mailSender04.send(preparator);
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    @Override
    public boolean sendEmail (final String templateName, final Map<String,Object> model) {
        boolean result = false;
        int countAttempts = 0;

        try {
            MimeMessagePreparator preparator;

            while (!result) {

                if (countAttempts > 5) {
                    break;
                }

                if (canSendEmailByUsernameCheckCount(EMAIL_USERNAME_01)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_01)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_01);
                    preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_01, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_01, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_01);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_02)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_02)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_02);
                    preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_02, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_02, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_02);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_03)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_03)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_03);
                    preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_03, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_03, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_03);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_04)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_04)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_04);
                    preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_04, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_04, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_04);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME);
                    preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME);
                    }
                }

                if (!canSendEmailByUsernameCheckCount(EMAIL_USERNAME)) {
                    deleteAllValuesFromMapCountAndEmailUsername();
                }

                countAttempts++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    private void checkAndAddSentEmailCurrentTimeToMap(String emailUsername) {
        if (!canSendEmailByUsernameCheckCount(emailUsername)) {
            MAP_SENDER_AND_LAST_SENT_TIME.put(emailUsername, LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        }
    }

    private void addCountSentEmailToMap(String emailUsername, int count) {
        if (!MAP_SENDER_AND_COUNT.containsKey(emailUsername)) {
            MAP_SENDER_AND_COUNT.put(emailUsername, count);
        } else {
            MAP_SENDER_AND_COUNT.put(emailUsername, MAP_SENDER_AND_COUNT.get(emailUsername) + count);
        }
    }

    private void sleepCurrentThreadWithTimeLimit() {
        try {
            Thread.sleep(COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME * 60 * 1000);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private MimeMessagePreparator getMimeMessagePreparator(final String templateName,
                                                           final Map<String,Object> model) {
        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                String from = (String) model.get(FROM);
                String to = (String) model.get(TO);
                String subject = (String) model.get(SUBJECT);

                List<String> bccList = (List<String>) model.get(BCC_LIST);

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());
                if(bccList != null){
                    for(String bcc: bccList){
                        message.addBcc(bcc);
                    }
                }

                List<FileSystemResource> fileList = (List<FileSystemResource>) model.get(ATTACHMENTS);

                if (fileList != null) {
                    for (FileSystemResource file:fileList) {
                        message.addAttachment(file.getFilename(), file);
                    }
                }

                model.put("noArgs", new Object());
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        templateName,"UTF-8",model);

                message.setText(text,true);
            }
        };
    }


    private void deleteAllValuesFromMapCountAndEmailUsername() {
        MAP_SENDER_AND_COUNT.clear();
    }

    private boolean canSendEmailByUsernameCheckCount(String emailUsername) {
        boolean result = false;
        if (MAP_SENDER_AND_COUNT.containsKey(emailUsername)) {
            if (MAP_SENDER_AND_COUNT.get(emailUsername) < COUNT_EMAILS_FOR_EACH_EMAIL_ACCOUNT) {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }


    private boolean checkTimeCanSendNewEmail(String emailUsername) {
        boolean result = true;
        if (MAP_SENDER_AND_LAST_SENT_TIME.containsKey(emailUsername)) {
            if (MAP_SENDER_AND_LAST_SENT_TIME.get(emailUsername).isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE))
                    .minusMinutes(COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME))) {
                result = false;
            }
        }
        return result;
    }




//    @Override
//    public boolean sendEmail (final String templateName, final Map<String,Object> model){
//        boolean res = false;
//        try {
//            MimeMessagePreparator preparator = new MimeMessagePreparator() {
//                @Override
//                public void prepare(MimeMessage mimeMessage) throws Exception {
//                    String from = (String) model.get(FROM);
//                    String to = (String) model.get(TO);
//                    String subject = (String) model.get(SUBJECT);
//
//                    List<String> bccList = (List<String>) model.get(BCC_LIST);
//
//                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
//                    message.setFrom(from);
//                    message.setTo(to);
//                    message.setSubject(subject);
//                    message.setSentDate(new Date());
//                    if(bccList != null){
//                        for(String bcc: bccList){
//                            message.addBcc(bcc);
//                        }
//                    }
//
//                    model.put("noArgs", new Object());
//                    String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
//                            templateName,"UTF-8",model);
//
//                    message.setText(text,true);
//                }
//            };
//
//            mailSender.send(preparator);
//            res = true;
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return res;
//    }


}
