package uns.ac.rs.ib.security.service.impl;

import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import uns.ac.rs.ib.security.service.EmailService;
import uns.ac.rs.ib.security.util.Constants;

/**
 * https://www.baeldung.com/apache-velocity
 * @author Elena 
 *
 */
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	JavaMailSender mailSender; 
	
	@Autowired 
	VelocityEngine velocityEngine; 

	@Override
	public void scheduleExamination(String date, String doctor, String examinationName, String clinic, String emailTo) {
		VelocityContext context = new VelocityContext(); 
		context.put("clinic", clinic);
		context.put("examinationName", examinationName);
		context.put("doctor", doctor); 
		context.put("date", date);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setSubject("Examination info");
			helper.setFrom(Constants.EMAIL_ELENA);
			helper.setTo(emailTo);
			helper.setText(examinationInformation(context), true);
			new Thread(() -> {
				mailSender.send(helper.getMimeMessage());
			}).start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public String examinationInformation(VelocityContext context) {
		StringWriter writer = new StringWriter();
		
		try {
			velocityEngine.mergeTemplate("/templates/examination-template.vm", context, writer);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}

}
