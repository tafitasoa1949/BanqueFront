<%-- 
    Document   : addTany
    Created on : 19 oct. 2023, 22:08:05
    Author     : Tafitasoa-P15B-140
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int cin = Integer.parseInt(request.getParameter("cin")); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajouter Tany</title>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css">
        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <link rel="stylesheet" href="css/add.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <script src='https://cdnjs.cloudflare.com/ajax/libs/Turf.js/0.0.124/turf.js'></script>
        


    </head>
    <body>
        <div class="gauche">
            <form action="<%= request.getContextPath() %>/AddTanyServlet">
                <h2>Ajouter un nouveau tany</h2>
                <div id="group">
                    <input type="hidden" name="cin" value="<%= cin %>">
                    <div class="form-group">
                        Latitude : <input type="number" step="any" name="latitude[]" required>
                        Longitude : <input type="number" step="any" name="longitude[]" required>
                        <button type="button" name="ajouter" class="btn-add" onclick="ajouterCoordonnees()">
                            <i class="fas fa-plus"></i>
                        </button>
                    </div>
                    <div class="form-group">
                        Latitude : <input type="number" step="any" name="latitude[]" required>
                        Longitude : <input type="number" step="any" name="longitude[]" required>
                    </div>
                    <div class="form-group">
                        Latitude : <input type="number" step="any" name="latitude[]" required>
                        Longitude : <input type="number" step="any" name="longitude[]" required>
                    </div>
                    
                </div>
                <div>
                    <h2>A propos</h2>
                    <div class="form-group">
                        Prix U: <input type="number" step="any" name="prixUnitaire" placeholder="Par mètre carré" value="100">
                        Prix Total : <input type="number" step="any" name="prixTotal" value="2000">
                    </div>
                    <div class="form-group">
                        Color : <input type="text"  name="color" value="Red">
                        Fillcolor : <input type="text" name="fillcolor" value="Blue">
                    </div>
                </div>
                <input type="submit" value="Ajouter" class="ajouter"> 
            </form>
            <form action="<%= request.getContextPath() %>/AddTanyCarteServlet">
                <h3>Coordonnées clique sur la carte</h3>
                <%
                    String cin_retour = (String)request.getAttribute("cin");
                    if (cin_retour != null) { 
                    int cin_valeur = Integer.parseInt(cin_retour);
                %>
                    <input type="hidden" name="cin" value="<%= cin_valeur %>">
                <% } else{ %>
                    <input type="hidden" name="cin" value="<%= cin %>">
                <% } %>
                <input type="hidden" name="counteur" value="0">
                <div id="coordonneesCarte">
                </div>
                <div id="valider"></div>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) { %>
                    <div class="error-message" style="color:red">
                        <%= errorMessage %>
                    </div>
                <% } %>
            </form>
            <div id="huhu"></div>
            <a href="<%= request.getContextPath() %>/Huhuservlet?cin=<%= cin %>">RETOUR</a>
        </div>
        <div class="droite" id="map"></div>
        <script>
            var divcoordonnees = document.getElementById("coordonneesCarte");
            var valider = document.getElementById("valider");
            var huhu = document.getElementById("huhu");
            var counteurInput = document.querySelector('input[name="counteur"]');
            var map = L.map('map').setView([-18.8792, 47.5079], 6);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 18,
            }).addTo(map);
            //
            var count = 0;
            var polyline = null;
            var pointsArray = [];
            map.on('click',function(e){
                //point coordonnees
                var latlng = e.latlng; 
                var latitude = latlng.lat;
                var longitude = latlng.lng;
                //console.log("Latitude"+latitude);
                //console.log("Longitude"+longitude);
                // Créez un objet point avec les coordonnées
                var point = { latitude: latitude, longitude: longitude };

                // Ajoutez le point au tableau
                pointsArray.push(point);
                
                //Latitude
                var inputLatitude = document.createElement('input');
                inputLatitude.type = 'hidden';
                inputLatitude.step = 'any';
                inputLatitude.name = 'latitude[]';
                inputLatitude.value = latitude;
                divcoordonnees.appendChild(inputLatitude);
                //Longitude
                var inputLongitude = document.createElement('input');
                inputLongitude.type = 'hidden';
                inputLongitude.step = 'any';
                inputLongitude.name = 'longitude[]';
                inputLongitude.value = longitude;
                divcoordonnees.appendChild(inputLongitude);
                //
                var p = document.createElement('p');
                var texteCoordonnees = 'Latitude : ' + latitude + ' Longitude : ' + longitude;
                p.textContent = texteCoordonnees;
                divcoordonnees.appendChild(p);
                //
                var marker = L.marker(latlng).addTo(map);
                marker.bindPopup("Latitude : " + latitude + "<br>Longitude : " + longitude).openPopup();
                // Créez une ligne pour relier les points
                if (polyline === null) {
                    polyline = L.polyline([latlng], { color: 'blue' }).addTo(map);
                } else {
                    var latlngs = polyline.getLatLngs();
                    latlngs.push(latlng);
                    polyline.setLatLngs(latlngs);
                }
    
                //bouton
                if(count == 0){
                    var bouttonValider = document.createElement('button');
                    bouttonValider.textContent ="Ajouter";
                    valider.appendChild(bouttonValider);
                }
                count++;
                counteurInput.value = count;
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
