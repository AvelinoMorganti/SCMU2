/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WServerSocket extends Thread {

    public WServerSocket() {
    }

    public void initialize() {
        ServerSocket s = null;

        try {

            s = new ServerSocket(4444);
            System.out.println("Servidor iniciado...- ServidorSockets (4444)");

            while (true) {
                Socket conexao = s.accept();
                conexao.setSoTimeout(1000 * 60 * 30);
                System.out.println("Conectou!");
                Thread t = new WSockets(conexao);
                t.start();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(s != null){
                    s.close();
                }
                System.exit(0);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    /*
     public static void main(String args[]) {
     List<Cidade> cidades = new ArrayList<>();

     try {
     InterfaceDAO<Cidade> cidadeDAO = new HibernateDAO<>(Cidade.class);

     Cidade jba = new Cidade(1, "Joaçaba");
     cidades.add(jba);

     Cidade luz = new Cidade(2, "Luzerna");
     cidades.add(luz);

     Cidade herval = new Cidade(3, "Herval d'Oeste");
     cidades.add(herval);

     } catch (Exception e) {
     System.err.println(e.getMessage());
     }

     ServerSocket s = null;

     try {

     s = new ServerSocket(2222);
     System.out.println("Servidor iniciado...");
     InterfaceDAO<Registro> registroDAO = new HibernateDAO<>(Registro.class);

     while (true) {

     Socket conexao = s.accept();
     conexao.setSoTimeout(30 * 60 * 1000);
     System.out.println("Conectou!");
     Thread t = new ServidorSockets(conexao, registroDAO, cidades);
     t.start();
     }
     } catch (IOException e) {
     System.err.println(e.getMessage());
     } finally {
     try {
     s.close();
     } catch (IOException ex) {
     System.err.println(ex.getMessage());
     }
     }

     }

     private Socket conexao;
     private InterfaceDAO<Registro> registroDAO;
     private Registro registro;
     private Cidade cidade;
     private List<Cidade> cidades = new ArrayList<>();
     private String nomeCidade;

     public ServidorSockets(Socket conexao, InterfaceDAO<Registro> registroDAO, List<Cidade> cidades) {
     this.conexao = conexao;
     this.registroDAO = registroDAO;
     this.cidades = cidades;
     }

     @Override
     public void run() {
     try {
     BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
     PrintStream saida = new PrintStream(conexao.getOutputStream());

     nomeCidade = entrada.readLine();
            
     System.out.println("Nome cidade: " + nomeCidade);

     if (nomeCidade == null) {
     throw new Exception("nome da cidade inválido");
     }

     switch (nomeCidade) {
     case "$jba":
     this.cidade = this.cidades.get(0);
     break;
     case "$luz":
     this.cidade = this.cidades.get(1);
     break;
     case "$hdo":
     this.cidade = this.cidades.get(2);
     break;
     default:
     throw new Exception("nome da cidade inválido");
     }

     saida.println("#A");

     String dados = entrada.readLine();
     String tokens[] = null;
     Float latitude = null;
     Float longitude = null;
     while (dados != null && !(dados.trim().equals(""))) {

     dados = dados.replace('$', ' ');
     tokens = dados.split(";");

     latitude = (((Float.parseFloat(tokens[0]) / 60) + Float.parseFloat(tokens[2])) * -1);
     longitude = (((Float.parseFloat(tokens[1]) / 60) + Float.parseFloat(tokens[3])) * -1);

     this.registro = new Registro();
     this.registro.setCidade(this.cidade);

     this.registro.setLatitude(latitude.toString());
     this.registro.setLongitude(longitude.toString());

     this.registro.setAltitude(Float.parseFloat(tokens[4]));
     this.registro.setTemperatura(Float.parseFloat(tokens[5]));
     this.registro.setUmidade(Float.parseFloat(tokens[6]));
     this.registro.setLuminosidade(Float.parseFloat(tokens[7]));
     this.registro.setChuva(Float.parseFloat(tokens[8]));
     this.registro.setQualidadeAr(Float.parseFloat(tokens[9]));

     this.registro.setDataRegistro(new Date());
     this.registro.setHoraRegistro(new Date());

     this.registroDAO.save(this.registro);

     dados = entrada.readLine();
     }

     } catch (IOException e) {

     System.err.println(e.getMessage());
     } catch (Exception ex) {
     Logger.getLogger(ServidorSockets.class.getName()).log(Level.SEVERE, null, ex);
     } finally {
     try {
     conexao.close();
     System.out.println("Conexao fechada");
     } catch (IOException ex) {
     System.err.println(ex.getMessage());
     }
     }
     }
     */
}
