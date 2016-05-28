package classes;

public class testeteste {

    public static void main(String[] args) {

        /*
        
         1 ) A cada 1  byte  exibe no terminal ( BLOCO ! )
         2 ) A cada 21 bytes exibe no terminal ( BLOCO 2 )
         3 ) A cada 29 bytes exibe no terminal ( BLOCO 3 )
         4 ) A cada 35 bytes exibe no terminal ( BLOCO 4 )
         5 ) A cada 36 bytes exibe no terminal ( BLOCO 5 )
         */
        for (int i = 1; i < 72; i++) {
            

            if (i % 21 == 0) {
                System.out.println(i + " BLOCO 2");
                continue;
            }
            if (i % 29 == 0) {
                System.out.println(i + " BLOCO 3");
                continue;
            }
            if (i % 35 == 0) {
                System.out.println(i + " BLOCO 4");
                continue;
            }
            if (i % 36 == 0) {
                System.out.println(i + " BLOCO 5");
                continue;
            }
            if(i == 1){
                System.out.println(i + " BLOCO !");
            }
        }
        
    }

}
