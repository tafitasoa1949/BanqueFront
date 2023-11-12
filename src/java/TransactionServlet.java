/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.TanyDAO;
import dto.Compte;
import dto.Transaction;
import dtoFoncier.Tany;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelsNet.Devise;
import sgbd.Connexion;
import webservice.PersonneData;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import modelsNet.Allergie;
import modelsNet.AntecedantMaladie;
import modelsNet.Personne;
import modelsNet.Vola;
import service.BanqueBeanLocal;
import service.FoncierBeanLocal;
import sgbd.Connexion1;

/**
 *
 * @author Tafitasoa-P15B-140
 */
public class TransactionServlet extends HttpServlet {
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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            float montantConvert = 0;
            int cin = Integer.parseInt(request.getParameter("cin"));
            try {
                int compteEnvoyeur = Integer.parseInt(request.getParameter("compteEnvoyeur"));
                int compteReceveur = Integer.parseInt(request.getParameter("compteReceveur")); 
                float montant = Float.parseFloat(request.getParameter("montant"));
                int code = Integer.parseInt(request.getParameter("vola"));
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date dateAjourdhui = new java.sql.Date(utilDate.getTime());
                
                //
                Transaction transaction = new Transaction();
                transaction.setNumerobancairedepart(compteEnvoyeur);
                transaction.setCode(code);
                transaction.setNumerobancairedestinateur(compteReceveur);
                transaction.setDate_transaction(dateAjourdhui);
                
                //checkNumerobanciare
                Connection con = Connexion.getConnex();
                banqueBean.checkCompteBancaire(con, transaction.getNumerobancairedepart());
                banqueBean.checkCompteBancaire(con, transaction.getNumerobancairedestinateur());
                //convert devise
                Devise devise_day = PersonneData.getDeviseDay(code);
                float volaVente = 0; 
                if(code == 3){
                    transaction.setMontant(montant);
                    transaction.setAr(0);
                    /*out.println(transaction.getMontant());
                    out.println("AR"+transaction.getAr());*/
                }else{
                    out.println(devise_day.getVola().getNom());
                    montantConvert = banqueBean.convrertionAr(montant, devise_day.getCoursAr());
                    transaction.setMontant(montantConvert);
                    volaVente = montant * devise_day.getTauxvente();
                    transaction.setVolaVente(volaVente);
                    transaction.setAr(1);
                    //out.println(montantConvert);
                }
                //verifer devise montant
                /*out.println(transaction.getNumerobancairedepart());
                out.println(transaction.getNumerobancairedestinateur());
                out.println(transaction.getCode());
                out.println(transaction.getDate_transaction());*/
                banqueBean.faireTransaction(con, transaction);
                con.close();
                out.println("eto zw2");
                //
                Personne per = PersonneData.getPersonneService(cin);
                request.setAttribute("personne", per);
                List<Allergie> listAllergie = PersonneData.getListAllergie(cin);
                request.setAttribute("listAllergie", listAllergie);
                List<AntecedantMaladie> listAntecedantMaladie = PersonneData.getListAntecedantMaladie(cin);
                request.setAttribute("listAntecedantMaladie", listAntecedantMaladie);
                List<Vola> list_vola = PersonneData.getListVola();
                request.setAttribute("list_vola", list_vola);
                
                //java
                Connection con1 = Connexion.getConnex();
                List<Compte> lis_compte = banqueBean.getLiscompte(con,cin);
                request.setAttribute("lis_compte", lis_compte);
                con1.close();
                //java and mysql
                Connection connexMysql = Connexion1.getconnection();
                List<Tany> listTany = TanyDAO.getTanyByPersonne(connexMysql,cin);
                request.setAttribute("listTany", listTany);
                connexMysql.close();
                RequestDispatcher dispatcher = request.getRequestDispatcher("information.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                //e.printStackTrace(out);
                //csharp
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
                Connection connexMysql = Connexion1.getconnection();
                List<Tany> listTany = TanyDAO.getTanyByPersonne(connexMysql,cin);
                request.setAttribute("listTany", listTany);
                connexMysql.close();
                //
                request.setAttribute("errorMessage", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("information.jsp");
                dispatcher.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
