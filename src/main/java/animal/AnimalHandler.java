package animal;

import area.Point;
import board.Board;
import board.BoardView;
import board.Tile;
import javafx.scene.paint.Color;
import utils.ConfigHandler;
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

    public Animal createAnimal(Point position) {
        Animal animal = new Animal(position);
        AnimalView animalView = new AnimalView(animal, generateColorOfGenotype(animal), boardView.calcTileSize() / 2);
        animal.setAnimalView(animalView);

        board.addAnimal(animal);
        animalList.add(animal);
        return animal;
    }

    private Direction[] generateChildGenotype(Animal animal1, Animal animal2) {
        final int genotypeSize = animal1.getGenotypeSize();
        // Child inherits % of parents' genotypes (% is based on their energy)
        int animal1EnergySize = (int) Math.ceil(animal1.getEnergyPercentage() * genotypeSize);
        int animal2EnergySize = genotypeSize - animal1EnergySize; // Fill the rest using other parent genotype

        Direction[] genotype = new Direction[genotypeSize];
        for (int i = 0; i < animal1EnergySize; i++) {
            genotype[i] = animal1.getGenotype()[Random.getRandom(0, genotypeSize-1)];
        }
        for (int i = 0; i < animal2EnergySize; i++) {
            genotype[i+animal1EnergySize] = animal2.getGenotype()[Random.getRandom(0, genotypeSize-1)];
        }
        return genotype;
    }

    private void createChildAnimal(Animal animal1, Animal animal2) {
        // Create the child
        Animal child = this.createAnimal(animal1.getPosition());

        // Use parents' energy
        int REPRODUCTION_ENERGY_REQUIREMENT = ConfigHandler.getInstance().getConfigValue("REPRODUCTION_ENERGY_REQUIREMENT");
        int energy1 = animal1.subtractEnergy(REPRODUCTION_ENERGY_REQUIREMENT);
        int energy2 = animal2.subtractEnergy(REPRODUCTION_ENERGY_REQUIREMENT);
        child.setEnergy(energy1+energy2);

        // Create the genotype
        child.setGenotype(generateChildGenotype(animal1, animal2));

        AnimalView animalView = new AnimalView(child, generateColorOfGenotype(child), boardView.calcTileSize() / 2);
        child.setAnimalView(animalView);

        board.addAnimal(child);
        animalList.add(child);
    }

    private Color generateColorOfGenotype(Animal animal) {
        int hash = 0;
        for (Direction direction : animal.getGenotype()) {
            hash = 31 * hash + direction.ordinal();
        }
        double hue = (hash % 360 + 360) % 360;
        double saturation = 0.75;
        double brightness = 0.9;
        return Color.hsb(hue, saturation, brightness);
    }

    private Color generateColorOfGenotype(Animal animal1, Animal animal2) {
        int hash = 0;
        Direction[] genotype1 = animal1.getGenotype();
        Direction[] genotype2 = animal2.getGenotype();
        for (int i = 0; i < genotype1.length; i++) {
            hash = 31 * hash + genotype1[i].ordinal();
            hash = 31 * hash + genotype2[i % genotype2.length].ordinal();
        }
        double hue = (hash % 360 + 360) % 360;
        double saturation = 0.75;
        double brightness = 0.9;
        return Color.hsb(hue, saturation, brightness);
    }

    public void runTurn() {
        moveAnimals();
        eatFood();
        reproduceAnimals();
        removeDeadAnimals();
    }

    public void moveAnimals() {
        for (Animal animal : animalList) {
            int prevX = animal.getPosition().getX();
            int prevY = animal.getPosition().getY();

            // Calculate animal energy consumption
            int MAX_ENERGY_CONSUMPTION = ConfigHandler.getInstance().getConfigValue("MAX_ENERGY_CONSUMPTION");
            int MIN_ENERGY_CONSUMPTION = ConfigHandler.getInstance().getConfigValue("MIN_ENERGY_CONSUMPTION");
            int nearestPoleDist = board.calcAnimalDistToNearestPole(animal);
            if (nearestPoleDist == 0) {
                animal.setEnergyConsumption(MAX_ENERGY_CONSUMPTION); // Maximum energy consumption at the pole
            } else {
                int energyConsumption = Math.max(
                        MIN_ENERGY_CONSUMPTION,
                        MAX_ENERGY_CONSUMPTION - (int) Math.pow(nearestPoleDist, 2) - nearestPoleDist * 5
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

            // Move the animal from its prevPos to newPos in the grid matrix
            board.getBoardMatrix()[prevX][prevY].removeAnimal(animal);
            board.getBoardMatrix()[newX][newY].addAnimal(animal);
        }
    }

    private Animal resolveTileWar(Tile tile, List<Animal> startingAnimalList) {
        // One animal on tile
        if (startingAnimalList.size() == 1) {
            return tile.getAnimalList().getFirst();
        }
        // Multiple animals on tile
        else if (startingAnimalList.size() > 1) {
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
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Tile tile = board.getBoardMatrix()[i][j];
                if (tile.hasFood()) {
                    // Resolve food war between animals on tile
                    Animal animal = resolveTileWar(tile, tile.getAnimalList());
                    if (animal != null) {
                        // Animal eats the food
                        animal.addEnergy(ConfigHandler.getInstance().getConfigValue("ENERGY_FOOD_GAIN"));
                        tile.setHasFood(false);
                    }
                }
            }
        }
    }

    private void reproduceAnimals() {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Tile tile = board.getBoardMatrix()[i][j];

                // Get animals that can reproduce - stuffed
                List<Animal> stuffedAnimalList = new ArrayList<>();
                for (Animal animal : tile.getAnimalList()) {
                    if (animal.canReproduce()) {
                        stuffedAnimalList.add(animal);
                    }
                }

                Animal animal1 = resolveTileWar(tile, stuffedAnimalList);
                if (animal1 != null) {
                    stuffedAnimalList.remove(animal1);
                    Animal animal2 = resolveTileWar(tile, stuffedAnimalList);
                    if (animal2 != null) {
                        createChildAnimal(animal1, animal2);
                    }
                }
            }
        }
    }

    private void removeDeadAnimals() {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Tile tile = board.getBoardMatrix()[i][j];

                List<Animal> animalsToRemove = new ArrayList<>();
                for (Animal animal : tile.getAnimalList()) {
                    if (!animal.isAlive()) {
                        animalsToRemove.add(animal);
                    }
                }
                tile.getAnimalList().removeAll(animalsToRemove);
                animalList.removeAll(animalsToRemove);
            }
        }
    }

    public List<Animal> getAnimalList() {
        return this.animalList;
    }
}
