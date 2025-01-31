# **Projekt: Darwin World**
Projekt inspirowany na podstawie opisu z [tego repozytorium](https://github.com/Soamid/obiektowe-lab/blob/master/proj/Readme.md).

## **1. Świat**
- Świat to prostokątna siatka podzielona na kwadratowe pola.
- Jest on zapętlony (kulisty) – jeśli zwierzątko przekroczy krawędź, pojawi się po przeciwnej stronie.

## **2. Zwierzątka**
- Świat zamieszkują zwierzątka, które co turę:
  - Obracają się w jedną stronę z wszystkich kierunków (góra, dół, lewo, prawo oraz ich kombinacje). 
  - Na początku każdej tury, zwierzątko idzie do przodu w stronę, w którą jest obrócone.

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