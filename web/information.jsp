<%-- 
    Document   : information
    Created on : 10 oct. 2023, 08:24:59
    Author     : Tafitasoa-P15B-140
--%>

<%@page import="dtoFoncier.Tany"%>
<%@page import="dto.Compte"%>
<%@page import="modelsNet.Vola"%>
<%@page import="modelsNet.AntecedantMaladie"%>
<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page import="java.util.List"%>
<%@page import="modelsNet.Allergie"%>
<%@page import="modelsNet.Personne"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Personne personne = (Personne) request.getAttribute("personne");
    List<Allergie> listAllergie = (List<Allergie>) request.getAttribute("listAllergie");
    List<AntecedantMaladie> listAntecedantMaladie = (List<AntecedantMaladie>) request.getAttribute("listAntecedantMaladie");
    List<Vola> list_vola = (List<Vola>) request.getAttribute("list_vola");
    List<Compte> list_compte = (List<Compte>) request.getAttribute("lis_compte");
    List<Tany> listTany = (List<Tany>) request.getAttribute("listTany");
    int cin = Integer.parseInt(request.getParameter("cin"));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Information</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
        <style>
            .butt{
                padding: 10px;
                background-color: royalblue;
                color:white;
                border: none;
                margin-left: 90px;
                margin-bottom: 10px;
            }
            .form-group{
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <div class="info-container">
            <div class="info-personne">
                <h1>Informations personnelles</h1>
                <h3>Nom : ${personne.nom}</h3>
                <h3>Prenoms : ${personne.prenoms}</h3>
                <h3>Email : ${personne.email}</h3>
                <h3>Contact : ${personne.contact}</h3>
            </div>
        </div>
        <div class="info-container">
            <!-- Div pour la santé -->
            <div class="info-box">
                <h2>Santé</h2>
                <h4>Antecedant maladie</h4>
                <ul>
                    <% for(int i=0 ; i < listAntecedantMaladie.size() ; i++){ %>
                    <li><b><%= listAntecedantMaladie.get(i).getMaladie().getNom() %></b></li>
                        Affection : <b><%= listAntecedantMaladie.get(i).getAffection() %></b><br>
                        Symptomes : <b><%= listAntecedantMaladie.get(i).getMaladie().getSymptomes() %></b>
                    <% } %>
                </ul>
                
                <h4>Allergie</h4>
                <ul>
                    <% for(int i=0 ; i < listAllergie.size() ; i++) { %>
                        <li><%= listAllergie.get(i).getNom()%></li>
                    <% } %>
                </ul>
            </div>

            <!-- Div pour la banque -->
            <div class="info-box">
                <h2>Banque</h2>
                <h4>Compte</h4>
                <ul>
                    <% for(int i=0 ; i < list_compte.size() ; i++){ %>
                    <li>Nom : <b><%= list_compte.get(i).getBanque().getNom() %></b></li>
                        Numero bancaire : <b><%= list_compte.get(i).getNumerobancaire() %></b><br>
                        Solde : <b><%= list_compte.get(i).getSolde()%> Ar</b>
                    <% } %> 
                </ul>
            </div>

            <!-- Div pour le foncier -->
            <div class="info-box">
                <h2>Foncier</h2>
                <h4>Tany</h4>
                <ul>
                    <% for(int i=0 ; i < listTany.size() ; i++){ %>
                    <li>Surface : <b><%= listTany.get(i).getSurface()%></b></li>
                        Prix : <b><%= listTany.get(i).getTotalprix()%></b>
                        <a href="<%= request.getContextPath() %>/CarteServlet?idTany=<%= listTany.get(i).getId() %>&cin=<%= cin %>">Voir carte</a>
                    <% } %> 
                    <br>
                    <a href="<%= request.getContextPath() %>/addTany.jsp?cin=<%= cin %>">Ajouter</a>
                </ul>
            </div>
        </div>
        <div class="info-container">
            <div class="info-personne">
                <form action="<%= request.getContextPath() %>/TransactionServlet" method="GET">
                    <input type="hidden" name="cin" value="${personne.cin}"/>
                    <h1>Transactions</h1>
                    <div class="form-container">
                        <div class="form-group">
                            <label for="compteEnvoyeur">Expediteur :</label>
                            <input type="number" name="compteEnvoyeur" id="compteEnvoyeur" required value="12345">
                        </div>
                        <div class="form-group">
                            <label for="compteReceveur">Receveur :</label>
                            <input type="number" name="compteReceveur" id="compteReceveur" required value="7890">
                        </div>
                        <div class="form-group">
                            <label for="montant">Montant :</label>
                            <input type="number" step="any" name="montant" id="montant" required value="1200">
                        </div>
                        <div class="form-group">
                            <label for="vola">Devise:</label>
                            <select id="vola" name="vola">
                                <% for(int i=0 ;i<list_vola.size() ;i++){ %>
                                <option value="<%= list_vola.get(i).getCode() %>"><%= list_vola.get(i).getNom()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");
                        if (errorMessage != null) { %>
                        <div class="error-message" style="color:red">
                            <%= errorMessage %>
                        </div>
                    <% } %>
                    <input type="submit" value="Valider" class="butt" />
                </form>
            </div>
        </div>
    </body>
</html>
