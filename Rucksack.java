//Magda Wojtowicz - 1
/* idea dzialania programu:
Program rozwiazuje problem zapakowania plecaka do pelna przy uzyciu przedmiotow o danych wagach.
Zawiera dwie funkcje: rekurencyjna oraz iteracyjna, obie rozwiazuja ten sam problem. Funkcja iteracyjna symuluje dzialanie rekurencyjnej
uzywajac struktury stosu
*/
import java.util.Scanner;

class Stack {
    int[] Data; //stos do symulacji rekurencji w funkcji iteracyjnej
    int top; // indeks szczytu stosu

    Stack(int size)
    {
        Data = new int[size];
        top = -1;
    }

    void push(int x) //wklada wyrazenie na gore stosu
    {
        top++; 
        Data[top] = x;
    }

    int pop() //sciaga wyrazenie z gory stosu
    {
        top--;
        return Data[top +1];
    }
}

public class Baca5 {
    public static Scanner scan = new Scanner(System.in);
    private int[] A; //tablica zawierajaca wagi przedmiotow ktorymi mozna zaladowac plecak
    private int k; //liczba przedmiotow ktorymi mozna zaladowac plecak
    private int V; //pojemnosc plecaka
    private boolean[] rec; //tablica boolean o dlugosci rownej liczbie przedmiotow; true - pakujemy ten przedmiot; false - nie pakujemy
    boolean isSol; //czy da sie zapakowac
    Stack St;

    Baca5(int size, int _V) //konstruktor
    {
        V = _V;
        k = size;
        A = new int[size];
        for(int j = 0; j < k; j++)
        {
            A[j] = scan.nextInt();
        }
        rec = new boolean[k];
        isSol = false;
        St = new Stack(2*k+1);
    }

    void REC(int currSum, int i) //funkcja rozwiazujaca problem rekurencyjnie
    {
        currSum += A[i]; //dodajemy wage aktualnego przedmiotu do sumy "juz zapakowanej"
        if(currSum <= V) //jesli pojemnosc plecaka nie zostanie przekroczona
        {
            rec[i] = true; // to pakujemy ten przedmiot
            if(currSum == V) //jesli wypelnilismy plecak
            {
                isSol = true; //da sue go wypelnic
                return; //koniec
            }
            if(i < k-1) //jesli nie skonczyly sie nam przedmioty
            {
                REC(currSum, i+1); //wywolujemy funkcje dla nastepnego indeksu
            }
        }
        if(!isSol) //jesli nie zapakowalismy
        {
            currSum -= A[i]; //usuwamy aktualny przedmiot z sumy
            rec[i] = false; //wypakowujemy aktualny przedmiot z plecaka
            if(i < k-1) //jesli nie skonczyly sie przedmioty
            {
                REC(currSum, i+1); //wywolujemy funkcje dla nastepnego indeksu
            }
        }
        return;
    }

    void ITER()
    {
        isSol = false;
        int currSum = 0;
        int i = 0; //zaczynamy od pierwszego przedmiotu
        while(i < k) //dopoki mamy elementy
        {
            currSum += A[i]; //dodaj aktualny przedmiot do sumy
            if(currSum == V) //jesli wypelnilismy plecak
                {
                    St.push(i); //dodajemy indeks na stos
                    isSol = true; //da sie zapakowac
                    return; //koniec
                }
            else if(currSum <= V && i < k-1) //jesli mamy miejsce i nie skoncza sie nam przedmioty
            {
                    St.push(i); //dodajemy indeks na stos
                    i++; //nastepny przedmiot
            }
            else { //jesli przekroczylismy wage
                currSum -= A[i]; //nie pakujemy aktualnego przedmioty
                if(i < k-1) //jesli nie skonczyly sie przedmioty
                {
                    i++; //to przechodzimy do nastepnego
                }
                else //jesli sie skonczyly

                {
                    if(St.top == -1)
                    {
                        return;
                    }
                    i = St.pop();// to znaczy ze musimy zdjac ostatni wlozony na stos indeks przedmiotu
                    currSum -= A[i]; // i odjac jego wage od sumy
                    i++; //bierzemy nastepny przedmiot
                }
            }
        }
        return; //jesli skonczyly sie przedmioty to isSol dalej jest false wiec nic nie robimy
    }

    public static void main( String [] args ) {

        int howMany = scan.nextInt();
        for(int i = 0; i < howMany; i++)
        {
            StringBuilder sB = new StringBuilder();
            int V = scan.nextInt();
            int k = scan.nextInt();
            Baca5 sou = new Baca5(k, V);
            sou.REC(0,0);
            if(sou.isSol)
            {
                sB.append("REC: ");
                sB.append(V);
                sB.append(" = ");
                for(int j = 0; j < k; j++)
                {
                    if(sou.rec[j] == true)
                    {
                        sB.append(sou.A[j]);
                        sB.append(" ");
                    }
                }
                sB.append("\n");
            }
            else
            {
                sB.append("BRAK \n");
            }
            sou.ITER();
            if(sou.isSol)
            {
                sB.append("ITER: ");
                sB.append(V);
                sB.append(" = ");
                for(int j = 0; j <= sou.St.top; j++)
                {
                    sB.append(sou.A[sou.St.Data[j]]);
                    sB.append(" ");
                }
                sB.append("\n");
            }
            String s = sB.toString();
            System.out.print(s);
        }

    }
}

/*
testy:
--------------------------
wejscie:
2
20
5
11 8 7 6 5
21
3
5 6 7 

poprawne wyjscie:
REC: 20 = 8 7 5
ITER: 20 = 8 7 5
BRAK 
----------------------------------------------------------

wejscie:
1
100000
100
13491 54395 46203 61208 92210 26569 73081 80991 91660 64639 60440 76256 4540 14456 38076 27532 23892 23341 47028 4073 96974 25602 5281 43412 53653 75050 11805 6892 41360 19728 39011 57746 74084 80327 20503 23533 93518 40704 91494 97798 68404 74185 10077 93057 59598 15498 2016 8030 77097 40569 18301 12693 67274 41145 37185 30358 37978 27660 21078 25010 28278 41061 5492 56055 62267 80093 98068 26446 29512 76470 52913 41826 47174 3109 12848 80644 3846 8045 66051 27836 26804 25614 41443 20067 51906 63136 14546 50572 28375 20621 88187 81730 43490 82476 39105 7500 21289 49374 95095 97048

poprawne wyjscie:
REC:  100000 = 13491 46203 4540 5281 10077 2016 3846 14546
ITER: 100000 = 13491 46203 4540 5281 10077 2016 3846 14546
-----------------------------------------------------------

wejscie:
5
5
1
5
5
1
1
5
1
6
5
1
3
5
5
1 1 1 1 1

poprawne wyjcie:
REC: 5 = 5 
ITER: 5 = 5
BRAK 
BRAK 
BRAK
REC: 5 = 1 1 1 1 1
ITER: 5 = 1 1 1 1 1
------------------------------------------------------------
*/