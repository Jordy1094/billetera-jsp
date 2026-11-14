package pe.edu.upn.billetera.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.upn.billetera.model.Billetera;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet(name = "BilleteraServlet", urlPatterns = {"/billetera"})
public class BilleteraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ATRIBUTO_BILLETERA = "billetera";
    private static final String VISTA = "/WEB-INF/views/billetera.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Billetera billetera = obtenerBilletera(request.getSession());
        request.setAttribute(ATRIBUTO_BILLETERA, billetera);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Billetera billetera = obtenerBilletera(request.getSession());

        String tipo = request.getParameter("tipo");
        String monto = request.getParameter("monto");

        String error = billetera.registrar(tipo, monto);

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("tipoIngresado", tipo);
            request.setAttribute("montoIngresado", monto);
            request.setAttribute("montoSolicitado", formatear(monto));
            request.setAttribute(ATRIBUTO_BILLETERA, billetera);
            request.getRequestDispatcher(VISTA).forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/billetera");
    }

    private Billetera obtenerBilletera(HttpSession session) {
        Billetera billetera = (Billetera) session.getAttribute(ATRIBUTO_BILLETERA);
        if (billetera == null) {
            billetera = new Billetera();
            session.setAttribute(ATRIBUTO_BILLETERA, billetera);
        }
        return billetera;
    }

    private String formatear(String monto) {
        try {
            return new BigDecimal(monto.trim().replace(',', '.')).setScale(2, RoundingMode.HALF_UP).toPlainString();
        } catch (RuntimeException e) {
            return monto;
        }
    }
}
