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
import structures.MyArrayList;
import structures.MyLinkedHashMap;
import structures.MyLinkedList;

/**
 *
 * @author DELL
 */
public class CategoryDAO {
    private final static JSONParser jsonParser = new JSONParser();
    private final static File f = new File("data/categories.json");
    private final static File fIndex = new File("data/lastIndex.json");
    
    public MyLinkedList<Category> getAllCategory(){
        MyLinkedList<Category> linkCategory = new MyLinkedList();
        try{
            FileReader fr = new FileReader(f);
            JSONArray jsonArr=(JSONArray) jsonParser.parse(fr);
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject cateJson=(JSONObject)jsonArr.get(i);
                linkCategory.add(
                            new Category(Integer.parseInt(cateJson.get("id").toString()),
                            cateJson.get("name").toString(),
                            Integer.parseInt(cateJson.get("countDebt").toString()),
                            Integer.parseInt(cateJson.get("activeDebt").toString())));
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("get all message: "+e.getMessage());
        }
        return linkCategory;
    }
    
    public static Category getByIdCategory(int id){
        try{
            Object obj = jsonParser.parse(new FileReader(f));
            JSONArray jsonArr=(JSONArray) obj;
            for(int i=0; i<jsonArr.size(); i++){
                JSONObject debtJson =(JSONObject) jsonArr.get(i);
                if (Integer.parseInt(debtJson.get("id").toString())==id){
                    return( new Category(Integer.parseInt(debtJson.get("id").toString()),
                            debtJson.get("name").toString(),
                            Integer.parseInt(debtJson.get("countDebt").toString()),
                            Integer.parseInt(debtJson.get("activeDebt").toString())));
                }
            }
        }catch(Exception e){
            System.out.println("get by id message: "+e.getMessage());
        }
        return null;
    }
    
    public static void incrementCount(int idCategory){
        Category c = getByIdCategory(idCategory);
        if(c!=null){
            c.setActiveDebt(1+c.getActiveDebt());
            c.setCountDebt(1+c.getCountDebt());
            updateCategory(c);
        }
    }
    
    public static void decrementCount(int idCategory){
        Category c = getByIdCategory(idCategory);
        if(c!=null){
            c.setActiveDebt(c.getActiveDebt()-1);
            updateCategory(c);
        }
    }
    
    public void insertCategory(Category category){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject cateJson=new JSONObject();
            cateJson.put("id", getLastIndex());
            cateJson.put("name",category.getName());
            cateJson.put("countDebt", category.getCountDebt());
            cateJson.put("activeDebt",category.getActiveDebt());
            arrayJson.add(cateJson);
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            updateIndex();
        }catch(IOException | ParseException e){
            System.out.println("insert message: "+e.getMessage());
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
            fIndex.setWritable(false);
            fw.flush();
        }catch(IOException | ParseException e){
            System.out.println("update Index message: "+e.getMessage());
        }
    }
    
    public static int getLastIndex(){
        try{
            FileReader fr = new FileReader(fIndex);
            JSONObject indexJson= (JSONObject) jsonParser.parse(fr);
            return Integer.parseInt(indexJson.get("idCategory").toString());
        }catch(IOException | ParseException e){
            System.out.println("LastIndex message: "+e.getMessage());
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
            MyArrayList<Debt> al=debtDAO.getByCategoryDebt(category.getId());
            Debt debt;
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
            for(int i=0; i<al.size(); i++){
                debt=al.get(i);
                debt.setIdCategory(0);
                debtDAO.updateDebt(debt);
            }
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("delete message: "+e.getMessage());
        }
    
    }
    
    public static void updateCategory(Category category){
        f.setWritable(true);
        try{
            FileReader fr = new FileReader(f);
            JSONArray arrayJson= (JSONArray) jsonParser.parse(fr);
            JSONObject tempJson=new JSONObject();
            for(int i=0;i<arrayJson.size();i++){
                tempJson=(JSONObject) arrayJson.get(i);
                if(Integer.parseInt(tempJson.get("id").toString())==category.getId()){
                    tempJson.replace("name",category.getName());
                    tempJson.replace("countDebt",category.getCountDebt());
                    tempJson.replace("activeDebt",category.getActiveDebt());
                    arrayJson.set(i, tempJson);
                    break;
                }
            }
            FileWriter fw=new FileWriter(f);
            fw.write(arrayJson.toJSONString());
            f.setWritable(false);
            fw.flush();
        }catch(IOException | NumberFormatException | ParseException e){
            System.out.println("update message: "+e.getMessage());
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
            System.out.println("delete all message: "+e.getMessage());
        }
    }
    
    public MyLinkedHashMap<Category, MyArrayList<Debt>> getAllDebtsByCategory(){
        MyLinkedHashMap<Category, MyArrayList<Debt>> debtsCategory = new MyLinkedHashMap<>();
        MyLinkedList<Category> categories = getAllCategory();
        DebtDAO debtDAO = new DebtDAO();
        for(int i = 0; i < categories.size(); i++){
            MyArrayList<Debt> temp = debtDAO.getByCategoryDebt(categories.get(i).getId());
            debtsCategory.put(categories.get(i),temp);
        }
        return debtsCategory;
    }
    
    /*public static void main(String[] args) {
        CategoryDAO cat = new CategoryDAO();
        MyLinkedHashMap<Category, MyArrayList<Debt>> d = cat.getAllDebtsByCategory();
        System.out.println(d);
        Category c = new Category(0, "Sin categoria", 0, 0);
        System.out.println(d.get(c));
    }*/
}