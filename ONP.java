// Magda Wojtowicz - 1
/* idea dzialania programu: program dokonuje konwersji wyrazen infiksowych na wyrazenia w odwrotnej notacji polskiej i na odwrot
jedna petla po dlugosci wyrazenia czysci je z niedozwolonych znakow
kolejna petla po dlugosci wyrazenia jednoczesnie sprawdza poprawnosc wyrazenia i dokonuje konwersji - zlozonosc n
program wykorzystuje algorytm "stacja rozrzadowa"
*/
import java.util.Scanner;

class Operation { //przechowuje wyrazenie arytmetyczne w stringu, priorytet najpozniej wykonywanego dzialania w wyrazeniu oraz strone w ktora jest laczny
    String s;
    int Order;
    char Asso;

    Operation()
    {
        s = new String();
    }
}

class OpStack { //stos wyrazen z klasy wyzej
    Operation [] operations;
    int top; //indeks szczytu stosu

    OpStack(int size) //konstruktor
    {
        operations = new Operation[size];
        top = -1;
    }

    void push(Operation x) //wklada wyrazenie na gore stosu
    {
        top++; 
        operations[top] = x;
    }

    void pop() //sciaga wyrazenie z gory stosu
    {
        top--;
    }

}
public class Baca3 {
    public static Scanner scan = new Scanner(System.in);

    char [] sCh; //tablica charow wyrazenia ktore konwertujemy
    String out;
    int n; //liczba znakow w wyrazeniu po wyczyszczeniu

    int [] opOrder; //priorytet operatorow
    boolean [] opAsso; //lacznosc operatorow lewostronna - true, prawostronna - false; indeks w taklicach to kod ascii symbolu operatora
    Baca3(int l)
    {
        sCh = new char[l];
        opOrder = new int[127];
        opAsso = new boolean[127];
        opOrder[33] = 1;
        opAsso[33] = false;
        opOrder[126] = 1;
        opAsso[126] = false;
        opOrder[94] = 2;
        opAsso[94] = false;
        opOrder[42] = 3;
        opAsso[42] = true;
        opOrder[47] = 3;
        opAsso[47] = true;
        opOrder[37] = 3;
        opAsso[37] = true;
        opOrder[43] = 4;
        opAsso[43] = true;
        opOrder[45] = 4;
        opAsso[45] = true;
        opOrder[60] = 5;
        opAsso[60] = true;
        opOrder[62] = 5;
        opAsso[62] = true;
        opOrder[63] = 6;
        opAsso[63] = true;
        opOrder[38] = 7;
        opAsso[38] = true;
        opOrder[124] = 8;
        opAsso[124] = true;
        opOrder[61] = 9;
        opAsso[61] = false;
        opOrder[40] = 10;
        opOrder[41] = 10;

        out = new String();
    }

    class Stack //stos wykorzystywany do konwersji
    {
        char [] elements;
        int top; // indeks szczytu stosu

        Stack()
        {
            elements = new char[n];
            top = -1;
        }
        
        void push(char x) //wklada znak na szczyt stosu
        {
            top++;
            elements[top] = x;
        }

        char pop() //sciaga znak ze szczytu stosu i zwraca go
        {
            top--;
            return elements[top+1];
        }
    }

    void DisplayONP() //wypisz wyrazenie
    {
        System.out.println(out);
    }

    void DisplayINF()
    {
        if(out == " error")
        {
            System.out.println(out);
        }
        else
        {
            for(int i = 0; i < out.length(); i++)
            {
                System.out.print(" " + out.charAt(i));
            }
            System.out.println();
        }
    }
    void CleanInf(String s) //czyszczenie wyrazenia w postaci INF
    {
        for(int i = 0; i < s.length(); i++)
        {
            char x = s.charAt(i);
            int c = x;
            if(c == 33 || c == 37 || c == 38 || (c >= 40 && c <= 43) || c == 45 || c == 47 || (c >= 60 && c <= 63) || c == 94 || (c >=97 && c <= 122) || c == 124 || c == 126)
            {
                sCh[n] = x;
                n++;
            }
        }
        return;
    }

    void CleanOnp(String s) // czyszczenie wyrazenia w postaci ONP
    {
        for(int i = 0; i < s.length(); i++)
        {
            char x = s.charAt(i);
            int c = x;
            if(c == 33 || c == 37 || c == 38 || (c >= 42 && c <= 43) || c == 45 || c == 47 || (c >= 60 && c <= 63) || c == 94 || (c >=97 && c <= 122) || c == 124 || c == 126)
            {
                sCh[n] = x;
                n++;
            }
        }
        return;
    }

    void toONP() //konwersja INF -> ONP
    {
        int Q = 0; //stan z automatu skonczonego
        int countOp = 0; //licznik operatorow do operandow
        int parenthesis = 0; //licznik nawiasow
        Stack lifo = new Stack(); //stos
        if(sCh[n-1] == '!' || sCh[n-1] == '~')
        {
            out = " error";
            return;
        }
        for(int i = 0; i < n; i++)
        {
            if(sCh[i] >= 97 && sCh[i] <= 122) //znak to operand
            {
                if(Q!=1) //graf z automatu
                {
                    Q = 1;
                }
                countOp++; //operand wiecej
                out +=" ";
                out += sCh[i]; //dodajemy znak do wyjscia
            }
            else if(sCh[i] == 40) //znak to nawias otwierajacy
            {
                if(Q==2) //graf z automatu
                {
                    Q = 0;
                }
                lifo.push(sCh[i]); //dodajemy nawias na stos 
                parenthesis++;
            }
            else if(sCh[i] == 41) //znak to nawias zamykajacy
            {
                parenthesis--;
                if(parenthesis < 0)
                {
                    out = " error";
                    return;
                }
                if(sCh[i-1] == 40)
                {
                    out = " error";
                    return;
                }
                if(Q==0) //dodane przeze mnie
                {
                    out = " error";
                    return;
                }
                
            
                while(lifo.elements[lifo.top] != 40) //sciagaj ze stosu i dawaj na wyjscie dopoki nie napotkasz nawiasu otwierajacego
                {
                    out +=" ";
                    out += lifo.pop();     
                }
                lifo.pop(); //usun nawias otwierajacy
            }
            else //znak to operator
            {
                if(sCh[i] == '~' || sCh[i] == '!') //graf z automatu
                {
                    if(Q != 1)
                    {
                        Q = 2;
                    }
                }
                else //graf z automatu
                {
                    countOp--;
                    if(countOp < 0) // jesli bylo za duzo operatorow
                {
                    out = " error";
                    return;
                }
                    if(Q == 1)
                    {
                        Q = 0;
                    }
                }
                if(opAsso[sCh[i]]) //lewostronny
                {
                    if(lifo.top < 0) //jak na stosie nic nie ma to dodaj na stos
                    {
                        lifo.push(sCh[i]);
                    }
                    else
                    {
                        while(opOrder[lifo.elements[lifo.top]] <= opOrder[sCh[i]]) //przenies ze stosu na wyjscie wszystkie operatory o wiekszym lub rownym priorytecie
                        {
                            out +=" ";
                            out += lifo.pop();
                            if(lifo.top < 0) //jesli stos sie skonczy przestan
                            {
                                break;
                            }
                        }
                        lifo.push(sCh[i]); //dodaj operator na stos
                    }
                }
                else //prawostronny
                {
                    if(lifo.top < 0) //jak na stosie nic nie ma to dodaj na stos
                    {
                        lifo.push(sCh[i]);
                    }
                    else
                    {
                        while(opOrder[lifo.elements[lifo.top]] < opOrder[sCh[i]]) //przenies ze stosu na wyjscie wszystkie operatory o wiekszym priorytecie
                        {
                            out +=" ";
                            out += lifo.pop();
                            if(lifo.top < 0) //jesli stos sie skonczy przestan
                            {
                                break;
                            }
                        }
                        lifo.push(sCh[i]); //dodaj operator na stos
                    }
                }
            }
        }
        if(Q != 1 || countOp != 1 || parenthesis != 0) //jesli nie zgadza sie liczba operatorow do operandow
        {
            out = " error";
            return;
        }
        while(lifo.top >= 0) //sciagaj ze stosu co na nim zostalo
        {
            out +=" ";
            out += lifo.pop();
        }
        
    }

    void toInf()
    {
        OpStack lifo = new OpStack(n); //stos
        int CountOp = 0; //licznik operatorow do operandow
        for(int i = 0; i < n; i++)
        {
            Operation oper = new Operation();
            if(sCh[i] >= 97 && sCh[i] <= 122) //znak to operand
            {
                CountOp++;
                 oper.s += sCh[i];
                 oper.Order = 0;
                 oper.Asso = 'N';
                 lifo.push(oper); //dodaj na stos
            }
            else if(sCh[i] == '~' || sCh[i] == '!') //operator jednoargumentowy
            {
                if(CountOp < 1) // jesli bylo za duzo operatorow
                {
                    out = " error";
                    return;
                }
                oper.s+=sCh[i];
                if((lifo.operations[lifo.top]).Order <= opOrder[sCh[i]])
                {
                    oper.s += (lifo.operations[lifo.top]).s; //budujemy wyrazenie - operator + ostatnie wyrazenie ze stosu
                }
                else
                {
                    oper.s += "(";
                    oper.s += (lifo.operations[lifo.top]).s;
                    oper.s += ")";
                }

                oper.Order = opOrder[sCh[i]];
                oper.Asso = 'R';

                lifo.pop(); //sciagamy poprzednie wyrazenie ze stosu
                lifo.push(oper); //dodajemy nwe wyrazenie
            }
            else // operator dwuargumentowy
            {
                CountOp--;
                if(CountOp < 1)
                {
                    out = " error";
                    return;
                }
                if(opAsso[sCh[i]]) // lewostronny
                {
                    if((lifo.operations[lifo.top - 1]).Order <= opOrder[sCh[i]])//jesli dzialanie "bardziej lewe go" wyrazenia z tych dwoch ktore bierzemy ma priorytet wiekszy lub rowny aktualnemu dzialaniu, mozemy je dac bez nawiasow
                    {
                        oper.s += (lifo.operations[lifo.top - 1]).s;
                    }
                    else //jesli nie to dodajemy nawiasy
                    {
                        oper.s += "(";
                        oper.s += (lifo.operations[lifo.top - 1]).s;
                        oper.s += ")";
                    }

                    oper.s+=sCh[i]; //laczymy wyrazenia aktualnym dzialaniem
                    
                    if((lifo.operations[lifo.top]).Order < opOrder[sCh[i]]) // to bez nawiasow
                    {
                        oper.s += (lifo.operations[lifo.top]).s;
                    }
                    else //w przeciwnym wypadku z nawiasami
                    {
                        oper.s += "(";
                        oper.s += (lifo.operations[lifo.top]).s;
                        oper.s += ")";
                    }
                    oper.Order = opOrder[sCh[i]];
                    oper.Asso = 'L';
                }
                else //prawostronny
                {
                    if((lifo.operations[lifo.top - 1]).Order < opOrder[sCh[i]]) //to bez nawiasow
                    {
                        oper.s += (lifo.operations[lifo.top -1]).s;
                    }
                    else //w przeciwnym wypadku z nawiasami
                    {
                        oper.s += "(";
                        oper.s += (lifo.operations[lifo.top - 1]).s;
                        oper.s += ")";
                    }
                    oper.s+=sCh[i]; //laczymy wyrazenia aktualnym operatorem

                    if((lifo.operations[lifo.top]).Order <= opOrder[sCh[i]]) //jesli dzialanie "bardziej prawego" wyrazenia z tych dwoch ktore bierzemy ma priorytet wiekszy lub rowny aktualnemu dzialaniu, mozemy je dac bez nawiasow
                    {
                        oper.s += (lifo.operations[lifo.top]).s;
                    }
                    else //w przeciwnym wypadku z nawiasami
                    {
                        oper.s += "(";
                        oper.s += (lifo.operations[lifo.top]).s;
                        oper.s += ")";
                    }

                    oper.Order = opOrder[sCh[i]];
                    oper.Asso = 'R';
                }
                lifo.pop(); 
                lifo.pop();
                lifo.push(oper); //sciagamy 2 wyrazenia i dodajemy nowe, (to one polaczone aktualnym operatorem)
            }
        }
        if(CountOp!= 1)
        {
            out = " error";
            return;
        }
        out = (lifo.operations[lifo.top]).s; //zwracamy to co jest na gorze stosu
    }
    

    public static void main( String [] args ) {
        int howMany = scan.nextInt();
        String s = new String();
        s = scan.nextLine();
        for(int i = 0; i < howMany; i++)
        {
            s = scan.nextLine();
            Baca3 sou = new Baca3(s.length());
            if(s.startsWith("I"))
            {
                sou.CleanInf(s);
                sou.toONP();
                System.out.print("ONP:");
                sou.DisplayONP();
            }
            else
            {
                sou.CleanOnp(s);
                sou.toInf();
                System.out.print("INF:");
                sou.DisplayINF();
            }

        }

    }
}
/*
testy: 
wejscie:

12
ONP: xabc**=
ONP: ab+a~a-+
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ( a,+ b)/..[c3
ONP: ( a,b,.).c;-,*
ONP: abc++def++g+++
INF: x=a=b=c^d^e
INF: (r+y)=a=(b+c)+d
INF: x=!(c>a & c<b)
INF: x=~~~a
ONP: xa~~~=

poprawne wyjscie:

INF: x = a * ( b * c )
INF: a + b + ( ~ a - a )
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: a b + c /
INF: a * ( b - c )
INF: error
ONP: x a b c d e ^ ^ = = =
ONP: r y + a b c + d + = =
ONP: x c a > c b < & ! =
ONP: x a ~ ~ ~ =
INF: x = ~ ~ ~ a

wejscie:

2
ONP: ab+c*!
ONP: abc**!

poprawne wyjscie:

INF: ! ( ( a + b ) * c )
INF: ! ( a * ( b * c ) )

wejscie:

14
INF: ((a
ONP: xabc**=
ONP: abc^^
INF: d/~p
INF: x=a*(b*c)
ONP: ab^c
INF: (a)
ONP: a~b~~+c~/~d~*~e~^~^~
INF: a+b+
ONP: xaab-c++=
ONP: dfghjklwertyuiop+-*^/<>=+-*^/<>=
INF: y-a*(b + x^v - e) / c + d / (~ p)
INF: y-a*(b + x^v - e) / c + d / ~ p
ONP: aa~*

poprawne wyjscie:

ONP: error
INF: x = a * ( b * c )
INF: a ^ b ^ c
ONP: d p ~ /
ONP: x a b c * * =
INF: error
ONP: a
INF: error
ONP: error
INF: x = a + ( a - b + c )
INF: error
ONP: y a b x v ^ + e - * c / - d p ~ / +
ONP: y a b x v ^ + e - * c / - d p ~ / +
INF: a * ~ a

wejscie:
2
INF: a++bc
ONP: x c a > c b < & ! =

poprawne wyjscie:
ONP: error
INF: x = ! ( c > a & c < b )
*/
