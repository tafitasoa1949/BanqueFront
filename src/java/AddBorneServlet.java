/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dtoFoncier.Detailtany;
import dtoFoncier.Tany;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.FoncierBeanLocal;
import sgbd.Connexion1;

/**
 *
 * @author Tafitasoa-P15B-140
 */
public class AddBorneServlet extends HttpServlet {
    @EJB
    private FoncierBeanLocal foncierBean;
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                int cin = Integer.parseInt(request.getParameter("cin"));
                int idtany = Integer.parseInt(request.getParameter("idtany"));
                double[] all_latitude = null;
                double[] all_longitude = null;
                String[] latitudes = request.getParameterValues("latitude[]");
                String[] longitudes = request.getParameterValues("longitude[]");
                //DetailTany
                int length = latitudes.length;
                all_latitude = new double[length];
                all_longitude = new double[length];
                if(latitudes != null && longitudes != null){
                    for (int i = 0; i < length; i++) {
                        all_latitude[i] = Double.parseDouble(latitudes[i]);
                        all_longitude[i] = Double.parseDouble(longitudes[i]);
                        //out.println("Latitude : "+all_latitude[i]+" et Longitude : "+all_longitude[i]);
                    }
                }
                //java and mysql
                Connection connexMysql = Connexion1.getconnection();
                for(int i=0 ; i<length ; i++){
                    Detailtany detail = new Detailtany();
                    detail.setIdtany(idtany);
                    detail.setLatitude(all_latitude[i]);
                    detail.setLongitude(all_longitude[i]);
                    //out.println("Latitude : "+listDetailTany[m].getLatitude()+"et longitude : "+listDetailTany[m].getLongitude());
                    foncierBean.insertDetailTany(connexMysql, detail);
                }
            } catch (Exception e) {
                e.printStackTrace(out);
            }
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
