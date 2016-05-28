<%@page import="javax.swing.JOptionPane"%>
<%@page import="DAO.DAOAccount"%>
<%@page import="classes.Account"%>
<%

    Cookie[] cookies = request.getCookies();
    String passwordHasheada = null;
    String username = null;
    Account user;
    DAOAccount auth = new DAOAccount();
    boolean login = false;

    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            //JOptionPane.showMessageDialog(null, new Gson().toJson(cookies[i]).toString());

            if (cookies[i].getName().equals("username")) {
                username = cookies[i].getValue().toString();
                //JOptionPane.showMessageDialog(null, "if username" + new Gson().toJson(cookies[i]).toString());
            }
            if (cookies[i].getName().equals("password")) {
                passwordHasheada = cookies[i].getValue().toString();
                //JOptionPane.showMessageDialog(null, "if password" + new Gson().toJson(cookies[i]).toString());
            }
        }

        if ((username != null) && (passwordHasheada != null)) {
            user = auth.getAccount(username);
            //JOptionPane.showMessageDialog(null, "objeto user" + new Gson().toJson(user));

            if (user.getUsername().equals(username) && (user.getPassword().equals(passwordHasheada))) {
                login = true;
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "cookie é null");
    }

    if (login == false) {
        response.sendRedirect("index.jsp?msg=Falha de autenticação header");
    }

%>