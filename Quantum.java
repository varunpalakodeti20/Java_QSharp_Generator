//author: YASHWANTH REDDY THADISINA
//date: 12th March, 2020
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Quantum {
    public static void functionDeclaration(int qubit)
    {
        int states = 1<<qubit;
        for(int i=0;i<states;i++)
        {
            System.out.println("operation Oracle" + i + " () : Unit");
            System.out.println("{");
            usingFunc(qubit,states);
            oracle(qubit,i);
            System.out.println("}");
        }
    }
    public static void usingFunc(int qubit,int states)
    {
        for(int i=0;i<states;i++)
        {
            System.out.println("mutable q" + i + "= 0;");
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
            System.out.println("X(q["+arr.get(i)+"]);");
        }
        return;
    }
    public static void oracle(int qubit,int num)
    {
        int temp = 0;
        System.out.println("using (q = Qubit["+(qubit+1)+"])");
        System.out.println("{");
        System.out.println("for(i in 1..1000)");
        System.out.println("{");
        System.out.println("X(q["+qubit+"]);");
        System.out.println();
        uniformSuperPos(qubit);
        System.out.println();
        pauli(qubit,num);
        System.out.println();
        System.out.print("let cq = [");
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
        System.out.println("Controlled X(cq,q["+qubit+"]);");
        System.out.println();
        //oracle begin
        pauli(qubit,num);
        System.out.println();
        uniformSuperPost(qubit);
        System.out.println();
        generateA(qubit);
        System.out.println();
        measurement(qubit);
        System.out.println("ResetAll(q);");
        code(qubit);
        //oracle end
        System.out.println("}");
        message(qubit);
        System.out.println("}");
    }
    public static void message(int qubit)
    {
        int states = 1<<qubit;
        for(int i=0;i<states;i++)
        {
            System.out.println("Message($\"State "+i+" -> {q"+i+"}\");");
        }
    }
    public static void code(int qubits) {
        int states = 1 << qubits;
        int j = qubits;
        Stack<String> stk = new Stack<String>();
        for (int i = 0; i < states; i++) {
            System.out.print("if(");
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
            System.out.println("{");
            System.out.println("set q" + i + " = q" + i + " + 1;");
            System.out.println("}");
        }
    }
    public static void uniformSuperPos(int qubit)
    {
        for(int i=0;i<qubit+1;i++)
        {
            System.out.println("H(q["+i+"]);");
        }
    }
    public static void uniformSuperPost(int qubit)
    {
        for(int i=0;i<qubit;i++)
        {
            System.out.println("H(q["+i+"]);");
        }
        System.out.println();
        for(int j=0;j<qubit;j++)
        {
            System.out.println("X(q["+j+"]);");
        }
    }
    public static void generateA(int qubit)
    {
        System.out.println("H(q["+(qubit-1)+"]);");
        System.out.print("let ctrl = [");
        for(int i=0;i<qubit-1;i++)
        {
            System.out.print("q["+i+"]");
            if(i!=(qubit-2))
            {
                System.out.print(",");
            }
        }
        System.out.println("];");
        System.out.println("Controlled X(ctrl,q["+(qubit-1)+"]);");
        System.out.println("H(q["+(qubit-1)+"]);");
        System.out.println();
        for(int k=0;k<qubit;k++)
        {
            System.out.println("X(q["+k+"]);");
        }
        System.out.println();
        for(int q=0;q<qubit;q++)
        {
            System.out.println("H(q["+q+"]);");
        }
    }
    public static void measurement(int qubit)
    {
        for(int i=0;i<qubit;i++)
        {
            System.out.println("let r"+(i+1)+" = M(q["+i+"]);");
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int qubit = sc.nextInt();
        functionDeclaration(qubit);
    }
}
