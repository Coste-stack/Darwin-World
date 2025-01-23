package animal;

import area.Point;
import board.Board;
import board.BoardView;
import board.Tile;
import utils.Random;

import java.util.ArrayList;
import java.util.List;

public class AnimalHandler {
    private final Board board;
    private final BoardView boardView;
    List<Animal> animalList;

    public AnimalHandler(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
        this.animalList = new ArrayList<>();
    }

    public void createAnimal(Point position) {
        Animal animal = new Animal(position, boardView.calcTileSize() / 2);
        board.addAnimal(animal);
        animalList.add(animal);
    }

    public void runTurn() {
        moveAnimals();
        eatFood();
    }

    private void moveAnimals() {
        for (Animal animal : animalList) {
            int prevX = animal.getPosition().getX();
            int prevY = animal.getPosition().getY();

            // Calculate animal energy consumption
            int maxEnergyConsumption = animalList.getFirst().getMaxEnergyConsumption();
            int minEnergyConsumption = animalList.getFirst().getMinEnergyConsumption();
            int nearestPoleDist = board.calcAnimalDistToNearestPole(animal);
            if (nearestPoleDist == 0) {
                animal.setEnergyConsumption(maxEnergyConsumption); // Maximum energy consumption at the pole
            } else {
                int energyConsumption = Math.max(
                        minEnergyConsumption,
                        maxEnergyConsumption - (int) Math.pow(nearestPoleDist, 2) - nearestPoleDist * 5
                ); // Decreases with distance, minimum 10
                animal.setEnergyConsumption(energyConsumption);
            }

            // Change animal position based on direction
            animal.move();
            animal.rotate();

            int currX =  animal.getPosition().getX();
            int currY = animal.getPosition().getY();
            int newX = currX;
            int newY = currY;

            // Adjust newX and newY to loop through the grid boundaries
            if (currX < 0) {
                newX = board.getWidth() + currX;
            } else if (currX >= board.getWidth()) {
                newX = currX - board.getWidth();
            }

            if (currY < 0) {
                newY = board.getHeight() + currY;
            } else if (currY >= board.getHeight()) {
                newY = currY - board.getHeight();
            }

            // Check if position should change
            if (newX != currX || newY != currY) {
                // Update the animal's position
                animal.getPosition().setX(newX);
                animal.getPosition().setY(newY);
            }

            // Check if this tile has food (add energy and remove food)
            if (board.getBoardMatrix()[newX][newY].hasFood()) {
                animal.addEnergy(animal.getEnergyFoodGain());
                board.getBoardMatrix()[newX][newY].setHasFood(false);
            }

            // Remove the animal from its previous position in the grid matrix
            board.getBoardMatrix()[prevX][prevY].removeAnimal(animal);
        }
    }

    private Animal resolveFoodWar(Tile tile) {
        // One animal on tile
        if (tile.getAnimalList().size() == 1) {
            return tile.getAnimalList().getFirst();
        }
        // Multiple animals on tile
        else if (tile.getAnimalList().size() > 1) {
            // Food gets animal with most energy
            List<Animal> maxEnergyAnimalList = tile.getAnimalListWithMostEnergy();
            // One animal with most energy
            if (maxEnergyAnimalList.size() == 1) {
                return maxEnergyAnimalList.getFirst();
            }
            // Multiple animals with most energy
            else if (maxEnergyAnimalList.size() > 1) {
                // Food gets oldest animal (of those with most energy)
                List<Animal> oldestMostEnergyAnimalList = tile.getOldestAnimalList(maxEnergyAnimalList);
                // One animal on tile - oldest & most energy
                if (oldestMostEnergyAnimalList.size() == 1) {
                    return oldestMostEnergyAnimalList.getFirst();
                }
                // Multiple animal on tile - oldest & most energy
                else if (oldestMostEnergyAnimalList.size() > 1) {
                    // Choose one animal randomly
                    return oldestMostEnergyAnimalList.get(Random.getRandom(0, oldestMostEnergyAnimalList.size()-1));
                }
            }
        }
        return null;
    }

    private void eatFood() {
        // Temporary list to store animals to be removed
        // (cant remove in the middle of iterations)
        List<Animal> animalsToRemove = new ArrayList<>();

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Tile tile = board.getBoardMatrix()[i][j];
                if (tile.hasFood()) {
                    // Resolve food war between animals on tile
                    Animal animal = resolveFoodWar(tile);
                    if (animal != null) {
                        // Animal eats the food
                        animal.addEnergy(animal.getEnergyFoodGain());
                        tile.setHasFood(false);
                    }
                }

            }
        }

        for (Animal animal : animalList) {
            int newX = animal.getPosition().getX();
            int newY = animal.getPosition().getY();

            // Check if this tile has food (add energy and remove food)
            if (board.getBoardMatrix()[newX][newY].hasFood()) {
                animal.addEnergy(animal.getEnergyFoodGain());
                board.getBoardMatrix()[newX][newY].setHasFood(false);
            }

            // Log the animal properties
            System.out.println(animalList.indexOf(animal) + " - " + animal);

            if (animal.isAlive()) {
                // Place the animal in the new position in the grid matrix
                board.getBoardMatrix()[newX][newY].addAnimal(animal);
            } else {
                animalsToRemove.add(animal);
            }
        }

        // Remove animals outside iterations
        animalList.removeAll(animalsToRemove);
    }
}
