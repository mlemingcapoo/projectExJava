

package Model;

/**
 *
 * @author catty
 */
public class NhanVien {
    private String maNV;
    private String password;
    private String fullName;
    private boolean role = false;
//    private String username;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean  getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
    
    @Override
    public String toString(){
        return this.fullName;
    }

 
}
