import java.util.LinkedList;

public class PolyLink {
    private Node first = new Node(0, 0);  // sentinel
    private Node last  = first;

    private static class Node {
        int coef;
        int exp;
        Node next;
        Node(int coef, int exp) {
            this.coef = coef;
            this.exp  = exp;
        }
    }

    // 0    
    private PolyLink() { }

    // a * x^b
    public PolyLink(int coef, int exp) {
        last.next = new Node(coef, exp);
        last = last.next;
    }

    // return c = a + b
    public PolyLink plus(PolyLink b) {
        PolyLink a = this;
        PolyLink c = new PolyLink();
        Node x = a.first.next;
        Node y = b.first.next;
        while (x != null || y != null) {
            Node t = null;
            if      (x == null)     { t = new Node(y.coef, y.exp);  y = y.next; }
            else if (y == null)     { t = new Node(x.coef, x.exp);  x = x.next; }
            else if (x.exp > y.exp) { t = new Node(x.coef, x.exp);  x = x.next; } 
            else if (x.exp < y.exp) { t = new Node(y.coef, y.exp);  y = y.next; } 

            else {
                int coef = x.coef + y.coef;
                int exp  = x.exp;
                x = x.next;
                y = y.next;
                if (coef == 0) continue;
                t = new Node(coef, exp);
            }
        
            c.last.next = t;
            c.last = c.last.next;
        }
        return c;
    }


    // return c = a + b
    public PolyLink times(PolyLink b) {
        PolyLink a = this;
        PolyLink c = new PolyLink();
        for (Node x = a.first.next; x!= null; x = x.next) {
            PolyLink temp = new PolyLink();
            for (Node y = b.first.next; y!= null; y = y.next) {
                temp.last.next = new Node(x.coef * y.coef, x.exp + y.exp);
                temp.last = temp.last.next;
            }
            c = c.plus(temp);
        }
        return c;
    }


    // convert to string representation
    public String toString() {
        String s = "";
        for (Node x = first.next; x != null; x = x.next) {
            if      (x.coef > 0) s = s + " + " +   x.coef  + "x^" + x.exp;
            else if (x.coef < 0) s = s + " - " + (-x.coef) + "x^" + x.exp;
        }
        return s;
    }

    // test client
    public static void main(String[] args) { 
        PolyLink zero = new PolyLink(0, 0);

        PolyLink p1   = new PolyLink(4, 3);
        PolyLink p2   = new PolyLink(3, 2);
        PolyLink p3   = new PolyLink(1, 0);
        PolyLink p4   = new PolyLink(2, 1);
        PolyLink p    = p1.plus(p2).plus(p3).plus(p4);   // 4x^3 + 3x^2 + 1

        PolyLink q1   = new PolyLink(3, 2);
        PolyLink q2   = new PolyLink(5, 0);
        PolyLink q    = q1.plus(q2);                     // 3x^2 + 5

        PolyLink r    = p.plus(q);
//      PolyLink s    = p.times(q);
        System.out.println("zero(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
//        System.out.println("p(x) * q(x) = " + s);
//        System.out.println("0 - p(x)    = " + zero.minus(p));
//        System.out.println("p(3)        = " + p.evaluate(3));
//        System.out.println("p'(x)       = " + p.differentiate());
//        System.out.println("p''(x)      = " + p.differentiate().differentiate());
   }

}