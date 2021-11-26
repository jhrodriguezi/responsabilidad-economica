/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package access;

import java.io.FileReader;
import model.Debt;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DebtDAO {
    public ArrayList<Debt> getAllDebt(){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("data/debts.json"));
            JSONObject json= (JSONObject) obj;
            JSONArray jsonArr=(JSONArray) json.get("debts");
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        return null;
    }
}
