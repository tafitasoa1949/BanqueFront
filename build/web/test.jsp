<%-- 
    Document   : test
    Created on : 22 oct. 2023, 16:43:24
    Author     : Tafitasoa-P15B-140
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajouter Tany</title>
        <link rel="stylesheet" href="leaflet/leaflet.css">
        <script src="leaflet/leaflet.js"></script>
        <link rel="stylesheet" href="css/add.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- comment <script src='https://unpkg.com/@turf/turf@6/turf.min.js'></script>-->
        <script src="https://unpkg.com/@turf/turf@6.3.0/turf.min.js"></script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div id="map" style="height: 500px;"></div>

    <script>
        // Créez une carte Leaflet
        var map = L.map('map').setView([51.505, -0.09], 13);

        // Créez deux polygones pour tester
        var polygon1 = [[51.508, -0.11], [51.508, -0.06], [51.51, -0.06], [51.51, -0.11], [51.508, -0.11]];
        var polygon2 = [[51.507, -0.08], [51.507, -0.07], [51.508, -0.07], [51.508, -0.08], [51.507, -0.08]];

        // Créez des objets GeoJSON pour les polygones
        var geojsonPolygon1 = turf.polygon([polygon1]);
        var geojsonPolygon2 = turf.polygon([polygon2]);

        // Ajoutez les polygones à la carte Leaflet
        L.geoJSON(geojsonPolygon1).addTo(map);
        L.geoJSON(geojsonPolygon2).addTo(map);

        // Vérifiez si une partie du polygone 1 est incluse dans le polygone 2
        var overlap = turf.booleanOverlap(geojsonPolygon1, geojsonPolygon2);

        if (overlap) {
            console.log("Une partie du polygone 1 est incluse dans le polygone 2.");
        } else {
            console.log("Aucune partie du polygone 1 n'est incluse dans le polygone 2.");
        }
    </script>
    </body>
</html>
