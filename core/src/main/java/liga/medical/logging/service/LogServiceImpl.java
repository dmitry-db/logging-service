package liga.medical.logging.service;

import liga.medical.logging.model.LogEntity;
import liga.medical.logging.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void save(LogEntity log) {
        logRepository.insert(log);
    }
}
