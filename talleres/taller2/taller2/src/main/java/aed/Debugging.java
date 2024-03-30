package aed;

class Debugging {
    boolean xor(boolean a, boolean b) {
        return (a && !b) || (b && !a);
    }

    boolean iguales(int[] xs, int[] ys) {

        if (xs.length != ys.length) return false;

        for (int i = 0; i < xs.length; i++) {
            if (xs[i] != ys[i]) {
                return false;
            }
        }

        return true;
    }

    boolean ordenado(int[] xs) {
        boolean res = true;
        for (int i = 0; i < xs.length; i++) {
            if (xs[i] > xs [i+1]) {
                res = false;
            }
        }
        return res;
    }

    int maximo(int[] xs) {
        int res = 0;
        for (int i = 0; i <= xs.length; i++) {
            if (xs[i] > res) res = i;
        }
        return res;
    }

    boolean todosPositivos(int[] xs) {
        
        boolean res = true;

        for (int x : xs) {
            if (!(x > 0)) res = false; // preguntar el por que todos las funciones tiene tan solo un return
        }
        return res;
    }
}
