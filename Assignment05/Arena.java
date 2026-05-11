package Assignment05;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Arena {

    public static void main(String[] args) {

        Warrior  thor    = new Warrior("Thor");
        Mage     loki  = new Mage("Loki");
        Paladin  odin     = new Paladin("Odin");
        Warrior  kratos = new Warrior("Kratos");   // different ID, same type

        Set<Player> playerSet = new HashSet<>();
        Map<Integer, GameCharacter> playerMap = new HashMap<>();
        List<String> history   = new ArrayList<>();

        Player p1 = new Player(1, "ThorFan",     thor);
        Player p2 = new Player(2, "WizardKing",  loki);
        Player p3 = new Player(3, "HolyLight",   odin);
        Player p4 = new Player(4, "GreekHero",   kratos);

        // Attempt to add a duplicate player 
        Player p1Duplicate = new Player(1, "ImposThor", thor);

        playerSet.add(p1);
        playerSet.add(p2);
        playerSet.add(p3);
        playerSet.add(p4);
        boolean duplicateAdded = playerSet.add(p1Duplicate);   // should be false
        System.out.println("------------------------- PLAYER REGISTRATIUON --------------------");
        System.out.println("Duplicate player (ID=1) added to Set? " + duplicateAdded
                + " HashSet prevented duplicate correctly.");
        System.out.println();

        playerMap.put(p1.getPlayerId(), thor);
        playerMap.put(p2.getPlayerId(), loki);
        playerMap.put(p3.getPlayerId(), odin);
        playerMap.put(p4.getPlayerId(), kratos);

        // Main part of the game simulation
        System.out.println("------------------------------- BATTLE BEGIN ------------------------");

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = thor.getName() + " attacks " + loki.getName();
            thor.attack(loki);
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException | InsufficientEnergyException e) {
            System.out.println("Action error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = loki.getName() + " casts Fireball on " + thor.getName();
            loki.useAbility(thor);
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException | InsufficientEnergyException e) {
            System.out.println("Action error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = odin.getName() + " heals himself";
            odin.heal();
            history.add(action);
            log.write(action + "\n");

        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = odin.getName() + " upgrades strength";
            odin.upgrade("strength");
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException e) {
            System.out.println("Action error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved – character health/energy logged.\n");
        }

        // Error test
        System.out.println("--- [Triggering Error 1: Attack on dead target] ---");
        loki.takeDamage(9999);   // kill Loki
        System.out.println(loki.getName() + " is dead: " + loki.isDeath());

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = thor.getName() + " attacks dead " + loki.getName();
            thor.attack(loki);   // to throw the exception
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException | InsufficientEnergyException e) {
            System.out.println("Action error: " + e.getMessage());
            history.add("[FAILED] " + thor.getName() + " tried to attack dead " + loki.getName());
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        // Error test 2
        System.out.println("--- [Triggering Error 2: Insufficient energy] ---");
        kratos.energy = 3;
        System.out.println(kratos.getName() + " energy forced to " + kratos.energy);

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = kratos.getName() + " uses Shield Slam on " + odin.getName();
            kratos.useAbility(odin);
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException | InsufficientEnergyException e) {
            System.out.println("Action error: " + e.getMessage());
            history.add("[FAILED] " + kratos.getName() + " tried Shield Slam with insufficient energy");
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        // Upgrade Error test 3
        System.out.println("--- [Triggering Error 3: Invalid upgrade stat] ---");

        try (FileWriter log = new FileWriter("game.log", true)) {
            String action = odin.getName() + " upgrades unknown stat 'luck'";
            odin.upgrade("luck");
            history.add(action);
            log.write(action + "\n");

        } catch (InvalidActionException e) {
            System.out.println("Action error: " + e.getMessage());
            history.add("[FAILED] " + odin.getName() + " tried to upgrade invalid stat 'luck'");
        } catch (IOException e) {
            System.out.println("Log error: " + e.getMessage());
        } finally {
            System.out.println("Game state saved - character health/energy logged.\n");
        }

        // Getting all the final stats
        System.out.println("\n-------------------FINAL-------------------");

        // Action history
        System.out.println("\n--- Action History (total: " + history.size() + " entries) ---");
        System.out.println("Last 3 actions:");
        int start = Math.max(0, history.size() - 3);
        for (int i = start; i < history.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + history.get(i));
        }

        // Unique players in match
        System.out.println("\n--- Unique Players in Match (" + playerSet.size() + " players) ---");
        for (Player p : playerSet) {
            System.out.println("  " + p);
        }

        // Health/energy of each character via Map lookup
        System.out.println("\n--- Character Status After Simulation ---");
        for (Map.Entry<Integer, GameCharacter> entry : playerMap.entrySet()) {
            GameCharacter ch = entry.getValue();
            String status = ch.isDeath() ? " [DEAD]" : "";
            System.out.println("  ID " + entry.getKey() + " -> " + ch + status);
        }

        System.out.println("\nSimulation complete. Good luck in the arena!");
    }
}