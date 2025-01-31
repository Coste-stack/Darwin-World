# **Projekt: Darwin World**
Projekt inspirowany na podstawie opisu z [tego repozytorium](https://github.com/Soamid/obiektowe-lab/blob/master/proj/Readme.md).

# Założenia

## **1. Świat**
- Świat to prostokątna siatka podzielona na kwadratowe pola.
- Jest on zapętlony (kulisty) – jeśli zwierzątko przekroczy krawędź, pojawi się po przeciwnej stronie.

## **2. Zwierzątka**
- Świat zamieszkują zwierzątka, które co turę:
  - Obracają się w jedną stronę z wszystkich kierunków (góra, dół, lewo, prawo oraz ich kombinacje). 
  - Na początku każdej tury, zwierzątko idzie do przodu w stronę, w którą jest obrócone.
  - Mają szansę na pominięcie tury. Jeżeli ich wiek jest większy niż minimalny (domyślnie 15), 
  to nie ruszają się podczas tury, ale zużywają energię.

## **3. Jedzenie**
- Na każdym polu jest szansa na pojawienie się jedzenia, które daje energie zwierzątkom.
- Są dwa rodzaje pól:
    - **Preferowane** – wyższa szansa na jedzenie (np. 80%).
    - **Niepreferowane** – niższa szansa na jedzenie (np. 20%).

## **4. Specjalne miejsca**
- Na zdefiniowanych współrzędnych istnieją specjalne miejsca - takie jak:
  - **Poles (bieguny)** (północne i południowe), które zajmują 10% świata od góry i dołu.
  - **Grassfield (łąka)** - znajduje się na środku świata.
  - **Plains (równiny)** - domyślne miejsce.
  - Wszystkie miejsca mają specjalne właściwości:
    - Bieguny - im bliżej zwierzątko znajduje się do biegunu, tym więcej energii zużywa na ruch w turze.
    - Szansa na preferowane pola przez jedzenie:  
    Domyślnie:
      - grassfield - **10%**.
      - plains - **6%**.
      - bieguny - **1%**.

## **5. Genotyp**
Każde zwierzątko ma swój własny genotyp.  
- Jest to tablica o długości 8 - ilość kierunków obrotu.   
- Genotyp zawiera kierunki obrotu (góra, dół, lewo, prawo, oraz ich kombinacje).  
- Na początku symulacji genotyp jest wybierany losowo dla każdej pozycji.  
- Po każdym ruchu zwierzątko obraca się zgodnie z kolejną wartością w swoim genotypie.

## **6. Rozmnażanie**
Każde zwierzątko ma szansę na rozmnożenie się, jednak są pewne wymagania do spełnienia:
- Musi być jeszcze jedno zwierzątko na tym samym polu
- Oba zwierzątka muszą mieć wystarczającą ilość energii, aby się rozmnożyć, muszą tą energie wykorzystać  

Dziecko dziedziczy:
- Wykorzystaną przez nich energią.
- Połączeniem genotypów jego rodziców:
  - Pierwsza część jest pierwszego rodzica, a druga drugiego rodzica, pozycja podziału genotypu zależy od tego który rodzic miał więcej energii do wykorzystania.
  - Każda pozycja jest wybierana losowo z tablicy genotypu odpowiedniego rodzica.

## **7. Wojna o jedzenie i rozmnażanie**
Na jednym polu tylko jeden organizm może zjeść jedzenie oraz tylko jedna para może się rozmnożyć.  
Priorytet działań dla obu akcji jest ustalany według:
1. **Najwyższej energii** – zwierzątko z większą ilością energii ma pierwszeństwo.
2. **Wiek** – starsze zwierzątko ma przewagę w przypadku remisu.
3. **Losowość** – jeśli energia i wiek są identyczne, decyzja jest losowa. 

# Zależności
- **Javafx** - graficzny interfejs
- **Junit** - testy

# Implementacja

## **1. Main**
Klasa inicjalizuje stage i pokazuje formularz, w którym można zmienić parametry symulacji.  
Parametry są zapisane w plikach tekstowych:
  - `static-config.txt`
  - `dynamic-config.txt`, które można zmieniać w formularzu
Formularz musi być poprawny, a gdy wyskakuje błąd lub ostrzeżenie - pokazuje się okienko alert.

## **2. ConfigHandler**
Klasa singleton, która ma tylko jedną instancje, którą można uzyskać za pomocą jej metody.  
Odczytuje pliki tekstowe i zapisuje je w mapie.  
Ma metody które pozwalają na zmiane danych w mapie oraz ich uzyskanie.  

## **3. Simulation**
Po wypełnieniu formularzu main tworzy instancje klasy Simulation, uzyskuje jej panel, wyświetla go, oraz wykonuje metodę `run`.
```
board.setFoodRandomly();
animalHandler.runTurn();
boardView.refreshBoard();
SimulationMenu.incrementIteration();
```
W konstruktorze tworzy instancje klasy Board, BoardView, AnimalHandler oraz SimulationMenu.

## **4. Board**
Posiada tablice 2D reprezentującą świat, przechowuje obiekty `Tile` reprezentujące poszczególne pola świata.  
Ma metody do:
- Dodania zwierzątka `Animal` oraz specjalnego miejsca `Area`.
- Ustawienia preferowanych przez jedzenie pól (na podstawie zdefiniowanej szansy).
- Obliczenia odległości do najbliższego biegunu.
- Dodania wykresu `Plot` oraz dodania danych do niego (które są dodawane co iteracje).

## **5. Tile**
Reprezentuje pojedyncze pole.  
Posiada liste zwierzątek.
Ma metody do:
- Zmiany koloru.
- Dodania/usunięcia zwierzątka z listy.
- Otrzymania listy `Animal` z największą ilością energii.
- Otrzymania listy `Animal` z najstarszymi zwierzątkami, jest otrzymywana z podanej jako parametr listy (z największą ilością energii).
- Sprawdzenia czy jest na nim jedzenie `hasFood` oraz ustawienie jedzenia.
- Sprawdzenia czy jest to miejsce preferowane przez jedzenie `isFoodPreferred` oraz ustawienie go.
- Otrzymanie miejsca specjalnego `Area` oraz ustawienia go.

## **6. Area**
Klasa abstrakcyjna reprezentująca miejsce specjalne. A więc:
- Przechowuje: współrzędne jej rogów, kolor jej pól, szanse na pole preferowane przez jedzenie.
- Ma tylko gettery, ustawienie parametrów występuje w konstruktorze.
Klasy po niej dziedziczące:
- `Grassfield`
- `Plains`
- `Pole` - też jest klasą abstrakcyjną, posiada enum z nazwami rodzai oraz przechowuje typ jako zmienną.
  - `NorthPole`
  - `SouthPole`

## **7. BoardView**
Ma na celu zwrócenie panelu od javafx reprezentującego tablice.  
Ma metody do:
- Oblcizenia wielkość kafelków (z podanej wysokości i szerokości panelu), którą wykorzystuje w `createBoard`.
- `createBoard` do utworzenia panelu.
- `refreshBoard` do zmiany panelu po zmianach logicznych wynikających z iteracji symulacji.

## **8. AnimalHandler**
Ma listę wszystkich zwierzątek `animalList`.  
Używa instancji `board` oraz `boardView` (z argumentów konstruktora).  

Ma metody do:
- Tworzenia zwierzątka: 
  - Używa boardView do obliczenia promienia `animalView`.
  - Dodaje go do list.
- Przeprowadzania tury, gdzie sprawia że wszystkie zwierzątka:
  - Poruszają się `moveAnimals`
  - Jedzą `eatFood`:
    - Sprawdza czy pole ma jedzenie
    - Znajduje zwierzątko z priorytetem używając `resolveTileWar`
  - Rozmnażają się (jeżeli mogą) `reproduceAnimals`:
    - Zdobywa liste zwierzątek, które mogą się rozmnożyć (mają wystarczającą ilość energii)
    - Otrzymuje dwa zwierzątka używając dwa razy `resolveTileWar`
  - Usuwa martwe zwierzątka `removeDeadAnimals`
- Generowania genotypu zwierzątka

## **9. Animal**
Przechowuje:
- Instancję `animalView` ustawioną w `animalHandler`.
- Pozycję `Point` w tablicy `board`.
- Obrót `Direction`, w którą stronę jest skierowany.
- Tablicę genotypu `genotypeList` zawierającą wybrane elementy `Direction`.
- Wiek `age`.
- Energię `energy`.
- Czy jest martwy/żywy `isAlive`.

Ma metody do:
- Dodania oraz odjęcia energii `addEnergy` i `subtractEnergy`.
- Poruszania się `move`, która implementuje logikę sprawdzającą:
  - Czy jest w wymiarach tablicy.
  - Czy wylosowana szansa sprawia że pominie turę `getSkipTurnChance` (bazowana na wieku `age`).
- Generacji losowego genotypu `generateGenotype`.

## **10. AnimalView**
Ma w zmiennych instancję `Animal`, którą wykorzystuje do otrzymania procentu energii (energia/maksymalna energia). 
Definiuje ona to jaki kolor oraz wysokość ma wypełnienie panelu.

Ustawione kolory to:
 - `GOOD_ENERGY_COLOR` - od 66% - zielony
 - `MEDIUM_ENERGY_COLOR` - od 33% - żółty
 - `BAD_ENERGY_COLOR` - od 0% - czerwony

Ma też metodę do zmiany skali panelu, która jest wykorzystywana jak jest więcej niż 1 zwierzątko na polu.

## **11. Direction**
Enum zawierający wszystkie kierunki:
- Góra
- Dół
- Prawo
- Lewo
- Góra-prawo
- Góra-lewo
- Dół-prawo
- Dół-lewo
