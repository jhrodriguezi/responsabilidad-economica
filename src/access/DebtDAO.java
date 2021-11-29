/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package access;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Debt;
import model.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import structures.ArrList;

public class DebtDAO {
    private final JSONParser jsonParser = new JSONParser();
    private final File f = new File("data/debts.json");
    private final File fIndex = new File("data/lastIndex.json");
    
    public ArrList<Debt> getByCategoryDebt(int idCategory){
        ArrList<Debt> arrayDebt = new ArrList();
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=(JSONArray) obj;
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson =(JSONObject) jsonArr.get(i);
                int category = Integer.parseInt(debtJson.get("idCategory").toString());
                if (idCategory==category){
                    arrayDebt.add(
                            new Debt(Integer.parseInt(debtJson.get("id").toString()),
                            debtJson.get("name").toString(),
                            Long.parseLong(debtJson.get("moneyToPaid").toString()),
                            debtJson.get("startDate").toString(),
                            Integer.parseInt(debtJson.get("numQuotas").toString()),
                            debtJson.get("periodicity").toString(),
                            debtJson.get("description").toString(),
                            Integer.parseInt(debtJson.get("idCategory").toString()),
                            Integer.parseInt(debtJson.get("percent").toString())));
                }
            }
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        return arrayDebt;
    }
    
    public Debt getByIdDebt(int id){
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=(JSONArray) obj;
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson =(JSONObject) jsonArr.get(i);
                if (Integer.parseInt(debtJson.get("id").toString())==id){
                    return( new Debt(Integer.parseInt(debtJson.get("id").toString()),
                            debtJson.get("name").toString(),
                            Long.parseLong(debtJson.get("moneyToPaid").toString()),
                            debtJson.get("startDate").toString(),
                            Integer.parseInt(debtJson.get("numQuotas").toString()),
                            debtJson.get("periodicity").toString(),
                            debtJson.get("description").toString(),
                            Integer.parseInt(debtJson.get("idCategory").toString()),
                            Integer.parseInt(debtJson.get("percent").toString())));
                }
            }
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        return null;
    }
    
    public ArrList<Debt> getAllDebt(){
        ArrList<Debt> arrayDebt = new ArrList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=(JSONArray) jsonParser.parse(fr);
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson=(JSONObject)jsonArr.get(i);
                arrayDebt.add(
                        new Debt(Integer.parseInt(debtJson.get("id").toString()),
                        debtJson.get("name").toString(),
                        Float.parseFloat(debtJson.get("moneyToPaid").toString()),
                        debtJson.get("startDate").toString(),
                        Integer.parseInt(debtJson.get("numQuotas").toString()),
                        debtJson.get("periodicity").toString(),
                        debtJson.get("description").toString(),
                        Integer.parseInt(debtJson.get("idCategory").toString()),
                        Integer.parseInt(debtJson.get("percent").toString())));
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return arrayDebt;
    }
    
    public void insertDebt(Debt debt){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject debtJson=new JSONObject();
            debtJson.put("id", getLastIndex());
            debtJson.put("name",debt.getName());
            debtJson.put("moneyToPaid",debt.getMoneyToPaid());
            debtJson.put("startDate",debt.getStartDate());
            debtJson.put("numQuotas",debt.getNumQuotas());
            debtJson.put("periodicity",debt.getPeriodicity());
            debtJson.put("description",debt.getDescription());
            debtJson.put("idCategory",debt.getIdCategory());
            debtJson.put("percent",debt.getPercent());
            arrayJson.add(debtJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            updateIndex();
            calculateEvents(debt);
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    private void calculateEvents(Debt debt){
        EventDAO eventDAO=new EventDAO();
        int days = 0;
        float frac = debt.getMoneyToPaid()/(float)debt.getNumQuotas();
        String newDate = calculateDate(debt.getStartDate(), days);
        switch(debt.getPeriodicity().toLowerCase()){
            case "semanal" -> {
                days=7;
            }
            case "mensual" -> {
                days=30;
            }
            case "anual" -> {
                days=365;
            }
            case "semestral" -> {
                days=183;
            }
            default -> {
            }
        }
        for(int i=0; i<debt.getNumQuotas(); i++){
            eventDAO.insertEvent(new Event(0,debt.getId(), frac, newDate, i+1));
            newDate=(calculateDate(newDate,days));
        }
    }
    
    private static String calculateDate(String date, int days){
        String[] d = date.split("-");
        days += Integer.parseInt(d[0])*365+Integer.parseInt(d[1])*30+Integer.parseInt(d[2]);
        int anios=days/365;
        int meses=(days%365)/30;
        if (meses==0)meses++;
        days=(days%365)%30;
        if(days==0)days++;
        return anios+"-"+meses+"-"+days;
    }
    
    public void updateIndex(){
        fIndex.setWritable(true);
        try{
            FileReader fr = new FileReader(fIndex);
            JSONObject indexJson= (JSONObject) jsonParser.parse(fr);
            indexJson.replace("idDebt", (Integer.parseInt(indexJson.get("idDebt").toString())+1));
            FileWriter fw=new FileWriter(fIndex);
            fw.write(indexJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public int getLastIndex(){
        try{
            FileReader fr = new FileReader(fIndex);
            JSONObject indexJson= (JSONObject) jsonParser.parse(fr);
            return Integer.parseInt(indexJson.get("idDebt").toString());
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return -1;
    }
    
    public void deleteByIdDebt(int id){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==id){
                    break;
                }
            }
            new EventDAO().deleteAllByIdDebtEvent(id);
            arrayJson.remove(tempJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    
    }
    
    public void deleteAllDebt(){
        f.setWritable(true);
        try{
            JSONArray arrayJson=new JSONArray();
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void updateDebt(Debt debt){
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
                    tempJson.replace("periodicity",debt.getPeriodicity());
                    tempJson.replace("description",debt.getDescription());
                    tempJson.replace("idCategory",debt.getIdCategory());
                    tempJson.replace("percent",debt.getPercent());
                    arrayJson.set(i, tempJson);
                    break;
                }
            }
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    
    }
    
    static void updatePercentDebt(int id, float value) {
        DebtDAO DAO = new DebtDAO();
        Debt debt = DAO.getByIdDebt(id);
        if(debt==null)return;
        int per = Math.round((debt.getMoneyToPaid()-(debt.getPercent()/100)*debt.getMoneyToPaid()-value)/debt.getMoneyToPaid())*100;
        debt.setPercent(per);
        DAO.updateDebt(debt);
    }
    
    public static void main(String[] args) {
        new DebtDAO().insertDebt(new Debt(1,"XD",(float)100.00,"2021-11-29",5,"Mensual","Prueba",0,0));
    }
}
