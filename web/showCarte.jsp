<%-- 
    Document   : ShowCarte
    Created on : 19 oct. 2023, 20:23:04
    Author     : Tafitasoa-P15B-140
--%>

<%@page import="dtoFoncier.Detailtany"%>
<%@page import="java.util.List"%>
<%@page import="dtoFoncier.Tany"%>
<%@page import="modelsNet.Personne"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int cin = Integer.parseInt(request.getParameter("cin"));
    Personne personne = (Personne) request.getAttribute("personne");
    Tany tany = (Tany) request.getAttribute("tany");
    List<Detailtany> detail_list = (List<Detailtany>) request.getAttribute("detailTany");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css">
        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="css/carte.css">
        <title>Carte</title>
    </head>
    <body>
        <div class="gauche">
            <h2>Proprietaire</h2>
            <p>Nom : <b>${personne.nom}</b></p>
            <p>Prenoms : <b>${personne.prenoms}</b></p>
            <h3>A propos de Tany</h3>
            <p>Superficie : <b><%= tany.getSurface() %></b></p>
            <p>Prix : <b><%= tany.getTotalprix() %></b></p>
            <h3>Bornes</h3>
            <ul>
                <% for (Detailtany detail : detail_list) { %>
                    <li>Latitude : <%= detail.getLatitude() %>, Longitude : <%= detail.getLongitude() %></li>
                <% } %>
            </ul>
            <h3>Additionner un borne</h3>
            <form action="<%= request.getContextPath() %>/AddBorneServlet" >
                <input type="hidden" name="cin" value="<%= cin %>">
                <input type="hidden" name="idtany" value="<%= tany.getId()%>">
                <div id="group">
                    <div class="form-group">
                            Latitude : <input type="number" step="any" name="latitude[]" required>
                            Longitude : <input type="number" step="any" name="longitude[]" required>
                            <button type="button" name="ajouter" class="btn-add" onclick="ajouterCoordonnees()">
                                <i class="fas fa-plus"></i>
                            </button>
                    </div>
                </div>
                <input type="submit" value="Ajouter" class="ajouter"> 
            </form>
            <a href="<%= request.getContextPath() %>/Huhuservlet?cin=<%= cin %>">RETOUR</a>
        </div>
        <div class="droite" id="map"></div>
        <script>
            
            function distancePoints(lat1, lon1, lat2, lon2) {
                const R = 6371; // Rayon de la Terre en kilomètres
                const dLat = (lat2 - lat1) * (Math.PI / 180);
                const dLon = (lon2 - lon1) * (Math.PI / 180);
                const a =
                    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(lat1 * (Math.PI / 180)) * Math.cos(lat2 * (Math.PI / 180)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                const distance = R * c; // Distance en kilomètres
                return distance;
            }
            function findNearestPoint(start, points) {
                var nearestPoint = null;
                var minDistance = Infinity;

                points.forEach(function(point, index) {
                    var d = distancePoints(start[0], start[1], point[0], point[1]);
                    if (d < minDistance) {
                        minDistance = d;
                        nearestPoint = point;
                    }
                });

                return nearestPoint;
            }
            function reorderPointsByNearest(points) {
                var orderedPoints = [];
                var remainingPoints = points.slice();

                var start = remainingPoints.shift();
                orderedPoints.push(start);

                while (remainingPoints.length > 0) {
                    var nearestPoint = findNearestPoint(start, remainingPoints);
                    orderedPoints.push(nearestPoint);
                    start = nearestPoint;
                    remainingPoints = remainingPoints.filter(point => point !== nearestPoint);
                }

                // Ajoutez le premier point à la fin pour fermer le chemin
                orderedPoints.push(orderedPoints[0]);
                return orderedPoints;
            }
            var map = L.map('map').setView([-18.8792, 47.5079], 6);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 18,
            }).addTo(map);
            var color = '<%= tany.getColor() %>';
            var fillColor = '<%= tany.getFillcolor()%>';
            var coordinates = [
                <%
                    for (Detailtany detail : detail_list) {
                        out.print("[" + detail.getLatitude() + ", " + detail.getLongitude() + "],");
                    }
                %>
            ];
            var orderedPoints = reorderPointsByNearest(coordinates);

            var polygon = L.polygon(orderedPoints, {
                color: color,
                fillColor: fillColor,
                fillOpacity: 0.5,
            }).addTo(map);
            map.on('click',function(e){
                var latlng = e.latlng; 
                var latitude = latlng.lat;
                var longitude = latlng.lng;
                var marker = L.marker(latlng).addTo(map);
                marker.bindPopup("Latitude : " + latitude + "<br>Longitude : " + longitude).openPopup();
            });
        </script>
        <script>
            function ajouterCoordonnees() {
                var group = document.getElementById("group");
                var div_group = document.createElement("div");
                div_group.setAttribute("class","form-group");
                
                var nouvelleLatitude = document.createElement('input');
                nouvelleLatitude.type = 'number';
                nouvelleLatitude.step = 'any';
                nouvelleLatitude.name = 'latitude[]';
                nouvelleLatitude.required = true;
                var nouvelleLongitude = document.createElement('input');
                nouvelleLongitude.type = 'number';
                nouvelleLongitude.step = 'any';
                nouvelleLongitude.name = 'longitude[]';
                nouvelleLongitude.required = true;

                div_group.appendChild(document.createTextNode('Latitude : '));
                div_group.appendChild(nouvelleLatitude);
                div_group.appendChild(document.createTextNode(' Longitude : '));
                div_group.appendChild(nouvelleLongitude);
                group.appendChild(div_group);
            }
        </script>
    </body>
</html>
