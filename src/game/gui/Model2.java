// package game.gui;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.PriorityQueue;

// import game.engine.Battle;
// import game.engine.exceptions.InsufficientResourcesException;
// import game.engine.exceptions.InvalidLaneException;
// import game.engine.lanes.Lane;

// public class Model2 {
//     public Battle simulateEasy(Battle battle, int vision) throws IOException {
//     	System.out.println("Parameter -> " + battle.toString()+ " "+ battle.getScore());
//         if (battle == null || battle.isGameOver() || vision <= 0) return battle;
//         Battle pass = battle.clone();
//         battle.passTurn();
//         Battle L1W1 = createBattleWithWeapon(battle, 1);
//         Battle L1W2 = createBattleWithWeapon(battle, 2);
//         Battle L1W3 = createBattleWithWeapon(battle, 3);
//         Battle L1W4 = createBattleWithWeapon(battle, 4);
//         System.out.println("clones -> "+pass.getScore() +L1W1.getScore() + L1W2.getScore() + L1W3.getScore() + L1W4.getScore());

//         ArrayList<Battle> battles = new ArrayList<>(Arrays.asList(
//         	simulateEasy(pass, vision - 1),
//             simulateEasy(L1W1, vision - 1), 
//             simulateEasy(L1W2, vision - 1), 
//             simulateEasy(L1W3, vision - 1), 
//             simulateEasy(L1W4, vision - 1)
//         ));
        
//         System.out.println("chosen -> "+ getMaxByAttribute(battles).toString() + " " +getMaxByAttribute(battles).getScore());
//         return getMaxByAttribute(battles);
//     }

//     private Battle createBattleWithWeapon(Battle battle, int weapon) throws IOException {
//         Battle newBattle = battle.clone();
//         try {
//             newBattle.purchaseWeapon(weapon, getMostDangerousLane(newBattle));
//         } catch (InsufficientResourcesException | InvalidLaneException e) {
//         	e.printStackTrace();
//             return null;
//         }
//         return newBattle;
//     }
    
//     private Lane getMostDangerousLane(Battle battle) {
//     	Object[] arr = battle.getLanes().toArray();
//     	return (Lane) arr[arr.length-1];
//     }

//     public static Battle getMaxByAttribute(ArrayList<Battle> list) {
//         return Collections.max(list, Comparator.comparingInt(Battle::getScore));
//     }
// }
