//author: YASHWANTH REDDY THADISINA
//date: 12th March, 2020
//modified by: VARUN KUMAR PALAKODETI
//date: 05th April, 2020
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class QuantumGen {
    public static void functionDeclaration(int qubit)
    {
        System.out.println("namespace Quantum.QSharpApplication"); 
        System.out.println("{\n\topen Microsoft.Quantum.Canon;\n\topen Microsoft.Quantum.Intrinsic;\n");
        int states = 1<<qubit;
        for(int i=0;i<states;i++)
        {
            System.out.println("\toperation Oracle" + i + " () : Unit");
            System.out.println("\t{");
            usingFunc(qubit,states);
            oracle(qubit,i);
            System.out.println("\t}");
        }
        System.out.println("}");
    }
    public static void usingFunc(int qubit,int states)
    {
        for(int i=0;i<states;i++)
        {
            System.out.println("\t\tmutable q" + i + "= 0;");
        }
    }
    public static void pauli(int qubit,int num)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(0);
        int t = qubit-1;
        int p = num;
        int u = 1;
        while(qubit>0)
        {
            if ((num & (1 << (u - 1))) == 0)
            {
                arr.add(t);
            }
            t--;
            qubit--;
            u++;
        }
        for(int i=arr.size()-1;i>-1;i--)
        {
            System.out.println("\t\t\t\tX(q["+arr.get(i)+"]);");
        }
        return;
    }
    public static void oracle(int qubit,int num)
    {
        int temp = 0;
        System.out.println("\t\tusing (q = Qubit["+(qubit+1)+"])");
        System.out.println("\t\t{");
        System.out.println("\t\tfor(g in 1..32)\n\t\t{");
        System.out.println("\t\t\tfor(i in 1..1000)");
        System.out.println("\t\t\t{");
        System.out.println("\t\t\t\tX(q["+qubit+"]);");
        System.out.println();
        uniformSuperPos(qubit);
        System.out.println();
        pauli(qubit,num);
        System.out.println();
        System.out.print("\t\t\t\tlet cq = [");
        while(temp != qubit)
        {
            System.out.print("q["+temp+"]");
            if(temp != qubit-1)
            {
                System.out.print(",");
            }
            temp++;
        }
        System.out.println("];");
        System.out.println("\t\t\t\tControlled X(cq,q["+qubit+"]);");
        System.out.println();
        //oracle begin
        pauli(qubit,num);
        System.out.println();
        uniformSuperPost(qubit);
        System.out.println();
        generateA(qubit);
        System.out.println();
        measurement(qubit);
        System.out.println("\t\t\t\tResetAll(q);");
        code(qubit);
        //oracle end
        System.out.println("\t\t\t}");
        System.out.println("\t\t}");
        message(qubit);
        System.out.println("\t\t}");
    }
    public static void message(int qubit)
    {
        int states = 1<<qubit;
        for(int i=0;i<states;i++)
        {
            System.out.println("\t\t\tMessage($\"State "+i+" -> {q"+i+"}\");");
        }
    }
    public static void code(int qubits) {
        int states = 1 << qubits;
        int j = qubits;
        Stack<String> stk = new Stack<String>();
        for (int i = 0; i < states; i++) {
            System.out.print("\t\t\t\tif(");
            int k = i;
            while (qubits != 0) {
                if (k % 2 == 0) {
                    stk.push("Zero");
                } else {
                    stk.push("One");
                }
                k = k/2;
                qubits--;
            }
            while (qubits != j) {
                System.out.print("r" + (++qubits) + " == " + stk.pop() + " ");
                if (stk.empty() == false)
                    System.out.print("and ");
            }
            System.out.println(")");
            System.out.println("\t\t\t\t{");
            System.out.println("\t\t\t\t\tset q" + i + " = q" + i + " + 1;");
            System.out.println("\t\t\t\t}");
        }
    }
    public static void uniformSuperPos(int qubit)
    {
        for(int i=0;i<qubit+1;i++)
        {
            System.out.println("\t\t\t\tH(q["+i+"]);");
        }
    }
    public static void uniformSuperPost(int qubit)
    {
        for(int i=0;i<qubit;i++)
        {
            System.out.println("\t\t\t\tH(q["+i+"]);");
        }
        System.out.println();
        for(int j=0;j<qubit;j++)
        {
            System.out.println("\t\t\t\tX(q["+j+"]);");
        }
    }
    public static void generateA(int qubit)
    {
        System.out.println("\t\t\t\tH(q["+(qubit-1)+"]);");
        System.out.print("\t\t\t\tlet ctrl = [");
        for(int i=0;i<qubit-1;i++)
        {
            System.out.print("q["+i+"]");
            if(i!=(qubit-2))
            {
                System.out.print(",");
            }
        }
        System.out.println("];");
        System.out.println("\t\t\t\tControlled X(ctrl,q["+(qubit-1)+"]);");
        System.out.println("\t\t\t\tH(q["+(qubit-1)+"]);");
        System.out.println();
        for(int k=0;k<qubit;k++)
        {
            System.out.println("\t\t\t\tX(q["+k+"]);");
        }
        System.out.println();
        for(int q=0;q<qubit;q++)
        {
            System.out.println("\t\t\t\tH(q["+q+"]);");
        }
    }
    public static void measurement(int qubit)
    {
        for(int i=0;i<qubit;i++)
        {
            System.out.println("\t\t\t\tlet r"+(i+1)+" = M(q["+i+"]);");
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int qubit = sc.nextInt();
        functionDeclaration(qubit);
    }
}