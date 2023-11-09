/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panelFrame;

import DAO.NguoiHocDAO;
import DAO.loginAuthen;
import GUI.mainUI;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.Validator;
import Model.NguoiHoc;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Calendar;

/**
 *
 * @author catty
 */
public class manageNguoiHoc extends javax.swing.JPanel {

    private final mainUI mainUI;
    /**
     * Creates new form manageNguoiHoc
     */
    NguoiHocDAO dao = new NguoiHocDAO();
    int row = -1;

    public manageNguoiHoc(mainUI mainUI) {
        initComponents();
        this.mainUI = mainUI;
        init();
    }

    private void search() {
        this.fillByName();
        this.clearForm();
        this.row = -1;
        this.updateStatus();
    }

    void init() {
        this.fillByName();
        this.row = -1;
        this.updateStatus();
    }

    void insert() {
        NguoiHoc nh = getForm();

        try {
            dao.insert(nh);
            this.fillByName();
            this.clearForm();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!" + e);
        }

    }

    void update() {
        NguoiHoc nh = getForm();

        try {
            dao.update(nh);
            this.fillByName();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }

    }

    void delete() {

        String manh = fieldMaNH.getText();
        if (DialogHelper.confirm(this, "Bạn có thực sự muốn xóa người học này?")) {
            try {
                dao.delete(manh);
                this.fillByName();
                this.clearForm();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thát bại!");
            }
        }

    }

    void clearForm() {
        NguoiHoc nh = new NguoiHoc();
        this.setForm(nh);
        this.row = -1;
        this.updateStatus();
        this.tblNguoiHoc.clearSelection();
    }

    void edit() {
        String manh = (String) tblNguoiHoc.getValueAt(this.row, 0);
        NguoiHoc nh = dao.selectByID(manh);
        if (nh != null) {
            this.setForm(nh);
            tabs.setSelectedIndex(0);
            this.updateStatus();

        }
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblNguoiHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblNguoiHoc.getRowCount() - 1;
        this.edit();
    }

    void fillByName() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String keyword = searchBar.getText();
            List<NguoiHoc> list = dao.selectByKeyword(keyword);
            for (NguoiHoc nh : list) {
                Object[] row = {
                    nh.getMaNH(),
                    nh.getFullName(),
                    nh.isGender() ? "Nam" : "Nữ",
                    DateHelper.toString(nh.getBirth()),
                    nh.getPhoneNum(),
                    nh.getMail(),
                    nh.getMaNV(),
                    DateHelper.toString(nh.getJoinDate())};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }
    

    void setForm(NguoiHoc nh) {
        fieldMaNH.setText(nh.getMaNH());
        fieldHoTen.setText(nh.getFullName());
        rdoMale.setSelected(nh.isGender());
        rdoFemale.setSelected(!nh.isGender());
        fieldNgaySinh.setText(DateHelper.toString(nh.getBirth()));
        fieldPhoneNum.setText(nh.getPhoneNum());
        fieldEmail.setText(nh.getMail());
        areaNote.setText(nh.getNote());
    }

    NguoiHoc getForm() {
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(fieldMaNH.getText());
        nh.setFullName(fieldHoTen.getText());
        nh.setGender(rdoMale.isSelected());
        nh.setBirth(DateHelper.toDate(fieldNgaySinh.getText()));
        nh.setPhoneNum(fieldPhoneNum.getText());
        nh.setMail(fieldEmail.getText());
        nh.setNote(areaNote.getText());
        nh.setMaNV(loginAuthen.getMaNV());
        nh.setJoinDate(DateHelper.now());
        return nh;
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNguoiHoc.getRowCount() - 1);
        //Trang thai form
        fieldMaNH.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        //Trang thai dieu huong
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    public boolean validateForm(boolean chk) {
        if (fieldMaNH.getText().length() == 0) {
            DialogHelper.alert(this, "Không được phép để trống mã người học!");
            fieldMaNH.requestFocus();
            return false;
        } else if (fieldMaNH.getText().length() > 7 || fieldMaNH.getText().length() < 7) {
            DialogHelper.alert(this, "Mã người học phải đúng 7 ký tự!");
            fieldMaNH.requestFocus();
            return false;
        } else if (fieldHoTen.getText().length() == 0) {
            DialogHelper.alert(this, "Không được phép để trống họ tên!");
            fieldHoTen.requestFocus();
            return false;
        } else if (checkSpecial(fieldHoTen.getText())) {
            DialogHelper.alert(this, "Không được phép ghi kí tự đặc biệt vào trong tên!");
            fieldHoTen.requestFocus();
            return false;
        } else if (fieldHoTen.getText().length() < 3) {
            DialogHelper.alert(this, "Họ và tên quá ngắn! Vui lòng ghi đầy đủ!");
            fieldHoTen.requestFocus();
            return false;
        } else if (fieldNgaySinh.getText().length() == 0) {
            DialogHelper.alert(this, "Không được phép để trống ngày sinh!");
            fieldNgaySinh.requestFocus();
            return false;
        } else if (!isValidAge(fieldNgaySinh.getText())) {
            DialogHelper.alert(this, "Ngày sinh của người học chưa đủ 16 tuổi!");
            fieldNgaySinh.requestFocus();
            return false;
        } else if (fieldPhoneNum.getText().length() == 0) {
            DialogHelper.alert(this, "Không được để trống số điện thoại!");
            fieldPhoneNum.requestFocus();
            return false;
        } else if (!fieldPhoneNum.getText().matches("((84)|(0))\\d{9}")) {
            DialogHelper.alert(this, "Không đúng định dạng số điện thoại!");
            fieldPhoneNum.requestFocus();
            return false;
        } else if (fieldEmail.getText().length() == 0) {
            DialogHelper.alert(this, "Bạn chưa nhập Email!");
            fieldEmail.requestFocus();
            return false;
        } else if (!fieldEmail.getText().matches("\\w+@\\w+(\\.\\w+){1,2}")) {
            DialogHelper.alert(this, "Email không đúng định dạng!");
            fieldEmail.requestFocus();
            return false;
        }

        try {
            Date ngaysinh = DateHelper.toDate(fieldNgaySinh.getText(), "dd/MM/yyyy");
        } catch (Exception e) {
            DialogHelper.alert(this, "Bạn nhập ngày sinh không đúng định dạng!");
            fieldNgaySinh.requestFocus();
            return false;
        }

        List<NguoiHoc> list = dao.selectAll();
        if (chk) {
            for (NguoiHoc cd : list) {
                if (fieldMaNH.getText().equals(cd.getMaNH())) {
                    DialogHelper.alert(this, "Mã người học đã tồn tại");
                    fieldMaNH.requestFocus();
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkSpecial(String value) {

        String specialChars = "@#$%^&*()_+-=[]{}|;:,.</>?";
        String numbers = "0123456789";

        for (int i = 0; i < value.length(); i++) {

            char c = value.charAt(i);

            if (specialChars.contains(String.valueOf(c))
                    || numbers.contains(String.valueOf(c))) {
                return true;
            }
        }

        return false;
    }

    public boolean isValidAge(String dateOfBirth) {

        String year = extractYear(dateOfBirth);

        int birthYear = Integer.parseInt(year);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(birthYear);
        System.out.println(currentYear);
        int age = currentYear - birthYear;
        System.out.println("age: " + age);
        if (2023 - age < 16) {
            System.out.println("true: " + age);
            return false;
        }

        return true;

    }

    private String extractYear(String date) {
        date = date.replace("/", "-");
        return date.split("-")[0];
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fieldMaNH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        fieldPhoneNum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fieldNgaySinh = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fieldEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaNote = new javax.swing.JTextArea();
        btnXoa = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        searchBar = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        tabs.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setText("Mã người học");

        jLabel3.setText("Họ và tên");

        jLabel4.setText("Giới tính");

        buttonGroup1.add(rdoMale);
        rdoMale.setSelected(true);
        rdoMale.setText("Nam");

        buttonGroup1.add(rdoFemale);
        rdoFemale.setText("Nữ");

        jLabel5.setText("Điện thoại");

        jLabel6.setText("Ngày sinh");

        jLabel7.setText("Địa chỉ email");

        jLabel8.setText("Ghi chú");

        areaNote.setColumns(20);
        areaNote.setRows(5);
        jScrollPane1.setViewportView(areaNote);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(fieldMaNH)
                    .addComponent(fieldHoTen)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoFemale))
                            .addComponent(jLabel5)
                            .addComponent(fieldPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldNgaySinh)
                            .addComponent(fieldEmail)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMale)
                    .addComponent(rdoFemale)
                    .addComponent(fieldNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Cập nhật", jPanel1);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tìm kiếm theo tên");

        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Email", "Mã NV", "Ngày DK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNguoiHocMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiHoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(searchBar, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTim)))
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
        );

        tabs.addTab("Danh sách", jPanel2);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Quản Lý Người Học");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(tabs))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm(true)) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateForm(false)) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        search();
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblNguoiHocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblNguoiHoc.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblNguoiHocMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaNote;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldHoTen;
    private javax.swing.JTextField fieldMaNH;
    private javax.swing.JTextField fieldNgaySinh;
    private javax.swing.JTextField fieldPhoneNum;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTextField searchBar;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNguoiHoc;
    // End of variables declaration//GEN-END:variables
}
