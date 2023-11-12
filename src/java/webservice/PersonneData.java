/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webservice;

import com.fasterxml.jackson.core.type.TypeReference;
import modelsNet.Personne;
import java.net.URL;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.NotFoundException;
import modelsNet.Allergie;
import modelsNet.AntecedantMaladie;
import modelsNet.Devise;
import modelsNet.Vola;
/**
 *
 * @author Tafitasoa-P15B-140
 */
public class PersonneData {
    public static Personne getPersonneService(int cin)throws Exception{
        Personne personne = null;
        try {
        String apiUrl = "http://localhost:7287/api/personne/getPersonne/" + cin;
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                personne = objectMapper.readValue(jsonResponse, Personne.class);
                in.close();
            } else if (conn.getResponseCode() == 204) {
                // Gérer le cas où aucune donnée n'est renvoyée, par exemple :
                throw new NotFoundException("Aucune personne trouvée pour le CIN : " + cin);
            } else {
                throw new IOException("La requête n'a pas abouti avec le code de réponse : " + conn.getResponseCode());
            }
        } catch (MalformedURLException e) {
            throw new IOException("URL mal formée : " + e.getMessage());
        }
        return personne;
    }
    public static List<Allergie> getListAllergie(int cin)throws Exception{
        List<Allergie> list_allergie = new ArrayList<>();
        try {
            String apiUrl = "http://localhost:7287/api/personne/getAllergiePersonne/"+cin;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                list_allergie = objectMapper.readValue(jsonResponse, new TypeReference<List<Allergie>>() {});
            }else{
                throw new IOException("La requête n'a pas abouti avec le code de réponse : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            throw new IOException("URL mal formée : " + e.getMessage());
        }
        return list_allergie;
    }
    public static List<AntecedantMaladie> getListAntecedantMaladie(int cin)throws Exception{
        List<AntecedantMaladie> list_antecedantMaladie = new ArrayList<>();
        try {
            String apiUrl = "http://localhost:7287/api/personne/getListAntecedantMaladie/"+cin;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                list_antecedantMaladie = objectMapper.readValue(jsonResponse, new TypeReference<List<AntecedantMaladie>>() {});

            }else{
                throw new IOException("La requête n'a pas abouti avec le code de réponse : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            throw new IOException("URL mal formée : " + e.getMessage());
        }
        return list_antecedantMaladie;
    }
    public static List<Vola> getListVola()throws Exception{
        List<Vola> list_vola = new ArrayList<>();
        try {
            String apiUrl = "http://localhost:7287/api/personne/getListVola";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                list_vola = objectMapper.readValue(jsonResponse, new TypeReference<List<Vola>>() {});

            }else{
                throw new IOException("La requête n'a pas abouti avec le code de réponse : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            throw new IOException("URL mal formée : " + e.getMessage());
        }
        return list_vola;
    }
    public static Devise getDeviseDay(int code)throws Exception{
        Devise devise = null;
        try {
            String apiUrl = "http://localhost:7287/api/personne/getDeviseDay/" + code;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                devise = objectMapper.readValue(jsonResponse, Devise.class);
                in.close();
            } else {
                throw new IOException("La requête n'a pas abouti avec le code de réponse : " + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            throw new IOException("URL mal formée : " + e.getMessage());
        }
        return devise;
    }
}
