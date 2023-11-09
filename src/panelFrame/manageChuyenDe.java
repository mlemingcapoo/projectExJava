/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panelFrame;

import DAO.ChuyenDeDAO;
import DAO.SQL_queries_DAO;
import DAO.loginAuthen;
import GUI.mainUI;
import Helper.DialogHelper;
import Helper.imgHelper;
import Model.ChuyenDe;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author catty
 */
public class manageChuyenDe extends javax.swing.JPanel {

    private final mainUI mainUI;
    SQL_queries_DAO dao = new ChuyenDeDAO();
    int rowCount = -1;
    JFileChooser fileChooser = new JFileChooser();

    /**
     * Creates new form chuyenDe
     */
    public manageChuyenDe(mainUI mainUI) {
        initComponents();
        this.mainUI = mainUI;
        init();
//        logo.setPreferredSize(new Dimension(50 , 100));
//        logo.setHorizontalAlignment(JLabel.CENTER);
//        logoChooser.setLayout(new GridLayout(0,3));
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imgHelper.save(file); //luu hinh vao thu muc logos
//            ImageIcon icon = ; //Doc hinh tu logos
//            Image originalImage = icon.getImage();
//            icon = Helper.imgHelper.resize(originalImage, 200, 200);
//            logo.setIcon(icon);
            
            ImageIcon icon = imgHelper.read(file.getName());
            Image img = imgHelper.resize(icon.getImage(), 140, 160);
            ImageIcon resizedIcon = new ImageIcon(img);
            logo.setIcon(resizedIcon);
            logo.setToolTipText(file.getName()); //giu ten hinh trong tooltip
        }
    }

    void init() {
        this.fillTable();
        this.rowCount = -1;
        this.updateStatus();
    }

    void insert() {
        ChuyenDe cd = getForm();

        try {
            dao.insert(cd);
            this.fillTable();
            this.clearForm();
            DialogHelper.showDialog(this, "Đã thêm mới hoàn tất!");
        } catch (Exception e) {
            DialogHelper.showDialog(this, "Có lỗi khi thêm mới");
        }

    }

    void update() {
        ChuyenDe cd = getForm();

        try {
            dao.update(cd);
            this.fillTable();
            DialogHelper.showDialog(this, "Cập nhật hoàn tất!");
        } catch (Exception e) {
            DialogHelper.showDialog(this, "Cập nhật thất bại");
        }

    }

    void delete() {

        if (loginAuthen.getCurrentPermission() == 0) {
            DialogHelper.showDialog(this, "Bạn không có đủ quyền cần thiết");
        } else {
            String macd = fieldMaCD.getText();
            if (macd.length() <= 1) {
                DialogHelper.showDialog(this, "Mã Chuyên đề không hợp lệ!");

            } else {
                ChuyenDe selectByID = (ChuyenDe) dao.selectByID(macd);
                if (!selectByID.getMaCD().equals(macd)) {
                    DialogHelper.showDialog(this, "Mã Chuyên đề không hợp lệ!");
                } else {
                    if (DialogHelper.confirm(this, "Xác nhận xóa chuyên đề?")) {
                        try {
                            dao.delete(macd);

                            this.fillTable();
                            this.clearForm();
                            DialogHelper.showDialog(this, "Đã xoá!");
                        } catch (Exception e) {
                            DialogHelper.showDialog(this, "Xóa thát bại!");
                        }
                    }
                }
            }
        }

    }

    void clearForm() {
        ChuyenDe cd = new ChuyenDe();
        this.setForm(cd);
        this.rowCount = -1;
        this.updateStatus();
        tableCD.clearSelection();
    }

    void edit() {
        String macd = (String) tableCD.getValueAt(this.rowCount, 0);
        ChuyenDe cd = (ChuyenDe) dao.selectByID(macd);
        this.setForm(cd);
        tabCD.setSelectedIndex(0);
        this.updateStatus();
    }

    void first() {
        this.rowCount = 0;
        this.edit();
    }

    void prev() {
        if (this.rowCount > 0) {
            this.rowCount--;
            this.edit();
        }
    }

    void next() {
        if (this.rowCount < tableCD.getRowCount() - 1) {
            this.rowCount++;
            this.edit();
        }
    }

    void last() {
        this.rowCount = tableCD.getRowCount() - 1;
        this.edit();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tableCD.getModel();
        model.setRowCount(0); // xoa tat ca cac hang
        try {
            List<ChuyenDe> list = dao.selectAll(); //doc du lieu tu CSDL
            System.out.println("CD list check: " + list.get(0).getTenCD());
            for (ChuyenDe cd : list) {
                Object[] row = {cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(), cd.getDuration(), cd.getImage()};
                model.addRow(row); //them 1 hang vao table
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.showDialog(this, "Có lỗi khi tải dữ liệu");
        }
    }

    void setForm(ChuyenDe cd) {
        fieldMaCD.setText(cd.getMaCD());
        fieldTenCD.setText(cd.getTenCD());
        fieldDuration.setText(String.valueOf(cd.getDuration()));
        fieldCost.setText(String.valueOf(cd.getHocPhi()));
        fieldInfo.setText(cd.getInfo());

        if (cd.getImage() != null) {
//            logo.setIcon();
            ImageIcon icon = imgHelper.read(cd.getImage());
            Image img = imgHelper.resize(icon.getImage(), 140, 160);
            ImageIcon resizedIcon = new ImageIcon(img);
            logo.setIcon(resizedIcon);
            logo.setToolTipText(cd.getImage());

        } else {
            logo.setToolTipText(null);
            logo.setIcon(null);
        }
    }

    ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe();

        cd.setMaCD(fieldMaCD.getText());
        cd.setTenCD(fieldTenCD.getText());
        cd.setDuration(Integer.valueOf(fieldDuration.getText()));
        cd.setHocPhi(Double.valueOf(fieldCost.getText()));
        cd.setInfo(fieldInfo.getText());
        cd.setImage(logo.getToolTipText());
        return cd;
    }

    void updateStatus() {
        boolean edit = (this.rowCount >= 0);
        boolean first = (this.rowCount == 0);
        boolean last = (this.rowCount == tableCD.getRowCount() - 1);
        //Trang thai form
        fieldMaCD.setEditable(!edit);
        buttonAdd.setEnabled(!edit);
        buttonEdit.setEnabled(edit);
        buttonEdit.setEnabled(edit);
        //Trang thai dieu huong
        buttonFirst.setEnabled(edit && !first);
        buttonPrev.setEnabled(edit && !first);
        buttonNext.setEnabled(edit && !last);
        buttonLast.setEnabled(edit && !last);
    }
    
    public boolean checkSpecial(String value) {

        String specialChars = "@#$%^&*()_+-=[]{}|;:,.</>?";
//        String numbers = "0123456789";

        for (int i = 0; i < value.length(); i++) {

            char c = value.charAt(i);

            if (specialChars.contains(String.valueOf(c))) {
                return true;
            }
        }

        return false;
    }

    public boolean validateForm(boolean chk) {
        try {
            if (fieldMaCD.getText().length() == 0) {
                DialogHelper.showDialog(this, "Vui lòng điền mã Chuyên Đề!");
                fieldMaCD.requestFocus();
                return false;
            } else if (fieldMaCD.getText().length() != 5) {
                DialogHelper.showDialog(this, "Mã Chuyên Đề chỉ cho phép đúng 5 kí tự !");
                fieldMaCD.requestFocus();
                return false;
            } else if (fieldTenCD.getText().length() == 0) {
                DialogHelper.showDialog(this, "Vui lòng điền tên Chuyên Đề!");
                fieldTenCD.requestFocus();
                return false;
            }else if (checkSpecial(fieldTenCD.getText())) {
                DialogHelper.showDialog(this, "Tên chuyên đề không được chứa kí tự đắc biệt!");
                fieldTenCD.requestFocus();
                return false;
            } else if (fieldTenCD.getText().length() <3) {
                DialogHelper.showDialog(this, "Tên chuyên đề quá ngắn");
                fieldTenCD.requestFocus();
                return false;
            } else if (fieldDuration.getText().length() == 0) {
                DialogHelper.showDialog(this, "Vui lòng điền thời lượng!");
                fieldDuration.requestFocus();
                return false;
            } else if (Integer.parseInt(fieldDuration.getText()) < 0) {
                DialogHelper.showDialog(this, "Thời lượng phải lớn hơn 0!");
                fieldDuration.requestFocus();
                return false;
            } else if (fieldCost.getText().length() == 0) {
                DialogHelper.showDialog(this, "Vui lòng điền học phí!");
                fieldCost.requestFocus();
                return false;
            } else if (Double.parseDouble(fieldCost.getText()) < 0) {
                DialogHelper.showDialog(this, "Học phí phải lớn hơn 0!");
                fieldCost.requestFocus();
                return false;
            } else if (logo.getIcon() == null) {
                DialogHelper.showDialog(this, "Vui lòng chọn logo!");
                logo.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.showDialog(this, "Định dạng không hợp lệ\r"
                    + "Vui lòng nhập số cho thời lượng và học phí.");
            return false;
        }
        
        

        List<ChuyenDe> list = dao.selectAll();
        if (chk) {
            for (ChuyenDe cd : list) {
                if (fieldMaCD.getText().equals(cd.getMaCD())) {
                    DialogHelper.showDialog(this, "Mã Chuyên đề đã tồn tại");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabCD = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        logoChooser = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldMaCD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fieldTenCD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fieldDuration = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fieldCost = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fieldInfo = new javax.swing.JTextArea();
        buttonAdd = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonNew = new javax.swing.JButton();
        buttonFirst = new javax.swing.JButton();
        buttonPrev = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        buttonLast = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCD = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        tabCD.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setText("Chọn Logo");

        logoChooser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        logoChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoChooserMousePressed(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoChooserMousePressed(evt);
            }
        });

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setFocusable(false);
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoChooserMousePressed(evt);
            }
        });

        javax.swing.GroupLayout logoChooserLayout = new javax.swing.GroupLayout(logoChooser);
        logoChooser.setLayout(logoChooserLayout);
        logoChooserLayout.setHorizontalGroup(
            logoChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoChooserLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        logoChooserLayout.setVerticalGroup(
            logoChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoChooserLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        jLabel3.setText("Mã chuyên đề");

        jLabel5.setText("Tên chuyên đề");

        jLabel6.setText("Thời Lượng");

        jLabel7.setText("Học phí");

        jLabel8.setText("Mô tả chuyên đề");

        fieldInfo.setColumns(20);
        fieldInfo.setRows(5);
        jScrollPane2.setViewportView(fieldInfo);

        buttonAdd.setText("Thêm");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        buttonEdit.setText("Sửa");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        buttonDelete.setText("Xóa");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonNew.setText("Mới");
        buttonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewActionPerformed(evt);
            }
        });

        buttonFirst.setText("|<");
        buttonFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFirstActionPerformed(evt);
            }
        });

        buttonPrev.setText("<-");
        buttonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrevActionPerformed(evt);
            }
        });

        buttonNext.setText("->");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        buttonLast.setText(">|");
        buttonLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNew)
                        .addGap(107, 107, 107)
                        .addComponent(buttonFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLast, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(logoChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(fieldDuration, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(fieldTenCD)
                            .addComponent(fieldMaCD)
                            .addComponent(fieldCost))
                        .addGap(2, 2, 2))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fieldCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logoChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAdd)
                    .addComponent(buttonEdit)
                    .addComponent(buttonDelete)
                    .addComponent(buttonNew)
                    .addComponent(buttonFirst)
                    .addComponent(buttonPrev)
                    .addComponent(buttonNext)
                    .addComponent(buttonLast))
                .addContainerGap())
        );

        tabCD.addTab("Cập Nhật", jPanel1);

        tableCD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã CD", "Tên chuyên đề", "Học Phí", "Thời lượng", "Hình"
            }
        ));
        tableCD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableCDMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tableCD);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabCD.addTab("Danh Sách", jPanel2);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Chuyên Đề");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabCD, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(tabCD)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logoChooserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoChooserMousePressed
        chonAnh();
    }//GEN-LAST:event_logoChooserMousePressed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        if (validateForm(true)) {
            insert();
        }
    }//GEN-LAST:event_buttonAddActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        if (validateForm(false)) {
            update();
        }
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        delete();
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewActionPerformed
        clearForm();
    }//GEN-LAST:event_buttonNewActionPerformed

    private void buttonFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFirstActionPerformed
        first();
    }//GEN-LAST:event_buttonFirstActionPerformed

    private void buttonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrevActionPerformed
        prev();
    }//GEN-LAST:event_buttonPrevActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        next();
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLastActionPerformed
        last();
    }//GEN-LAST:event_buttonLastActionPerformed

    private void tableCDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCDMousePressed
        if (evt.getClickCount() == 2) {
            this.rowCount = tableCD.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tableCDMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonFirst;
    private javax.swing.JButton buttonLast;
    private javax.swing.JButton buttonNew;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPrev;
    private javax.swing.JTextField fieldCost;
    private javax.swing.JTextField fieldDuration;
    private javax.swing.JTextArea fieldInfo;
    private javax.swing.JTextField fieldMaCD;
    private javax.swing.JTextField fieldTenCD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel logoChooser;
    private javax.swing.JTabbedPane tabCD;
    private javax.swing.JTable tableCD;
    // End of variables declaration//GEN-END:variables
}
