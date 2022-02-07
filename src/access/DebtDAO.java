/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package access;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import model.Debt;
import model.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import structures.MyArrayList;

public class DebtDAO {
    private static final JSONParser jsonParser = new JSONParser();
    private static final File f = new File("data/debts.json");
    private static final File fIndex = new File("data/lastIndex.json");
    
    public MyArrayList<Debt> getByCategoryDebt(int idCategory){
        MyArrayList<Debt> arrayDebt = new MyArrayList();
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=sortJson((JSONArray) obj);
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson =(JSONObject) jsonArr.get(i);
                int category = Integer.parseInt(debtJson.get("idCategory").toString());
                if (idCategory==category){
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
            }
        }catch(Exception e){
            System.out.println("message ERROR: "+e.getMessage());
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
                            Float.parseFloat(debtJson.get("moneyToPaid").toString()),
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
    
    public JSONArray sortJson(JSONArray JSONa){
        Collections.sort(JSONa,new Comparator<JSONObject>(){
                 public int compare(JSONObject c1,JSONObject c2){
                       int n = Integer.parseInt(c1.get("id").toString());
                       int m = Integer.parseInt(c2.get("id").toString());
                       return new Integer(n).compareTo(new Integer(m));
                 }});
        return JSONa;
    }
    
    public MyArrayList<Debt> getAllDebt(){
        MyArrayList<Debt> arrayDebt = new MyArrayList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=sortJson((JSONArray) jsonParser.parse(fr));
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
    
    public MyArrayList<Debt> getAllDebtWithOut100Percent(){
        MyArrayList<Debt> arrayDebt = new MyArrayList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=sortJson((JSONArray) jsonParser.parse(fr));
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson=(JSONObject)jsonArr.get(i);
                if(Integer.parseInt(debtJson.get("percent").toString())!=100){
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
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return arrayDebt;
    }
    
    public void insertDebt(Debt debt, boolean flag){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject debtJson=new JSONObject();
            int id;
            if(flag)
                id = debt.getId();
            else
                id = getLastIndex();
            
            debtJson.put("id", id);
            debtJson.put("name",debt.getName());
            debtJson.put("moneyToPaid",debt.getMoneyToPaid());
            debtJson.put("startDate",debt.getStartDate());
            debtJson.put("numQuotas",debt.getNumQuotas());
            debtJson.put("periodicity",debt.getPeriodicity());
            debtJson.put("description",debt.getDescription());
            debtJson.put("idCategory",debt.getIdCategory());
            debtJson.put("percent",debt.getPercent());
            CategoryDAO.incrementCount(debt.getIdCategory());
            debt.setId(id);
            calculateEvents(debt);
            arrayJson.add(debtJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            updateIndex();
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    private void calculateEvents(Debt debt){
        EventDAO eventDAO=new EventDAO();
        float days = 0;
        float frac = debt.getMoneyToPaid()/(float)debt.getNumQuotas();
        switch(debt.getPeriodicity().toLowerCase()){
            case "semanal" -> {
                days=7;
            }
            case "mensual" -> {
                days=(float)30.417;
            }
            case "anual" -> {
                days=(float)365.2425;
            }
            case "semestral" -> {
                days=(float)182.62125;
            }
            default -> {
            }
        }
        String newDate = calculateDate(debt.getStartDate(), days);
        for(int i=0; i<debt.getNumQuotas(); i++){
            eventDAO.insertEvent(new Event(0,debt.getId(), frac, newDate, i+1));
            newDate=(calculateDate(newDate,days));
        }
    }
    
    private static String calculateDate(String date, float days){
        String[] d = date.split("-");
        Float temp;
        days += Integer.parseInt(d[0])*365.2425+(Integer.parseInt(d[1])-1)*30.417+Integer.parseInt(d[2]);
        temp=(float)(days/365.2425);
        days%=365.2425;
        int anios=temp.intValue();
        temp = (float)(days/30.417);
        days%=30.417;
        int meses=temp.intValue();
        if(temp-temp.intValue()>0)meses++;
        if(days==0)days++;
        return anios+"-"+meses+"-"+(int)days;
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
    
    public static int getLastIndex(){
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
            if(Integer.parseInt(tempJson.get("percent").toString())!=100){
                CategoryDAO.decrementCount(Integer.parseInt(tempJson.get("idCategory").toString()));
                new EventDAO().deleteAllByIdDebtEvent(id);
            }
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
            if(debt.getPercent()==0){
                new EventDAO().deleteAllByIdDebtEvent(debt.getId());
                calculateEvents(debt);
            }else if(debt.getPercent()==100){
                CategoryDAO.decrementCount(debt.getIdCategory());
            }
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==debt.getId()){
                    if(Integer.parseInt(tempJson.get("idCategory").toString())!=debt.getIdCategory()){
                        CategoryDAO.incrementCount(debt.getIdCategory());
                        CategoryDAO.decrementCount(Integer.parseInt(tempJson.get("idCategory").toString()));
                    }
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
    
    static void updatePercentDebt(int id, float value, int cuota) {
        DebtDAO DAO = new DebtDAO();
        Debt debt = DAO.getByIdDebt(id);
        if(debt==null)return;
        float temp = (value*cuota)/debt.getMoneyToPaid();
        int per=Math.round(temp*100);
        debt.setPercent(per);
        DAO.updateDebt(debt);
    }
    
    /*public static void main(String[] args) {
        new DebtDAO().insertDebt(new Debt(DebtDAO.getLastIndex(),"Carro 4k",(float)100.00,"2021-11-29",5,"Mensual","Prueba",0,0), true);
        //new DebtDAO().insertDebt(new Debt(1,"PRUEBAA",(float)500.00,"2021-11-30",7,"Mensual","Prueba",0,0));
    }*/
}
