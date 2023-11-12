/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dtoFoncier.Tany;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.TanyDAO;
import dto.Compte;
import dtoFoncier.Detailtany;
import java.sql.Connection;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import modelsNet.Allergie;
import modelsNet.AntecedantMaladie;
import modelsNet.Personne;
import modelsNet.Vola;
import service.BanqueBeanLocal;
import service.FoncierBeanLocal;
import sgbd.Connexion;
import sgbd.Connexion1;
import webservice.PersonneData;
/**
 *
 * @author Tafitasoa-P15B-140
 */
public class AddTanyServlet extends HttpServlet {
    @EJB
    private BanqueBeanLocal banqueBean;
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
                double[] all_latitude = null;
                double[] all_longitude = null;
                int cin = Integer.parseInt(request.getParameter("cin"));
                String[] latitudes = request.getParameterValues("latitude[]");
                String[] longitudes = request.getParameterValues("longitude[]");
                float prixUnitaire = Float.parseFloat(request.getParameter("prixUnitaire"));
                float prixTotal = Float.parseFloat(request.getParameter("prixTotal"));
                String color = request.getParameter("color");
                String fillcolor = request.getParameter("fillcolor");
                //Date actuel
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date dateAjourdhui = new java.sql.Date(utilDate.getTime());
                //Tany
                Tany tany = new Tany();
                tany.setCinproprietaire(cin);
                tany.setSurface(0);
                tany.setPrixunitaire(prixUnitaire);
                tany.setTotalprix(prixTotal);
                tany.setDate(dateAjourdhui);
                tany.setColor(color);
                tany.setFillcolor(fillcolor);
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
                Detailtany[] listDetailTany = new Detailtany[length];
                for(int m=0 ; m<length ; m++){
                    Detailtany detail = new Detailtany();
                    detail.setLatitude(all_latitude[m]);
                    detail.setLongitude(all_longitude[m]);
                    listDetailTany[m] = detail;
                    //out.println("Latitude : "+listDetailTany[m].getLatitude()+"et longitude : "+listDetailTany[m].getLongitude());
                }
                //java and mysql
                Connection connexMysql = Connexion1.getconnection();
                foncierBean.insertNew(connexMysql, tany, listDetailTany);
                connexMysql.close();
                //retour information
                Personne per = PersonneData.getPersonneService(cin);
                request.setAttribute("personne", per);
                List<Allergie> listAllergie = PersonneData.getListAllergie(cin);
                request.setAttribute("listAllergie", listAllergie);
                List<AntecedantMaladie> listAntecedantMaladie = PersonneData.getListAntecedantMaladie(cin);
                request.setAttribute("listAntecedantMaladie", listAntecedantMaladie);
                List<Vola> list_vola = PersonneData.getListVola();
                request.setAttribute("list_vola", list_vola);
                //java
                Connection con = Connexion.getConnex();
                List<Compte> lis_compte = banqueBean.getLiscompte(con,cin);
                request.setAttribute("lis_compte", lis_compte);
                con.close();
                //java mysql
                //java and mysql
                Connection connexMysqlOne = Connexion1.getconnection();
                List<Tany> listTany = TanyDAO.getTanyByPersonne(connexMysqlOne,cin);
                request.setAttribute("listTany", listTany);
                connexMysql.close();
                //
                RequestDispatcher dispatcher = request.getRequestDispatcher("information.jsp");
                dispatcher.forward(request, response);
                /*GeometryFactory geometryFactory = new GeometryFactory();

                // Coordonnées du polygone (e.xemple)
                Coordinate[] coordinates = new Coordinate[]{
                     new Coordinate(-18.8792, 47.2479),
                    new Coordinate(-18.87232, 47.7239),
                    new Coordinate(-19.3342, 47.3031),
                    new Coordinate(-18.8792, 47.2479)
                };


                // Créez un anneau linéaire (LinearRing) à partir des coordonnées
                LinearRing linearRing = geometryFactory.createLinearRing(coordinates);

                // Créez un polygone à partir de l'anneau linéaire
                Polygon polygon = geometryFactory.createPolygon(linearRing);

                // Calculez la surface du polygone
                double surface = polygon.getArea();
                double surfaceEnKilometresCarres = surface * 1e6;
                double surfaceArrondie = Math.round(surface * 100.0) / 100.0;
                out.println(surfaceArrondie);
                out.println("Kilometre"+surfaceEnKilometresCarres);*/
                //out.println("v"+cin);
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
