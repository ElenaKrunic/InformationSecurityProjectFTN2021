package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.repository.HealthSerRepository;
import uns.ac.rs.ib.security.service.HealthSerService;

@Service
public class HealthSerServiceImpl implements HealthSerService {

	@Autowired
	HealthSerRepository healthSerRepository;
	
	@Override
	public List<HealthSer> findAll() {
		return healthSerRepository.findAll();
	}

	@Override
	public HealthSer findOne(Integer id) {
		return healthSerRepository.getById(id);
	}

	@Override
	public HealthSer save(HealthSer healthSer) {
		return healthSerRepository.save(healthSer);
	}

	@Override
	public void remove(Integer id) {
		healthSerRepository.deleteById(id);
	}

}
