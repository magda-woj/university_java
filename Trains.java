//Magda Wojtowicz - 1
/*idea dzialania programu: program wykonuje zadane w tresci zadania operacje na listachwiazanych : 
liscie pojedynczej, symbolizujacej liste pociagow oraz jednostronnych listach dwukierunkowych cyklicznych symbolizujacych pojedyncze pociagu
Wszystkie operacje poza Display i Trains dzialaja w czasie O(1) (nie liczac znajdowania zadanego pociagu)
Znajdujac (lub wykazujac ze nie istnieja) zadane pociagi przechodze przez liste tylko raz
*/
import java.util.Scanner;

class Wagon // klasa wagon
{
    String wagonName; //nazwa wagonu
    Wagon nextWagon; //referencja na nastepny wagon
    Wagon prevWagon; //referencja na poprzedni wagon

    Wagon(String W) //konstruktor
    {
        wagonName = W;
    }

    void swap() //zamienia nastepny i poprzedni wagon miejscami
    {
        Wagon bufor = nextWagon;
        nextWagon = prevWagon;
        prevWagon = bufor;
    }
}

public class Baca4 { //glowna klasa w niej znajduje sie lista pociagow
    public StringBuilder sB = new StringBuilder(); //StringBuilder do outputu
    public static Scanner scan = new Scanner(System.in);

    class Train //klasa pociag
    {
        String name; //nazwa pociagu
        Wagon firstWagon; //referencja na pierwszy wagon pociagu
        Train nextTrain; // referencja na kolejny pociag

        Train(String T, String W) //konstruktor - jego argumenty to nazwa pociagu i nazwa pierwszego wagonu
        {
            name = T; 
            Wagon wag = new Wagon(W); //tworzymy wagon
            wag.nextWagon = wag;
            wag.prevWagon = wag; //pociag to lista cykliczna wiec gdy jest jeden wagon, to jego poprzedni i nastepny wagon to on sam
            firstWagon = wag; //ustawiamy nowo stworzony wagon jako pierwszy
        }
            
            void TrainReverse() //zamienia kolejnosc wagonow w pociagu
        {
            firstWagon.swap(); //zamieniamy prev i next pierwszego wagonu 
            (firstWagon.nextWagon).swap(); //zamieniamy prev i next ostatniego wagonu
            firstWagon = firstWagon.nextWagon; // ostatni wagon to teraz pierwszy wagon
        }
        //po tej operacji prevy i nexty ostatniego i pierwszego wagonu oraz pierwszy wagon sa dobrze - mozemy spokojnie wykonywac operacje
        //reszta wagonow ma zle prevy i nexty, bedziemy to sprawdzac przy wypisywaniu i delach

        void TrainInsertFirst(String W) //wstawia nowy pierwszy wagon - argument funkcji to jego nazwa
        {
            Wagon wag = new Wagon(W); //tworzymy wagon
            wag.nextWagon = firstWagon; //jest przed pierwszym
            wag.prevWagon = firstWagon.prevWagon; //jest po ostatnim

            (wag.nextWagon).prevWagon = wag; 
            (wag.prevWagon).nextWagon = wag; //naprawiamy drugie strony wiazan

            firstWagon = wag; //jest teraz pierwszym wagonem
        }

        void TrainInsertLast(String W) //wstawia nowy ostatni wagon - argument funkcji to jego nazwa
        {
            Wagon wag = new Wagon(W); //tworzymy nowy wagon
            wag.nextWagon = firstWagon; //jest przed pierwszym
            wag.prevWagon = firstWagon.prevWagon; //jest po ostatnim

            (wag.nextWagon).prevWagon = wag; 
            (wag.prevWagon).nextWagon = wag; //naprawiamy drugie strony wiazan
        }

        void TrainDelFirst() //usuwa pierwszy wagon pociagu (odpali sie tylko jesli pociag ma ponad jeden wagon)
        {
            Wagon wag = firstWagon.nextWagon; // to bedzie nowy pierwszy wagon
            if((wag.nextWagon).prevWagon != wag) //warunek na odwrocony pociag
            {
                wag.swap(); //naprawiamy preva i nexta drugiego wagony
            }
            wag.prevWagon = firstWagon.prevWagon; //laczymy go z ostatnim
            (wag.prevWagon).nextWagon = wag; //laczymy ostatni z nim
            firstWagon = wag; //to teraz pierwszy wagon
        }

        void TrainDelLast() //usuwa ostatni wagon pociagu (odpali sie tylko jesli pociag ma ponad jeden wagon)
        {
            Wagon wag = (firstWagon.prevWagon).prevWagon; //to bedzie nowy ostatni wagon
            if(wag.prevWagon.nextWagon != wag) //warunek na odwrocony pociag
            {
                wag.swap(); //naprawiamy preva i nexta przedostatniego wagonu
            }
            wag.nextWagon = firstWagon; //laczymy go z pierwszym
            firstWagon.prevWagon = wag; //laczymy pierwszt z nim
        }

        void TrainDisplay() //wypisuje nazwe pociagu i po dwukropku nazwy wagonow pociagu
        {
            sB.append(name + ": ");
            Wagon wag = firstWagon; //zaczynamy od poczatku
            sB.append(wag.wagonName + " "); //wypisujemy jego nazwe
            if((wag.nextWagon).prevWagon != wag) //warunek na odwrocony pociag
            {
                wag.nextWagon.swap(); //naprawiamy
            }
            while(wag.nextWagon != firstWagon) //dopoki nie dojdziemy do konca
            {
                wag = wag.nextWagon; //nastepny wagon
                sB.append(wag.wagonName + " "); //wypisujemy jego nazwe
                if((wag.nextWagon).prevWagon != wag) //warunek na odwrocony pociag
                {
                    wag.nextWagon.swap(); //naprawiamy
                }
            }
            sB.append("\n");
        }
    }
   
    Train firstTrain; //referencja na pierwsz pociag 

    Baca4()
    {
        firstTrain = null; //na poczatku lista wagonow jest pusta
    }

    void NewTrain(String T, String W) //tworzy nowy pociag i dodaje go na poczatek listy (tylko jesli pociag jeszcze nie istnieje)
    {
        Train tr = firstTrain;
        while(tr != null) //przechodzimy po calej liscie pociagow
        {
            if(tr.name.equals(T)) //jesli w liscie juz jest pociag o takiej nazwie
            {
                sB.append("Train " + T + " already exists \n"); //komunikat
                return;
            }
            tr = tr.nextTrain; //nastepny pociag
        }
        Train t = new Train(T, W); //nowy pociag o danej nazwie T i jednym wagonie o nazwie W
        t.nextTrain = firstTrain; //jest przed dotychczasowym pierwszym pociagiem
        firstTrain = t; //pierwszy pociag to teraz on
    }

    void Trains() //wypisujemy pociagi z listy
    {
        sB.append("Trains: ");
        Train tr = firstTrain; //zaczynamy od pierwszego
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            sB.append(tr.name + " "); //dodaj imie do outputu
            tr = tr.nextTrain; //nastepny pociag
        }
        sB.append("\n");
    }

    void Reverse(String T) //odwroc pociag dany argumentem
    {
        Train tr = firstTrain; //szukamy pociagu zaczynamy od pierwszego
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            if(tr.name.equals(T)) //jesli znajdziemy
            {
                tr.TrainReverse(); //to odwracamy
                return; //koniec
            }
            tr = tr.nextTrain; //nastepny pociag
        }
        sB.append("Train " + T + " does not exist \n"); //jesli nie znajezlismy to komunikat
    }

    void InsertFirst(String T, String W) //wstawiamy pierwszy wagon o nazwie W do pociagu o nazwie T
    {
        Train tr = firstTrain; //szukamy pociagu zaczynamy od pierwszego
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            if(tr.name.equals(T)) //jesli znajdziemy
            {
                tr.TrainInsertFirst(W); //to wstawiamy
                return; // i koniec
            }
            tr = tr.nextTrain; //nastepny pociag
        }
        sB.append("Train " + T + " does not exist \n"); //jesli nie znajdziemy to komunikat
    }

    void InsertLast(String T, String W) //wstawiamy ostatni wagon o nazwie W do pociagu o nazwie T
    {
        Train tr = firstTrain; //szukamy pociagu zaczynamy od pierwszego
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            if(tr.name.equals(T)) //jesli znajdziemy
            {
                tr.TrainInsertLast(W); //to wstawiamy
                return; // i koniec
            }
            tr = tr.nextTrain; //nastepny pociag
        }
        sB.append("Train " + T + " does not exist \n"); //jesli nie znajdziemy to komunikat
    }

    void Delnext(Train prevtr) //pomocnicza funkcja - usuwa z listy pociag nastepujacy po pociagu ktory jest zadany argumentem
    {
        if(prevtr == null) //jesli to null, to usuwamy pierwszy pociag
        {
            firstTrain = firstTrain.nextTrain;
            return;
        }
        prevtr.nextTrain = (prevtr.nextTrain).nextTrain; //jesli nie to przepinamy nexta pociagu z argumentu o jeden dalej
    }

    void Union(String T1, String T2) //dolacza pociag o nazwie o nazwie T2 na koniec pociagu o nazwie T1 i usuwa pociag T2 z listy pociagow 
    {
        Train tr = firstTrain;
        Train tr1 = null;
        Train tr2 = null;
        Train prevtr = null;
        Train prevtr2 = null;
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            if(tr.name.equals(T1)) //jesli znajdziemy T1
            {
                tr1 = tr; //zapamietujemy
            }
            if(tr.name.equals(T2)) //jesli znajdziemy T2
            {
                tr2 = tr;
                prevtr2 = prevtr; //zapamietujemy jego i poprzedniego (zeby moc go usunac z listy)
            }
            prevtr = tr;
            tr = tr.nextTrain; //nastepny pociag
        }
        if(tr1 != null && tr2 != null) //jesli znalezlismy oba
        {
                Wagon first1 = tr1.firstWagon;
                Wagon first2 = tr2.firstWagon;
                Wagon last2 = first2.prevWagon; //zmienne pomocnicze zeby sie nie pogubic

                first2.prevWagon = first1.prevWagon; // poprzednik pierwszego wagonu T2 to ostatni wagon T1
                (first2.prevWagon).nextWagon = first2; // to samo w druga strone
                 
                first1.prevWagon = last2; //poprzednik pierwszego wagonu T1 to ostatni wagon T2
                last2.nextWagon = first1; // to samo w druga strone
                Delnext(prevtr2); //usuwamy T2 z listy
                return;
        }
        if(tr1 == null) //jesli nie znalezlismy pierwszego
        {
            sB.append("Train " + T1 + " does not exist \n"); //komunikat
            return;
        }
        sB.append("Train " + T2 + " does not exist \n"); //tu dojdziemy jesli znalezlismy pierwszy a drugiego nie
    }

    void DelLast(String T1, String T2) //usuwa ostatni wagon pociagu T1 i tworzy z niego pociag T2
    {
        Train tr = firstTrain;
        Train tr1 = null;
        Train prevtr = null;
        Train prevtr1 = null;
        while(tr != null)
        {
            if(tr.name.equals(T2)) //jesli T2 juz istnieje
            {
                sB.append("Train " + T2 + " already exists \n"); //komunikat
                return;
            }
            if(tr.name.equals(T1)) //znajdujemy T1
            {
                prevtr1 = prevtr;
                tr1 = tr; //zapamietujemy jego i poprzedniego zeby moc usunac
            }
            prevtr = tr;
            tr = tr.nextTrain; //nastepny pociag
        }
        if(tr1 == null) //jesli nie znalezlismy pierwszego
        {
            sB.append("Train " + T1 + " does not exist \n"); //komunikat
            return;
        }
        String s = ((tr1.firstWagon).prevWagon).wagonName; //zapamietujemy nazwe usuwanego wagonu
        if((tr1.firstWagon).nextWagon == tr1.firstWagon) //jeslu T1 ma jeden wagon
        {
            Delnext(prevtr1); //usuwamt go
            NewTrain(T2, s); //tworzymy T2 z zapamietanym wagonem
            return;
        }
        NewTrain(T2, s); //tworzymy T2 z zapamietanym wagonem
        tr1.TrainDelLast(); //usuwamy ostatni wagon z T1
        return;
    }

    void DelFirst(String T1, String T2) //usuwa pierwszy wagon pociagu T1 i tworzy z niego pociag T2
    {
        Train tr = firstTrain;
        Train tr1 = null;
        Train prevtr = null;
        Train prevtr1 = null;
        while(tr != null)
        {
            if(tr.name.equals(T2)) //jesli T2 juz istnije
            {
                sB.append("Train " + T2 + " already exists \n"); //komunikat
                return;
            }
            if(tr.name.equals(T1)) //szukamy T1
            {
                prevtr1 = prevtr;
                tr1 = tr; //zapamietujemy
            }
            prevtr = tr;
            tr = tr.nextTrain; //nastepny pociag
        }
        if(tr1 == null) //jesli nie bylo T1
        {
            sB.append("Train " + T1 + " does not exist \n"); //komunikat
            return;
        }
        String s = tr1.firstWagon.wagonName; //zapamietujemy nazwe wagonu ktory usuwamy
        if((tr1.firstWagon).nextWagon == tr1.firstWagon) //jesli T1 ma jeden wagon
        {
            Delnext(prevtr1); //usuwamy T1 z listy
            NewTrain(T2, s); //tworzymy dobrze T2
            return;
        }
        NewTrain(T2, s); //tworzymy dobrze T2
        tr1.TrainDelFirst(); //usuwamy pierwszy wagon w T1
    }

    void Display(String T) //wypisuje wagony pociagu T
    {
        Train tr = firstTrain;
        while(tr != null) //dopoki nie dojdziemy do konca
        {
            if(tr.name.equals(T)) //szukamy T
            {
                tr.TrainDisplay(); //wypisujemy jego wagony
                return;
            }
            tr = tr.nextTrain; //nastepny pociag
        }
        sB.append("Train " + T + " does not exist \n"); //jesli nie znalezlismy T - komunikat
    }
    
    public static void main( String [] args ) {

        int howMany = scan.nextInt();
        for(int i = 0; i < howMany; i++)
        {
            Baca4 sou = new Baca4();
            int n = scan.nextInt();
            String s = new String();
            s = scan.nextLine();
            for(int j = 0; j < n; j++)
            {
                s = scan.nextLine();
                String[] S = s.split(" ");
                if(s.contains("New"))
                {
                    sou.NewTrain(S[1], S[2]);
                }
                else if(s.contains("InsertFirst"))
                {
                    sou.InsertFirst(S[1], S[2]);
                }
                else if(s.contains("InsertLast"))
                {
                    sou.InsertLast(S[1], S[2]);
                }
                else if(s.contains("Display"))
                {
                    sou.Display(S[1]);
                }
                else if(s.contains("Trains"))
                {
                    sou.Trains();
                }
                else if(s.contains("Reverse"))
                {
                    sou.Reverse(S[1]);
                }
                else if(s.contains("Union"))
                {
                    sou.Union(S[1], S[2]);
                }
                else if(s.contains("DelFirst"))
                {
                    sou.DelFirst(S[1], S[2]);
                }
                else if(s.contains("DelLast"))
                {
                    sou.DelLast(S[1], S[2]);
                }
            }
            s = sou.sB.toString();
            System.out.print(s);
        } 
    }
}

/*
testy:

wejscie:
1
13
New T1 W1
Display T1
New T1 W0
InsertLast T1 W2
Display T1
DelLast T1 T2
Display T1
Display T2
DelFirst T1 T3
Display T3
Display T2
Display T1
Trains 

poprawne wyjscie:
T1: W1
Train T1 already exists
T1: W1 W2
T1: W1
T2: W2
T3: W1
T2: W2
Train T1 does not exist
Trains: T3 T2 

wejscie:
2
5
New A A1
InsertFirst A A2
DelFirst A A
Trains
Display A
3
New A A1
DelFirst A B
Trains

poprawne wyjscie:
Train A already exists
Trains: A
A: A2 A1
Trains: B

wejscie:
1
15
New T1 W0
InsertLast T1 W1
InsertLast T1 W2
Display T1
Reverse T1
Display T1
DelFirst T1 T2
InsertLast T2 Q1
Display T1
Display T2
Trains
Reverse T2
Display T2
Union T1 T2
Display T1

poprawne wyjscie:
T1: W0 W1 W2
T1: W2 W1 W0
T1: W1 W0
T2: W2 Q1
Trains: T2 T1
T2: Q1 W2
T1: W1 W0 Q1 W2
*/