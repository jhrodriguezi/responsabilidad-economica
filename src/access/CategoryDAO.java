/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package access;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Category;
import model.Debt;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import structures.ArrList;
import structures.LinkList;

/**
 *
 * @author DELL
 */
public class CategoryDAO {
    private final JSONParser jsonParser = new JSONParser();
    private final File f = new File("data/categories.json");
    private final File fIndex = new File("data/lastIndex.json");
    
    public LinkList<Category> getAllCategory(){
        LinkList<Category> linkCategory = new LinkList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=(JSONArray) jsonParser.parse(fr);
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject cateJson=(JSONObject)jsonArr.get(i);
                linkCategory.add(
                            new Category(Integer.parseInt(cateJson.get("id").toString()),
                            cateJson.get("name").toString()));
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return linkCategory;
    }
    
    public Category getByIdCategory(int id){
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=(JSONArray) obj;
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson =(JSONObject) jsonArr.get(i);
                if (Integer.parseInt(debtJson.get("id").toString())==id){
                    return( new Category(Integer.parseInt(debtJson.get("id").toString()),
                            debtJson.get("name").toString()));
                }
            }
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        return null;
    }
    
    public void insertCategory(Category category){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject cateJson=new JSONObject();
            cateJson.put("id", getLastIndex());
            cateJson.put("name",category.getName());
            arrayJson.add(cateJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            updateIndex();
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    }
    
    public void updateIndex(){
        fIndex.setWritable(true);
        try{
            FileReader fr = new FileReader(fIndex);
            JSONObject indexJson= (JSONObject) jsonParser.parse(fr);
            indexJson.replace("idCategory", (Integer.parseInt(indexJson.get("idCategory").toString())+1));
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
            return Integer.parseInt(indexJson.get("idCategory").toString());
        }catch(IOException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
        return -1;
    }
    
    public void deteleCategory(Category category){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==category.getId()){
                    break;
                }
            }
            arrayJson.remove(tempJson);
            DebtDAO debtDAO = new DebtDAO();
            ArrList<Debt> al=new ArrList();
            al=debtDAO.getByCategoryDebt(category.getId());
            Debt debt;
            for(int i=0; i<al.size(); i++){
                debt=al.get(i);
                debt.setIdCategory(0);
                debtDAO.updateDebt(debt);
            }
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("message: "+e.getMessage());
        }
    
    }
    
    public void updateCategory(Category category){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==category.getId()){
                    tempJson.replace("name",category.getName());
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
    
    public void deteleAllCategory(){//NO VA A SER HABILITADA
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