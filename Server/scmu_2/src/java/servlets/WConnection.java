
package servlets;

import classes.WSockets;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Avelino
 */
public class WConnection extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        response.sendError(403);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void init() throws ServletException {
        
        System.out.println("----------");
        System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
        System.out.println("----------");

        ServerSocket s = null;

        try {

            s = new ServerSocket(3334);
            System.out.println("Servidor iniciado... WConnection (3333)");

            while (true) {
                Socket conexao = s.accept();
                conexao.setSoTimeout(1000 * 60 * 60 * 24);
                System.out.println("Conectou!");
                Thread t = new WSockets(conexao);
                t.start();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                System.exit(0);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
