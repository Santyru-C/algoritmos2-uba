package aed;

class Funciones {
    int cuadrado(int x) {
        int res = x * x;
        return res;
    }

    double distancia(double x, double y) {
        double res = Math.sqrt((x * x) + (y * y));
        return res;
    }

    boolean esPar(int n) {
        boolean res = n % 2 == 0;
        return res;
    }

    boolean divideA(int x, int y) { // auxiliar para esBisiesto
        boolean res = y % x == 0;
        return res;
    }

    boolean esBisiesto(int n) {
        boolean res = (divideA(4, n) && !divideA(100, n)) || divideA(400, n);
        return res; 
    }

    int factorialIterativo(int n) {

        int res = 1;

        if (n == 0) {
            return res;
        }

        else {
            for (int i = 1; i <= n; i++) {
                res = res * i;
            }
        }
        return res;
    }

    int factorialRecursivo(int n) {
        int res = 1;

        if (n == 0) {
            return res;
        }
        else {
            res = n * factorialRecursivo(n - 1);
        }

        return res;
    }

    boolean esPrimo(int n) {
        double raizCuadDeN = Math.sqrt(n);

        if (n == 0 || n == 1) return false; // Nos encargamos del caso de 0 y 1 aparte

        for (int i = 2; i <= raizCuadDeN; i++) {
            if (divideA(i, n)) return false;
        }

        return true;
    }

    int sumatoria(int[] numeros) {
        int res = 0;

        for (int valor : numeros) {
            res += valor;
        }

        return res;
    }

    int busqueda(int[] numeros, int buscado) {

        int indiceActual = 0;

        for (int valor : numeros) {
            if (valor == buscado) {
                return indiceActual;
            }
            else {
                indiceActual += 1;
            }
        }

        return 0; //por alguna razon el compilador no toma solo al return de el for loop como valido
    }

    boolean tienePrimo(int[] numeros) {

        for (int valor : numeros) {
            if (esPrimo(valor)) return true;
        }
        return false;
    }

    boolean todosPares(int[] numeros) {

        for (int valor : numeros) {
            if (!esPar(valor)) return false;
        }
        return true;
    }

    boolean esPrefijo(String s1, String s2) {
        
        if (s1.length() > s2.length()) return false;

        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)) return false;
        }

        return true;
    }

    boolean esSufijo(String s1, String s2) {
        
        String s2Invertido = invertir(s2);
        return true

        //podemos usar esPrefijo invirtiendo la segunda cadena de caracteres.
    }

    String invertir(String s) {
        int largoS = s.length();
        String nuevo = "";

        for (int i = largoS - 1; i >= 0; i--) {
            nuevo += s.charAt(i);
        }

        return nuevo;
    }
}
