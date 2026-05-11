package Assignment05;

import java.util.Objects;

public class Player {
    private int playerId;
    private String nickname;
    private GameCharacter character;

    public Player(int playerId, String nickname, GameCharacter character) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.character = character;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public GameCharacter getCharacter() {
        return character;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player other = (Player) obj;
        return this.playerId == other.playerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }

    @Override
    public String toString() {
        return "Player #" + playerId + " (" + nickname + ") → " + character;
    }
}