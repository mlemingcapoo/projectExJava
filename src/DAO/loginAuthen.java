

package DAO;

import Model.NhanVien;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author catty
 */
public class loginAuthen {

    protected static int permissionLevel = -1;
    protected static int currentUserIndex = 0;
    static List<NhanVien> userList = new ArrayList();
    
    public static int getLogin(String usr, String psw) throws ClassNotFoundException, SQLException {
        
        userList.clear();
        SQL_queries_DAO sql = new NhanVienDAO_1();
        userList =  sql.selectAll();
        boolean found = false;
        int index = 0;
        for (NhanVien x : userList) {
            index++;
            if (x.getMaNV().equals(usr) && x.getPassword().equals(psw)) {
                currentUserIndex = index;
                if (x.getRole()) {
                    permissionLevel = 1;
                } else {
                    permissionLevel = 0;
                }
                found = true;
                System.out.println("loginAuth logged: " + x.getMaNV());
                System.out.println("permission level: " + x.getRole());
                System.out.println("name: " + x.getFullName());
            }
        }
        if (!found) {
            System.out.println("user not found!");
            return -1;
        }
        return permissionLevel;
    }

    public static void setLogout() {
        permissionLevel = -1;
    }

    public static int getCurrentPermission() {
        return permissionLevel;
    }

    public static List<NhanVien> getCurrentUsers(){
        return userList;
    }
    
    public static void setCurrentUsers(List<NhanVien> nv){
        userList = nv;
    }
    
    public static int getCurrentIndex() {
        return currentUserIndex-1;
    }
    
    public static String getMaNV(){
        return userList.get(currentUserIndex-1).getMaNV();
    } 
    
    public static boolean isManager(){
        return userList.get(currentUserIndex-1).getRole();
    } 
    
}
