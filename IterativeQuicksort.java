//Magda Wojtowicz - 1
/*
Idea programu: Program sortuje wiersze metadanych (format dany w tresci zadania) wg wybranej kolumny. Rodzaj sortowania - 
malejacy lub rosnacy - rowniez wybieramy.
Program wykorzystuje iteracyjna wersje programu quicksort (bez stosu), a dla malych podzadan - algorytm Selection Sort
By algorytm dzialal w wersji iteracyjnej konieczne jest zmienianie czasem danych, aby zapamietac w ktorym miejscu powinno skonczyc sie 
nastepne podzadanie.

Srednia zlozonosc czasowa - O(nlogn) - zakladajac, ze kazda z permutacji ciagu wejsciowego jest jednakowo prawdopodobna,
algorytm ma srednia zlozonosc czasowa O(nlogn), analogicznie do algorytmu quicksort w wersji rekurencyjnej - dzieli bowiem tablice na 
2 podzadania, a wyznaczenie ich dzieje sie w czasie liniowym.

Zlozonosc pamieciowa - stala - nie wykorzystywana jest zadna dodatkowa tablica, ani stos.
*/
import java.util.Scanner;

public class IterativeQuicksort {
    public static Scanner scan = new Scanner(System.in);
    public StringBuilder sB = new StringBuilder(); //StringBuilder do outputu

    String[] Headline; //tablica z naglowkiem
    String[][] A; //tablica dwuwymiarowa z danymi
    int numOfRows; //liczba rzedow
    int numOfColumns; //liczba kolumn
    int WhichColumn; //po ktorej kolumnie sortujemy
    int SortOrder; //czy sortowanie rosnace czy malejace
    boolean isNumeric; //czy w kolumnie po ktorej sortujemy dane to liczby

    boolean isNum(String s) //sprawdzamy czy w kolumnie po ktorej sortujemy dane to liczby
    { 
        try {  
          Integer.parseInt(s);  
          return true; //jesli sie udalo to zwracamy prawda
        } catch(NumberFormatException e){  
          return false;  //jesli zlapalismy wyjatek to zwracamy falsz
        }  
    }

    IterativeQuicksort(int n, int Column, int Order) //konstruktor
    {
        SortOrder = Order;
        WhichColumn = Column-1;
        String s = scan.nextLine();
        Headline = s.split(",");
        numOfRows = n;
        numOfColumns = Headline.length;
        A = new String[numOfRows][numOfColumns];
        for(int i = 0; i < numOfRows; i++)
        {
            s = scan.nextLine();
            A[i] = s.split(",");
        }

        if(isNum(A[0][WhichColumn]))
            isNumeric = true;
        else
            isNumeric = false;
    }

    boolean compare(int i, int j) //funkcja do porownywania - bierze pod uwage czy wartosci to liczby i kolejnosc sortowania
    {
        if(SortOrder == 1) //jesli rosnacy
        {
            if(isNumeric) //jesli jest liczba
            {
                if(Integer.parseInt(A[i][WhichColumn]) > Integer.parseInt(A[j][WhichColumn]))
                {
                    return true;//prawda jesli wartosc w i wieksza
                }
                return false; //w przeciwnym falsz
            }
            if(A[i][WhichColumn].compareTo(A[j][WhichColumn]) > 0) //jesli to napis to uzywamy compare to
            {
                return true;
            }
            return false;
        }
        if(isNumeric) //jesli nie weszlismy do ifa, to znaczy ze sortowanie malejace - wszystko na odwrot
        {
            if(Integer.parseInt(A[i][WhichColumn]) >= Integer.parseInt(A[j][WhichColumn]))
            {
                return false;
            }
            return true;
        }
        if(A[i][WhichColumn].compareTo(A[j][WhichColumn]) >= 0)
        {
            return false;
        }
        return true;
    }

    void swap(int i, int j)//zamienianie rzedow o indeksach i oraz j zlozonosc czasowa liniowa
    {
        String bufor = new String(); //jedyna dodatkowa zmienna
        for(int k = 0; k < numOfColumns; k++)
        {
            bufor = A[i][k]; 
            A[i][k] = A[j][k]; 
            A[j][k] = bufor;
        }
    }

    void Selection(int l, int r) //selection sort do malych podzadan
    {
        for(int i = l; i < r; i++) //idziemy po podzadaniu
        {
            int minIndex = i; 
            for(int j = i + 1; j <= r; j++) //idziemy po nieposortowanej czesci
            {
                if(compare(minIndex, j))
                {
                    minIndex = j; //znajdujemy indeks najmniejszego (najwiekszego jesli sortujemy w druga) elementu
                }
            }
            swap(i, minIndex); //wstawiamy najmniejszy el na miejsce
        }
    }
        int findNextR(int l) //funkcja zwracajaca indeks konca nastepnego podzadania
        {
            for (int i = l; i < numOfRows; ++i) {
                if (A[i][WhichColumn].endsWith("-"))
                    return i;
            }
            return numOfRows-1;
        }

        int HoarePartition(int l, int r) //funkcja partition w wersji Hoare'a (komentarze do sortowania rosnacego, w malejacym jest na odwrot)
        {
            int pivot = r; //indeks pivota to prawy koniec
            int i = l-1, j = r; //ustawiamy i oraz j
            while(true)
            {
                while(compare(pivot, ++i)); //zatrzymujemy sie na el wiekszym niz pivot
                while(compare(--j, pivot) && j > l); //zatrzymujemy sie na el mniejszym
                if(i >= j) //jesli sa w dobrej kolejnosci
                {
                    break;
                }
                else //jesli sa w zlej to je zamieniamy
                {
                    swap(i, j);
                }
            }
            swap(i, r); //dajemy pivota na dobre miejsce
            return i; //zwracamy miejsce w ktorym jest teraz pivot
        }

        void QuickIterative()
        {
            int l = 0, r = numOfRows-1, q = 0, i = 0; //na poczatku l to pierwsz indeks, a r - ostatni
            int curr = r; //aktualny koniec tam gdzie koniec
           
            while(true)
            {
                i--;
                while(curr - l + 1 > 5) //jesli podzadanie jest odpowiednio duze
                {
                    q = HoarePartition(l, curr); //znajdujemy el ktory jest juz na dobrym mijesc
                    A[curr][WhichColumn] += "-"; //dodajemy "-" zeby wiedziec ze to koniec prawej czesci tablicy, ktora bedzie pozniejszym podzadaniem
                    curr = q-1; //aktualny koniec na lewo od q, bo element w q jest juz w dobrym miejscu
                    i++; //zwiekszamy liczbe podzadan
                }
                Selection(l, curr); //wyszlismy z while'a - czyli podzadanie po lewej jest wystarczajaco male - wywolujemy na nim selection
                l = curr+2; //do curr mamy posortowane, curr+1 jest na dobrym miejscu, bo to q wyznaczane funkcja partition, czyli nastepne podzadanie zaczyna sie w curr+2
                curr = findNextR(l); //znajdujemy gdzie konczy sie nastepne podzadanie
                if(A[curr][WhichColumn].endsWith("-"))//jesli mielismy minusem zaznaczone
                {
                    int len = A[curr][WhichColumn].length() - 1;
                    A[curr][WhichColumn] = A[curr][WhichColumn].substring(0,len); //to go usuwamy
                }
                if(i < 0) //jesli skonczyly sie podzadania
                {
                    break; //koniec
                }
            }
            for(i = 0; i < numOfRows; i++) //usuwanie minusow, ktore sie ostaly - liniowe, wiec nie zmienia zlozonosci czasowej w notacji O
            {
                if(A[i][WhichColumn].endsWith("-"))
                {
                    int len = A[i][WhichColumn].length() - 1;
                    A[i][WhichColumn] = A[i][WhichColumn].substring(0,len); //usuwamy minusy, jesli sa
                }
            }
        }

    void display() //funkcja dodajaca output do string buildera w odpowiedniej kolejnosci
    {
        sB.append(Headline[WhichColumn]); //najpierw kolumna po ktorej sortowalismy
        for(int i = 0; i < WhichColumn; i++) //reszta naglowka
        {
            sB.append(",");
            sB.append(Headline[i]);
        }
        for(int i = WhichColumn+1; i < numOfColumns; i++)
        {
            sB.append(",");
            sB.append(Headline[i]);
        }
        sB.append("\n");
        for(int j = 0; j < numOfRows; j++)
        {
            sB.append(A[j][WhichColumn]);//najpierw kolumna po ktorej sortowalismy
            for(int i = 0; i < WhichColumn; i++) //reszta wiersza (wiersze sa posortowane)
            {
                sB.append(",");
                sB.append(A[j][i]);
            }
            for(int i = WhichColumn+1; i < numOfColumns; i++)
            {
                sB.append(",");
                sB.append(A[j][i]);
            }
            sB.append("\n");
        }
    }

    public static void main( String [] args ) {
   
        int howMany = scan.nextInt();
        scan.nextLine();
        for(int i = 0; i < howMany; i++)
        {
            String s = scan.nextLine();
            String[] Info = new String[3];
            Info = s.split(",");
            IterativeQuicksort sou = new IterativeQuicksort(Integer.parseInt(Info[0]), Integer.parseInt(Info[1]), Integer.parseInt(Info[2]));

            sou.QuickIterative();
            sou.display();
            s = sou.sB.toString();      
            System.out.print(s);
            System.out.println();
        }
    }    
}

/*
testy:

wejscie:
3
3,1,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,2,1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,4,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56

poprawne wyjscie:
Album,Year,Songs,Length
Unlimited Love,2022,17,73
Stadium Arcadium,2006,28,122
Californication,1999,15,56

Year,Album,Songs,Length
1999,Californication,15,56
2006,Stadium Arcadium,28,122
2022,Unlimited Love,17,73

Length,Album,Year,Songs
122,Stadium Arcadium,2006,28
73,Unlimited Love,2022,17
56,Californication,1999,15

wejscie:
4
10,1,1
Album,Songs
E,5
H,8
A,1
C,3
G,7
D,4
B,2
J,10
F,6
I,9
10,1,-1
Album,Songs
E,5
H,8
A,1
C,3
G,7
D,4
B,2
J,10
F,6
I,9
10,2,1
Album,Songs
E,5
H,8
A,1
C,3
G,7
D,4
B,2
J,10
F,6
I,9
10,2,-1
Album,Songs
E,5
H,8
A,1
C,3
G,7
D,4
B,2
J,10
F,6
I,9

poprawne wyjscie:
Album,Songs
J,10
I,9
H,8
G,7
F,6
E,5
D,4
C,3
B,2
A,1

Songs,Album
1,A
2,B
3,C
4,D
5,E
6,F
7,G
8,H
9,I
10,J

Songs,Album
10,J
9,I
8,H
7,G
6,F
5,E
4,D
3,C
2,B
1,A
*/