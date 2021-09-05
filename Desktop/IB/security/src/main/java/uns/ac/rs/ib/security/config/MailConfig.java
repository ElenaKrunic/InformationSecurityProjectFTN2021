package uns.ac.rs.ib.security.config;


import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import uns.ac.rs.ib.security.util.Constants;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "uns.ac.rs.ib.security")
public class MailConfig {

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername(Constants.EMAIL);
        mailSender.setPassword(Constants.PASS);
        //
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.host", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.port", 465);
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.socketFactory.port", 465);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.debug", "true");
        //
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public VelocityEngine getVelocityEngine() throws VelocityException {
        Properties properties = new Properties();
        //
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        return velocityEngine;
    }
}
