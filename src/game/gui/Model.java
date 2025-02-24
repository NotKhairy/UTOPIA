// package game.gui;

// import java.util.ArrayList;
// import java.util.List;

// import game.engine.Battle;
// import game.engine.exceptions.InsufficientResourcesException;
// import game.engine.exceptions.InvalidLaneException;
// import game.engine.lanes.Lane;

// public class Model {

//     public Move getBestMove(Battle OGbattle) throws InsufficientResourcesException, InvalidLaneException {
//         Battle battle = OGbattle.clone();
//         Move move = new Move();
//         ArrayList<Lane> originalLanes = battle.getOriginalLanes();

//         // Dynamic resource threshold based on current phase of the game
//         int minResources = Math.max(25, 15 * battle.getLanes().size() - (battle.getNumberOfTurns() / 10));
//         if (battle.getResourcesGathered() < minResources) {
//             move.setPass(true);
//             move.setDescription("Pass the turn to save resources");
//             return move;
//         }

//         // Lane analysis
//         List<Move> moves = new ArrayList<>(); // contains the best move for each lane based on score to res ratio
//         for (int i = 0; i < originalLanes.size(); i++) {
//             Lane currLane = originalLanes.get(i);
//             double bestWeightedScore = 0;
//             Move bestMoveForLane = null;

//             for (int j = 1; j <= 4; j++) {
//                 Battle simBattle = new Battle(battle);
//                 try {
//                     simBattle.purchaseWeapon(j, currLane);
//                 } catch (InsufficientResourcesException | InvalidLaneException e) {
//                     continue;
//                 }

//                 double scoreIncrease = simBattle.getScore() - battle.getScore();
//                 double resourceCost = battle.getWeaponFactory().getWeaponShop().get(j).getPrice();
//                 double distanceWeight = 1.0 / (currLane.getTitans().isEmpty() ? 1 : currLane.getTitans().peek().getDistance());

//                 double weightedScore = (scoreIncrease / resourceCost) * distanceWeight;
//                 if (weightedScore > bestWeightedScore) {
//                     bestWeightedScore = weightedScore;
//                     bestMoveForLane = new Move();
//                     bestMoveForLane.setRatio(weightedScore);
//                     bestMoveForLane.setPass(false);
//                     bestMoveForLane.setLaneNumber(i);
//                     bestMoveForLane.setWeaponCode(j);
//                     bestMoveForLane.setDescription("Buy " + battle.getWeaponFactory().getWeaponShop().get(j).getName() + " in lane " + i);
//                 }
//             }

//             if (bestMoveForLane != null) {
//                 moves.add(bestMoveForLane);
//             } else {
//                 Move passMove = new Move();
//                 passMove.setPass(true);
//                 passMove.setDescription("Pass the turn for lane " + i);
//                 moves.add(passMove);
//             }
//         }

//         // Overall analysis to prioritize lanes with the closest titans
//         Lane chosenLane = null;
//         int chosenLaneIndex = 0;
//         int closestDistance = Integer.MAX_VALUE;
//         for (int i = 0; i < originalLanes.size(); i++) {
//             Lane currLane = originalLanes.get(i);
//             if (!currLane.isLaneLost() && !currLane.getTitans().isEmpty()) {
//                 int titanDistance = currLane.getTitans().peek().getDistance();
//                 if (titanDistance < closestDistance) {
//                     chosenLane = currLane;
//                     chosenLaneIndex = i;
//                     closestDistance = titanDistance;
//                 }
//             }
//         }

//         // Generating the final decision
//         if (chosenLane == null) {
//             double maxRatio = 0;
//             for (Move m : moves) {
//                 if (!m.getPass() && m.getRatio() > maxRatio) {
//                     maxRatio = m.getRatio();
//                     move = m;
//                 }
//             }
//         } else {
//             move = moves.get(chosenLaneIndex);
//         }

//         return move;
//     }
// }
