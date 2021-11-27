/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package access;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import model.Debt;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DebtDAO {
    
    public ArrayList<Debt> getAllDebt(){
        JSONParser jsonParser = new JSONParser();
        File f = new File("data/debts.json");
        ArrayList<Debt> arrayDebt = new ArrayList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=(JSONArray) jsonParser.parse(fr);
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson=(JSONObject)jsonArr.get(i);
                arrayDebt.add(
                        new Debt(Integer.parseInt(debtJson.get("id").toString()),
                        debtJson.get("name").toString(),
                        Long.parseLong(debtJson.get("moneyToPaid").toString()),
                        debtJson.get("startDate").toString(),
                        Integer.parseInt(debtJson.get("numQuotas").toString()),
                        debtJson.get("description").toString(),
                        debtJson.get("category").toString()));
            }
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        return arrayDebt;
    }
    
    public void insertDebt(Debt debt){
        JSONParser jsonParser = new JSONParser();
        File f = new File("data/debts.json");
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject debtJson=new JSONObject();
            debtJson.put("id", debt.getId());
            debtJson.put("name",debt.getName());
            debtJson.put("moneyToPaid",debt.getMoneyToPaid());
            debtJson.put("startDate",debt.getStartDate());
            debtJson.put("numQuotas",debt.getNumQuotas());
            debtJson.put("description",debt.getDescription());
            debtJson.put("category",debt.getCategory());
            arrayJson.add(debtJson);
            FileWriter fw=new FileWriter("data/debts.json");
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void deleteDebt(Debt debt){
        JSONParser jsonParser = new JSONParser();
        File f = new File("data/debts.json");
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==debt.getId()){
                    break;
                }
            }
            arrayJson.remove(tempJson);
            FileWriter fw=new FileWriter("data/debts.json");
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
    
    }
    
    public void deleteAllDebt(){
        JSONParser jsonParser = new JSONParser();
        File f = new File("data/debts.json");
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson=new JSONArray();
            FileWriter fw=new FileWriter("data/debts.json");
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void updateDebt(Debt debt){
        JSONParser jsonParser = new JSONParser();
        File f = new File("data/debts.json");
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==debt.getId()){
                    tempJson.replace("name",debt.getName());
                    tempJson.replace("moneyToPaid",debt.getMoneyToPaid());
                    tempJson.replace("startDate",debt.getStartDate());
                    tempJson.replace("numQuotas",debt.getNumQuotas());
                    tempJson.replace("description",debt.getDescription());
                    tempJson.replace("category",debt.getCategory());
                    break;
                }
            }
            FileWriter fw=new FileWriter("data/debts.json");
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
    
    }
}
