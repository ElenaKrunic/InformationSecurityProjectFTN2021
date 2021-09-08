package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.dto.ExaminationDTOReq;
import uns.ac.rs.ib.security.dto.ExaminationDTORes;
import uns.ac.rs.ib.security.dto.SimpleSelectDTORes;
import uns.ac.rs.ib.security.dto.ExaminationDTO;
import uns.ac.rs.ib.security.model.Examination;

public interface ExaminationService {
	
	////////////////////////basic crud methods 
	List<Examination> findAll(); 
	
	Examination findOne(Integer id);
	
	Examination save(Examination examination); 
	
	void remove(Integer id);
	///////////////////////////////////////////

	List<ExaminationDTO> nursesWorkCalendar(String name) throws Exception;

	List<ExaminationDTORes> historyExamination(String name) throws Exception;

	List<ExaminationDTORes> availableTerms(String name);

	String createExamination(int id, String name) throws Exception;
	
	String createAppointment(ExaminationDTOReq examinationDTOReq, String name) throws Exception;

	List<SimpleSelectDTORes> doctors(String name) throws Exception;

	List<SimpleSelectDTORes> nurses(String name) throws Exception;

	List<SimpleSelectDTORes> services(String name) throws Exception;

	List<ExaminationDTORes> workCalendar(String name) throws Exception;

}
