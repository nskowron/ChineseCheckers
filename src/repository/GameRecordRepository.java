package src.repository;

import org.springframework.data.repository.*;
import org.springframework.data.jpa.repository.*;
import src.entity.GameRecord;

public interface GameRecordRepository extends JpaRepository<GameRecord, Integer> {
    // Additional query methods can be added if needed
}
