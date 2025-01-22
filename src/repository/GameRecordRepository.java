package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import src.entity.GameRecord;

public interface GameRecordRepository extends JpaRepository<GameRecord, Integer> {
    // Additional query methods can be added if needed
}
