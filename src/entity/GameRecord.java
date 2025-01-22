package src.entity;

import javax.persistence.*;

@Entity
@Table(name = "game_records")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_data", columnDefinition = "TEXT")
    private String gameData;

    public GameRecord() {}

    public GameRecord(String gameData) {
        this.gameData = gameData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
}
