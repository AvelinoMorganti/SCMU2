/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import DAO.DAOAccount;
import classes.Account;
import classes.hashPassword;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author avelino
 */
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            HttpSession session = null;
            String passwordRequest = request.getParameter("password");
            String usernameRequest = request.getParameter("username").toLowerCase();

            Account account = null;
            DAOAccount my = new DAOAccount();

            //Validação da quantidade de caracteres
            //Valida se o conteúdo é vazio ou null
            if (passwordRequest.length() > 35
                    || usernameRequest.length() > 35
                    || passwordRequest.equals("")
                    || usernameRequest.equals("")) {
                response.sendRedirect("index.jsp?msg=Login invalido");
            }

            //Pesquisa o usuário pelo nome
            account = my.getAccount(usernameRequest);

            //JOptionPane.showMessageDialog(null, "\n" + new Gson().toJson(account).toString());
            if (account != null) {
                //Seleciona a password hasheada do banco
                String passwordHasheada
                        = hashPassword.getSHA512(passwordRequest + account.getSalt());

                passwordRequest = "";

                //Confere se a password hasheada da base de dados é igual
                //a password hasheada do usuário
                if (passwordHasheada.equals(account.getPassword())
                        && usernameRequest.equals(account.getUsername())) {

                    //Verifica se a conta está bloqueada
                    if (account.isLocked()) {
                        if (account.getUsername().equals("root")) {
                            //Cria sessão..
                            session = request.getSession();
                            session.setAttribute("username_s", usernameRequest);
                            session.setAttribute("password_s", passwordHasheada);
                            //Cria Cookie
                            Cookie usernameCookie = new Cookie("username", usernameRequest);
                            Cookie passwordCookie = new Cookie("password", passwordHasheada);
                            usernameCookie.setMaxAge(60 * 60 * 24 * 7); //Store cookie for 1 week
                            passwordCookie.setMaxAge(60 * 60 * 24 * 7); //Store cookie for 1 week
                            response.addCookie(usernameCookie);
                            response.addCookie(passwordCookie);
                            //Redireciona para home
                            response.sendRedirect("home.jsp");
                        } else {
                            response.sendRedirect("index.jsp?msg=Conta bloqueada. Por favor, contate o administrador do sistema.");
                        }
                    } else {
                        //Cria sessão..
                        session = request.getSession();
                        session.setAttribute("username_s", usernameRequest);
                        session.setAttribute("password_s", passwordHasheada);
                        //Cria Cookie
                        Cookie usernameCookie = new Cookie("username", usernameRequest);
                        Cookie passwordCookie = new Cookie("password", passwordHasheada);
                        //usernameCookie.setMaxAge(60 * 60 * 24 * 7); //Store cookie for 1 week
                        //passwordCookie.setMaxAge(60 * 60 * 24 * 7); //Store cookie for 1 week
                        response.addCookie(usernameCookie);
                        response.addCookie(passwordCookie);
                        //JOptionPane.showMessageDialog(null, new Gson().toJson(usernameCookie).toString());
                        //JOptionPane.showMessageDialog(null, new Gson().toJson(passwordCookie).toString());
                        //Redireciona para home
                        response.sendRedirect("home.jsp");
                    }
                } else {
                    response.sendRedirect("index.jsp?msg=Login ou senha incorretos");
                }
            } else {
                response.sendRedirect("index.jsp?msg=Login ou senha incorretos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage() + "\n\n" + e.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
