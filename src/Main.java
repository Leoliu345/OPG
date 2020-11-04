import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

import static java.lang.System.exit;

public class Main {

    public static String terminals = new String(new char[]{'#', '+', '*', '(', ')', 'i'});

    //0=error,1=less,2=more,3=equal
    public static int[][] matrix = {{3, 1, 1, 1, 1, 1}, {2, 2, 1, 1, 2, 1}, {2, 2, 2, 1, 2, 1}, {2, 1, 1, 1, 3, 1}, {2, 2, 2, 0, 2, 0}, {2, 2, 2, 0, 2, 0}};
    public static Stack<Character> symbols = new Stack<>();

    public static void main(String[] args) {
        String nextline;
        try {
            //测试用
            BufferedReader in = new BufferedReader(new FileReader("D:\\学习\\大三上\\compile\\OPG\\src\\test.txt"));
            //BufferedReader in = new BufferedReader(new FileReader(args[0]));
            while ((nextline = in.readLine()) != null) {
                String fixLine = "#" + nextline + "#";
                for (int i = 0; i < fixLine.length(); i++) {
                    char next = fixLine.charAt(i);
                    if (symbols.empty())
                        symbols.push(next);
                    else {
                        char old = peekTerminal();
                        int x = terminals.indexOf(old);
                        int y = terminals.indexOf(next);
                        if (x == -1 || y == -1) {
                            System.out.println("E");
                            return;
                        }
                        int judgePriority = matrix[x][y];
                        if (judgePriority == 1) {
                            symbols.push(next);
                            System.out.println("I" + next);
                        } else if (judgePriority == 2) {
                            stipulation();
                            i--;
                            System.out.println("R");
                        } else if (judgePriority == 3) {
                            if (next == '#') return;
                            symbols.push(next);
                            System.out.println("I"+next);
                        } else {
                            System.out.println("E");
                            return;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Character peekTerminal() {
        char first = symbols.peek();
        if (first == 'N') {
            symbols.pop();
            if (symbols.size() == 0) throw new EmptyStackException();
            char second = symbols.peek();
            symbols.push(first);
            return second;
        } else return first;
    }

    public static void stipulation() {
        char first = symbols.pop();
        if (first == 'i') {
            symbols.push('N');
            return;
        } else if (first == 'N') {
            char tmp = symbols.pop();
            if (tmp == '+' || tmp == '*')
                if (symbols.peek() == 'N')
                    return;

        } else if (first == ')') {
            if (symbols.pop() == 'N')
                if (symbols.pop() == '(') {
                    symbols.push('N');
                    return;
                }
        }

        System.out.println("RE");
        exit(0);
    }
}
