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
import structures.MyLinkedList;
import structures.QueueArray;

/**
 *
 * @author DELL
 */
public class EventDAO {
    private final JSONParser jsonParser = new JSONParser();
    private final File f = new File("data/events.json");
    private final File fIndex = new File("data/lastIndex.json");
    
    public MyLinkedList<Event> getByDebtEvent(Debt debt){
        MyLinkedList<Event> linkEvent = new MyLinkedList();
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=(JSONArray) obj;
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject event =(JSONObject) jsonArr.get(i);
                if (debt.getId()==Integer.parseInt(event.get("idDebt").toString())){
                    JSONObject debtJson=(JSONObject)jsonArr.get(i);
                    linkEvent.add(
                            new Event(Integer.parseInt(debtJson.get("id").toString()),
                            Integer.parseInt(debtJson.get("idDebt").toString()),
                            Float.parseFloat(debtJson.get("value").toString()),
                            debtJson.get("date").toString(),
                            Integer.parseInt(debtJson.get("quota").toString())));
                }
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return linkEvent;
    }
    
    public MyLinkedList<Event> getAllEvent(){
        MyLinkedList<Event> linkEvent = new MyLinkedList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=(JSONArray) jsonParser.parse(fr);
            Collections.sort(jsonArr,new Comparator<JSONObject>(){
                 public int compare(JSONObject c1,JSONObject c2){
                       String[] n = c1.get("date").toString().split("-");
                       String[] m = c2.get("date").toString().split("-");
                       int n_v = Integer.parseInt(n[0])*365+Integer.parseInt(n[1])*30+Integer.parseInt(n[2]);
                       int m_v = Integer.parseInt(m[0])*365+Integer.parseInt(m[1])*30+Integer.parseInt(m[2]);
                       return new Integer(n_v).compareTo(new Integer(m_v));
                 }});
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson=(JSONObject)jsonArr.get(i);
                linkEvent.add(
                            new Event(Integer.parseInt(debtJson.get("id").toString()),
                            Integer.parseInt(debtJson.get("idDebt").toString()),
                            Float.parseFloat(debtJson.get("value").toString()),
                            debtJson.get("date").toString(),
                            Integer.parseInt(debtJson.get("quota").toString())));
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return linkEvent;
    }
    
    public QueueArray<Event> getAllByFechaAsc(MyLinkedList<Event> linkEvent){
        QueueArray<Event> queueEvent=new QueueArray();
        for(int i=0; i<linkEvent.size();i++){
            queueEvent.offer(linkEvent.get(i));
        }
        return queueEvent;
    }
    
    public void insertEvent(Event event){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject eventJson=new JSONObject();
            eventJson.put("id", getLastIndex());
            eventJson.put("idDebt", event.getIdDebt());
            eventJson.put("value",event.getValue());
            eventJson.put("date",event.getDate());
            eventJson.put("quota",event.getQuota());
            arrayJson.add(eventJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            updateIndex();
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void deleteEvent(Event event){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==event.getId()){
                    break;
                }
            }
            arrayJson.remove(tempJson);
            DebtDAO.updatePercentDebt(event.getIdDebt(), event.getValue(), event.getQuota());
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void deleteAllByIdDebtEvent(int idDebt){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("idDebt").toString())==idDebt){
                    arrayJson.remove(tempJson);
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
    
    public void updateIndex(){
        fIndex.setWritable(true);
        try{
            FileReader fr = new FileReader(fIndex);
            JSONObject indexJson= (JSONObject) jsonParser.parse(fr);
            indexJson.replace("idEvent", (Integer.parseInt(indexJson.get("idEvent").toString())+1));
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
            return Integer.parseInt(indexJson.get("idEvent").toString());
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return -1;
    }
    
    public void deteleAllEvent(){
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
    
}
