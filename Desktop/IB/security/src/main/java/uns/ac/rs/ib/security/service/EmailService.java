package uns.ac.rs.ib.security.service;

public interface EmailService {

	void scheduleExamination(String date, String doctor, String examinationName, String clinic, String emailTo);
}
