package edu.mum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by Erdenebayar on 9/30/14
 */
@Component
public class EmailService {

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final ExecutorService executorService;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(@Qualifier("emailPoolExecutor") ExecutorService executorService, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.executorService = executorService;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void send(String fromEmail, String toEmail, String subject, String template, Map<String, ?> model){
        executorService.execute(new EmailSendTask(fromEmail, toEmail, subject, template, model));
    }

    public boolean sendSync(String fromEmail, String toEmail, String subject, String template, Map<String, ?> model){
        EmailSendTask task = new EmailSendTask(fromEmail, toEmail, subject, template, model);
        task.run();
        return task.getStatus() == TaskStatus.SUCCESS;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SYSTEM"})
    public void sendTestMail(String to, String subject){
        Map<String, ?> model = Collections.singletonMap("message", "hi <br /> second line");
        executorService.execute(new EmailSendTask("test@mum.edu", to, subject, "message", model));
    }

    private class EmailSendTask implements Runnable{

        final String fromEmail;
        final String toEmail;
        final String subject;
        final String template;
        final Map<String, ?> model;
        private TaskStatus status;

        public EmailSendTask(String fromEmail, String toEmail, String subject, String template, Map<String, ?> model) {
            this.fromEmail = fromEmail;
            this.toEmail = toEmail;
            this.subject = subject;
            this.template = template;
            this.model = model;
            this.status = TaskStatus.QUEUED;
        }

        @Override
        public void run() {
            this.status = TaskStatus.RUNNING;
            try {
                send(fromEmail, toEmail);
                this.status = TaskStatus.SUCCESS;
            }catch (MessagingException | UnsupportedEncodingException e){
                logger.error(String.format("Can't send email. Template: %s", template), e);
                this.status = TaskStatus.ERROR;
            }
        }

        protected void send(String fromEmail, String toEmail) throws MessagingException, UnsupportedEncodingException {
            int i = fromEmail.indexOf("@") + 1;
            String domain = fromEmail.substring(i);
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.addHeader("X-SMTPAPI", "{\"filters\":{\"domainkeys\":{\"settings\":{\"enable\":1,\"domain\":\""+domain+"\",\"sender\":1}}}}");
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false , "UTF-8");
            message.setSubject(subject);
            message.setFrom(new InternetAddress(fromEmail));
            message.setTo(toEmail);

            final String htmlContent = templateEngine.process(template, new Context(Locale.getDefault(), model));
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        }

        public TaskStatus getStatus() {
            return status;
        }
    }

    private class BulkEmailSendTask implements Runnable{

        final String toEmail;
        final String subject;
        final String template;
        final Map<String, ?> model;
        private TaskStatus status;

        public BulkEmailSendTask(String toEmail, String subject, String template, Map<String, ?> model) {
            this.toEmail = toEmail;
            this.subject = subject;
            this.template = template;
            this.model = model;
            this.status = TaskStatus.QUEUED;
        }

        @Override
        public void run() {

        }
    }

    private enum TaskStatus {
        QUEUED, RUNNING, SUCCESS, ERROR
    }
}
