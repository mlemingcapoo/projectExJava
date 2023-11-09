/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.DBCManager;
import DAO.loginAuthen;
import Helper.DialogHelper;
import Helper.PanelHelper;
import Model.NhanVien;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
//import modules.loginListener;
import panelFrame.reLoginPanel;
import panelFrame.changePassword;
import panelFrame.manageChuyenDe;
import panelFrame.manageHocVien;
import panelFrame.manageKhoaHoc;
import panelFrame.manageNguoiHoc;
import panelFrame.manageNhanVien;
import panelFrame.tutorialPanel;
import panelFrame.viewBangDiemKhoaPanel;
import panelFrame.viewDiemChuyenDe;
import panelFrame.viewDoanhThu;
import panelFrame.viewNguoiHoc;

/**
 *
 * @author catty
 */
public final class mainUI extends javax.swing.JFrame {

    protected int permissionLevel = -1;
    String currentFullName;
    String currentUserName;
    NhanVien nv;

//    LoginPanel loginPanel;
//    JInternalFrame innerFrameMain = new innerBlankFrame(); 
    /**
     * Creates new form mainUI
     */
    public mainUI() {
        initComponents();
        setSize(850, 750);
        setTitle("Quản lý đào tạo Edusys");
        setLocationRelativeTo(null);
//        adaptiveDisplay.setUI(new BasicTabbedPaneUI() {
//            protected int getTabLabelHeight() {
//                return 0;
//            }
//        });
        setVisible(true);
        PanelHelper.reset(adaptiveDisplay);
        counter();
        this.setIconImage(Helper.imgHelper.getAppIcon());

    }

    void counter() {
        //Đồng hồ
        new Timer(10, new ActionListener() {
            SimpleDateFormat fomat = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                timeIndicator.setText(fomat.format(new Date()));

            }
        }).start();
    }

//    @Override
    public void onLoginSuccess() {
        nv = loginAuthen.getCurrentUsers().get(loginAuthen.getCurrentIndex());

        currentFullName = nv.getFullName();
        currentUserName = nv.getMaNV();
        permissionLevel = loginAuthen.getCurrentPermission();
        switch (permissionLevel) {
            case 0:
                roleLabel.setText("Nhân viên phòng đào tạo");
                break;
            case 1:
                roleLabel.setText("Trưởng phòng đào tạo");
                break;
            default:
                roleLabel.setText("Không có quyền hạn");
                break;
        }
        System.out.println(
                "mainUI said success");
        PanelHelper.reset(adaptiveDisplay);
        currentFullName = nv.getFullName();
        currentUserName = nv.getMaNV();

        userLabel.setText(currentFullName + "!");
    }

//    @Override
    public void onLoginFail() {
        permissionLevel = -1;
        System.out.println("mainUI said failed");

    }

    public void onAbort() {
        System.out.println("aborted");
        PanelHelper.reset(adaptiveDisplay);
        setSize(850, 750);
    }

    void openHuongDan() {
        try {
            String filename = "index.html";
            URL url = getClass().getClassLoader().getResource(filename);
            if (url != null) {
                Desktop.getDesktop().browse(url.toURI());
            } else {
                DialogHelper.alert(this, "File not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        basePanel = new javax.swing.JPanel();
        adaptiveDisplay = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        timeIndicator = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuLogin = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();
        loginMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuChangePass = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuChuyenDe = new javax.swing.JMenuItem();
        menuKhoaHoc = new javax.swing.JMenuItem();
        menuNguoiHoc = new javax.swing.JMenuItem();
        menuHocVien = new javax.swing.JMenuItem();
        menuNhanVien = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuViewNguoiHoc = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuViewBangDiemKhoa = new javax.swing.JMenuItem();
        menuViewDiemTungKhoa = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuDoanhThu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuTutorial = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuIntroduce = new javax.swing.JMenuItem();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        adaptiveDisplay.setBackground(new java.awt.Color(255, 255, 255));
        adaptiveDisplay.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(204, 204, 204));

        timeIndicator.setText("00:00:00 AM");

        lblTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Red star.png"))); // NOI18N
        lblTrangThai.setText("Hệ thống đào tạo");
        lblTrangThai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangThaiMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Monitor.png"))); // NOI18N
        jLabel5.setText("Time:");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTrangThai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeIndicator)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeIndicator)
                    .addComponent(lblTrangThai)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(basePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(adaptiveDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basePanelLayout.createSequentialGroup()
                .addGap(0, 524, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(basePanelLayout.createSequentialGroup()
                    .addComponent(adaptiveDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addGap(36, 36, 36)))
        );

        toolBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        toolBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        toolBar.setEnabled(false);
        toolBar.setMargin(new java.awt.Insets(0, 10, 0, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/đăng xuất 24.png"))); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        toolBar.add(jButton1);

        jSeparator9.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        toolBar.add(jSeparator9);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/thoat 24.png"))); // NOI18N
        jButton2.setText("Kết thúc");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        toolBar.add(jButton2);
        toolBar.add(jSeparator6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/To do list.png"))); // NOI18N
        jButton3.setText("Chuyên đề");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChuyenDeActionPerformed(evt);
            }
        });
        toolBar.add(jButton3);

        jSeparator10.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        toolBar.add(jSeparator10);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Unknown person.png"))); // NOI18N
        jButton4.setText("Người học");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNguoiHocActionPerformed(evt);
            }
        });
        toolBar.add(jButton4);

        jSeparator11.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        toolBar.add(jSeparator11);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Scroll list.png"))); // NOI18N
        jButton5.setText("Khoá học");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKhoaHocActionPerformed(evt);
            }
        });
        toolBar.add(jButton5);
        toolBar.add(jSeparator7);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Properties.png"))); // NOI18N
        jButton6.setText("Hướng dẫn");
        jButton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTutorialActionPerformed(evt);
            }
        });
        toolBar.add(jButton6);
        toolBar.add(jSeparator8);

        jLabel1.setText("Welcome");

        userLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        userLabel.setText("?");

        roleLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        roleLabel.setText("?");

        jLabel2.setText("Phiên:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(roleLabel))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        toolBar.add(jPanel1);

        menuBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        menuLogin.setText("Hệ thống");

        logoutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Users.png"))); // NOI18N
        logoutMenuItem.setText("Đăng nhập lại");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginMenuItemActionPerformed(evt);
            }
        });
        menuLogin.add(logoutMenuItem);

        loginMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/out 15x15.png"))); // NOI18N
        loginMenuItem.setText("Đăng xuất");
        loginMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        menuLogin.add(loginMenuItem);
        menuLogin.add(jSeparator1);

        menuChangePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/permission (1).png"))); // NOI18N
        menuChangePass.setText("Đổi mật khẩu");
        menuChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChangePassActionPerformed(evt);
            }
        });
        menuLogin.add(menuChangePass);
        menuLogin.add(jSeparator2);

        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/thoat 15.png"))); // NOI18N
        menuExit.setText("Kết thúc");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuLogin.add(menuExit);

        menuBar.add(menuLogin);

        jMenu4.setText("Quản lý");

        menuChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/To do list.png"))); // NOI18N
        menuChuyenDe.setText("Chuyên đề");
        menuChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChuyenDeActionPerformed(evt);
            }
        });
        jMenu4.add(menuChuyenDe);

        menuKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Scroll list.png"))); // NOI18N
        menuKhoaHoc.setText("Khoá học");
        menuKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKhoaHocActionPerformed(evt);
            }
        });
        jMenu4.add(menuKhoaHoc);

        menuNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Unknown person.png"))); // NOI18N
        menuNguoiHoc.setText("Người học");
        menuNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNguoiHocActionPerformed(evt);
            }
        });
        jMenu4.add(menuNguoiHoc);

        menuHocVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/SV 24x24.png"))); // NOI18N
        menuHocVien.setText("Học viên");
        menuHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHocVienActionPerformed(evt);
            }
        });
        jMenu4.add(menuHocVien);

        menuNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/User group.png"))); // NOI18N
        menuNhanVien.setText("Nhân viên");
        menuNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNhanVienActionPerformed(evt);
            }
        });
        jMenu4.add(menuNhanVien);

        menuBar.add(jMenu4);

        jMenu2.setText("Thống kê");

        menuViewNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Notes.png"))); // NOI18N
        menuViewNguoiHoc.setText("Người học từng năm");
        menuViewNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewNguoiHocActionPerformed(evt);
            }
        });
        jMenu2.add(menuViewNguoiHoc);
        jMenu2.add(jSeparator3);

        menuViewBangDiemKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Numbered list.png"))); // NOI18N
        menuViewBangDiemKhoa.setText("Bảng điểm khoá");
        menuViewBangDiemKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewBangDiemKhoaActionPerformed(evt);
            }
        });
        jMenu2.add(menuViewBangDiemKhoa);

        menuViewDiemTungKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Report.png"))); // NOI18N
        menuViewDiemTungKhoa.setText("Điểm từng khoá học");
        menuViewDiemTungKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewDiemTungKhoaActionPerformed(evt);
            }
        });
        jMenu2.add(menuViewDiemTungKhoa);
        jMenu2.add(jSeparator4);

        menuDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Price list.png"))); // NOI18N
        menuDoanhThu.setText("Doanh thu từng chuyên đề");
        menuDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDoanhThuActionPerformed(evt);
            }
        });
        jMenu2.add(menuDoanhThu);

        menuBar.add(jMenu2);

        jMenu3.setText("Trợ giúp");

        menuTutorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Properties.png"))); // NOI18N
        menuTutorial.setText("Hướng dẫn sử dụng");
        menuTutorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTutorialActionPerformed(evt);
            }
        });
        jMenu3.add(menuTutorial);
        jMenu3.add(jSeparator5);

        menuIntroduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icn/Software.png"))); // NOI18N
        menuIntroduce.setText("Giới thiệu sản phẩm");
        menuIntroduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIntroduceActionPerformed(evt);
            }
        });
        jMenu3.add(menuIntroduce);

        menuBar.add(jMenu3);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
            .addComponent(basePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(basePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginMenuItemActionPerformed
        // TODO add your handling code here:
//        this.setEnabled(false);

        PanelHelper.switchDisplay(adaptiveDisplay, new reLoginPanel(this));


    }//GEN-LAST:event_loginMenuItemActionPerformed

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuItemActionPerformed
        // TODO add your handling code here:
        if (DialogHelper.confirm(this, "Xác nhận đăng xuất?")) {
            this.setEnabled(false);
            this.dispose();
            loginAuthen.setLogout();
            new Login(this);
        }

    }//GEN-LAST:event_logoutMenuItemActionPerformed

    private void menuChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChangePassActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new changePassword(this, currentUserName));
    
    }//GEN-LAST:event_menuChangePassActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // TODO add your handling code here:
        if (DialogHelper.confirm(this, "Xác nhận thoát?")){
            DBCManager.closeConnection();
        System.exit(0);
        }
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChuyenDeActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new manageChuyenDe(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuChuyenDeActionPerformed

    private void menuHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHocVienActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new manageHocVien(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuHocVienActionPerformed

    private void menuNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNguoiHocActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new manageNguoiHoc(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuNguoiHocActionPerformed

    private void menuKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKhoaHocActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new manageKhoaHoc(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuKhoaHocActionPerformed

    private void menuTutorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTutorialActionPerformed
        // TODO add your handling code here:

        PanelHelper.switchDisplay(adaptiveDisplay, new tutorialPanel());
//        setSize(792, 690);
//        openHuongDan();
    }//GEN-LAST:event_menuTutorialActionPerformed

    private void menuNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNhanVienActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new manageNhanVien(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuNhanVienActionPerformed

    private void menuViewNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewNguoiHocActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new viewNguoiHoc(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuViewNguoiHocActionPerformed

    private void menuViewBangDiemKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewBangDiemKhoaActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new viewBangDiemKhoaPanel(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuViewBangDiemKhoaActionPerformed

    private void menuViewDiemTungKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewDiemTungKhoaActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new viewDiemChuyenDe(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuViewDiemTungKhoaActionPerformed

    private void menuDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDoanhThuActionPerformed
        // TODO add your handling code here:
        PanelHelper.switchDisplay(adaptiveDisplay, new viewDoanhThu(this));
//        setSize(792, 690);
    }//GEN-LAST:event_menuDoanhThuActionPerformed

    private void menuIntroduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIntroduceActionPerformed
        // TODO add your handling code here:
        new IntroduceDialog(this, true);
    }//GEN-LAST:event_menuIntroduceActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        onAbort();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void lblTrangThaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangThaiMouseClicked
        // TODO add your handling code here:
        new IntroduceDialog(this, true);
    }//GEN-LAST:event_lblTrangThaiMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane adaptiveDisplay;
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JMenuItem loginMenuItem;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuChangePass;
    private javax.swing.JMenuItem menuChuyenDe;
    private javax.swing.JMenuItem menuDoanhThu;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuHocVien;
    private javax.swing.JMenuItem menuIntroduce;
    private javax.swing.JMenuItem menuKhoaHoc;
    private javax.swing.JMenu menuLogin;
    private javax.swing.JMenuItem menuNguoiHoc;
    private javax.swing.JMenuItem menuNhanVien;
    private javax.swing.JMenuItem menuTutorial;
    private javax.swing.JMenuItem menuViewBangDiemKhoa;
    private javax.swing.JMenuItem menuViewDiemTungKhoa;
    private javax.swing.JMenuItem menuViewNguoiHoc;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel timeIndicator;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables

}
