

package Model;

import java.util.Date;

/**
 *
 * @author catty
 */
public class KhoaHoc {

    private int MaKH;
    private String MaCD;
    private double HocPhi;
    private int duration;
    private Date KGdate;
    private String note;
    private String MaNV;
    private Date dateCreated;

    

    @Override
    public String toString() {
        return (MaCD + " (" + KGdate + ")");
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(double HocPhi) {
        this.HocPhi = HocPhi;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getKGdate() {
        return KGdate;
    }

    public void setKGdate(Date KGdate) {
        this.KGdate = KGdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
