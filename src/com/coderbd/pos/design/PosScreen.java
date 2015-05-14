/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.constraints.Role;
import com.coderbd.pos.dao.ExpendituresDao;
import com.coderbd.pos.dao.ExpenseCategoriesDao;
import com.coderbd.pos.dao.ProductsDao;
import com.coderbd.pos.dao.ShopOwnersDao;
import com.coderbd.pos.dao.ShopsDao;
import com.coderbd.pos.dao.UsersDao;
import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.pdf.BarcodePdf;
import com.coderbd.pos.service.ProductService;
import com.coderbd.pos.service.ShopExpenseService;
import com.coderbd.pos.service.ShopOwnerService;
import com.coderbd.pos.service.ShopService;
import com.coderbd.pos.service.UserService;
import com.coderbd.pos.utils.BarCodeGenerate;
import com.coderbd.pos.utils.Converter;
import com.coderbd.pos.utils.IDBuilder;
import com.coderbd.pos.utils.ShopExpense;
import com.coderbd.pos.utils.ShopOrder;
import com.coderbd.pos.validation.LoginValidation;
import com.coderbd.pos.validation.Message;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Biswajit Debnath
 */
public class PosScreen extends javax.swing.JFrame {

    /**
     * Creates new form PosScreen
     */
    private ApplicationContext appContext;
    
    private UserService userService;
    private ShopService shopService;
    private ShopOwnerService shopOwnerService;
    private ProductService productService;
    private ShopExpenseService shopExpenseService;
    
    private DeveloperHelper devHelper;
    private ManagerHelper manHelper;
    private StaffHelper staffHelper;
    private ResetTable resetter;
    
    private PanelSlider panelSlider;
    private LoginValidation loginValidation;
    private ProductAdder productAdder;
    private ExpenseAdder expenseAdder;
    private Converter convert;
    private IDBuilder idBuilder;

    /*User authorized shop list*/
    private List<Shop> shops;
    private List<User> users;
    /*Selected Shop's Products*/
    private List<Product> products;
    /*Selected Shop's Category wise expenditures*/
    private List<ShopExpense> shopExpenses;
    /*Login User Info*/
    private User user;
    /*User selected shop*/
    private Shop shop;
    /*Customer Shop Order Object*/
    private ShopOrder shopOrder;
    
    public PosScreen() {
        initComponents();
        /**
         *
         * Below method are added for object initialize
         */
        initInstance();
        initUI();
        initAppContext();
        initOrder();
    }
    
    private void initInstance() {
        panelSlider = new PanelSlider();
        loginValidation = new LoginValidation();
        devHelper = new DeveloperHelper();
        manHelper = new ManagerHelper();
        staffHelper = new StaffHelper();
        productAdder = new ProductAdder();
        expenseAdder = new ExpenseAdder();
        resetter = new ResetTable();
        convert = new Converter();
        idBuilder = new IDBuilder();
    }
    
    private void initUI() {
        logoutItem.setEnabled(false);
        panelSlider.changeThePanel(mainPanel, loginPanel);
    }
    
    private void initAppContext() {
        try {
            appContext = new ClassPathXmlApplicationContext("com/coderbd/pos/properties/spring-config.xml");
            System.out.println("App Context Loaded!");
        } catch (BeansException ex) {
            System.out.println(ex.getMessage());
            System.out.println("App Context Not Loaded!");
        }
        
        if (appContext != null) {
            userService = (UserService) appContext.getBean("userService");
            shopService = (ShopService) appContext.getBean("shopService");
            shopOwnerService = (ShopOwnerService) appContext.getBean("shopOwnerService");
            productService = (ProductService) appContext.getBean("productService");
            shopExpenseService = (ShopExpenseService) appContext.getBean("shopExpenseService");
        }
    }
    
    private void initOrder() {
        shopOrder = new ShopOrder();
        String orderId = idBuilder.geOrderUniqueID();
        orderIDViewLabel.setText(orderId);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        addNewShopPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        shopTinField = new javax.swing.JTextField();
        shopAddressField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        shopNameField = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        createShopButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        shopMobileField = new javax.swing.JTextField();
        shopCreationLabel = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        controllerPanel = new javax.swing.JPanel();
        signBoardPanel = new javax.swing.JPanel();
        shopNameLabel = new javax.swing.JLabel();
        shopAddressLabel = new javax.swing.JLabel();
        softUserNameLabel = new javax.swing.JLabel();
        softUserMobileLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        mobileLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        softUserRoleLabel = new javax.swing.JLabel();
        printerCB = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        viewPanel = new javax.swing.JPanel();
        userTabPanel = new javax.swing.JTabbedPane();
        customerOrderPanel = new javax.swing.JPanel();
        orderInputPanel = new javax.swing.JPanel();
        productCodeLabel = new javax.swing.JLabel();
        orderPProductBarcodeField = new javax.swing.JTextField();
        addOItemButton = new javax.swing.JButton();
        inStockLabel = new javax.swing.JLabel();
        inStockViewLabel = new javax.swing.JLabel();
        newOrderCreateButton = new javax.swing.JButton();
        removeOtItemButton = new javax.swing.JButton();
        productInfoLabel = new javax.swing.JLabel();
        orderFinalizePanel = new javax.swing.JPanel();
        customerNameField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        customerMobileField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        totalBillViewLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cashField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cashBackViewLabel = new javax.swing.JLabel();
        orderCompleteButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        orderIDViewLabel = new javax.swing.JLabel();
        orderViewPanel = new javax.swing.JPanel();
        orderViewScroll = new javax.swing.JScrollPane();
        orderViewTable = new javax.swing.JTable();
        searchProductPanel = new javax.swing.JPanel();
        searchInputPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        searchViewPanel = new javax.swing.JPanel();
        searchViewScroll = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        barcodeGenerationPanel = new javax.swing.JPanel();
        barcodeInputPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        previousCodeField = new javax.swing.JTextField();
        quantityCodeField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        barcodeQuantityLabel = new javax.swing.JLabel();
        barcodeSimulatePanel = new javax.swing.JPanel();
        bcodeGenButton = new javax.swing.JButton();
        barcodeViewPanel = new javax.swing.JPanel();
        managerPanel = new javax.swing.JPanel();
        managerTab = new javax.swing.JTabbedPane();
        managerShopStockPanel = new javax.swing.JPanel();
        stockInputPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        psCodeField = new javax.swing.JTextField();
        psNameField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        psQuantityField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        psRateField = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        refreshStocksButton = new javax.swing.JButton();
        stockScroll = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable();
        shopExpenditurePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        expAmountField = new javax.swing.JTextField();
        saveExpenseButton = new javax.swing.JButton();
        expenditureDeleteButton = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        expDescField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        expDate = new com.toedter.calendar.JDateChooser();
        expCategoryCB = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        reportPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        selectionPanel = new javax.swing.JPanel();
        shopSelectionEnclosedPanel = new javax.swing.JPanel();
        openShopButton = new javax.swing.JButton();
        createNewShopButton = new javax.swing.JButton();
        shopSelectCB = new javax.swing.JComboBox();
        shopSelectErrorLabel = new javax.swing.JLabel();
        addNewStaffButton = new javax.swing.JButton();
        loginPanel = new javax.swing.JPanel();
        logPanelEnclosed = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        logIn = new javax.swing.JButton();
        logInError = new javax.swing.JLabel();
        forgotLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        accountCreatePanel = new javax.swing.JPanel();
        acpEnclosedPanel = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        mobileFieldACP = new javax.swing.JTextField();
        passField2ACP = new javax.swing.JPasswordField();
        jLabel38 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        backButtonACP = new javax.swing.JButton();
        authorityComboACP = new javax.swing.JComboBox();
        passField1ACP = new javax.swing.JPasswordField();
        addUserButtonACP = new javax.swing.JButton();
        userCreationErrorLabel = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        nameFieldACP = new javax.swing.JTextField();
        userFieldACP = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        staffAddPanel = new javax.swing.JPanel();
        staffAddEncloed = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        userCB = new javax.swing.JComboBox();
        addUserStatusLabel = new javax.swing.JLabel();
        addStafFinalButton = new javax.swing.JButton();
        shopCB = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        logoutItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        backupDateItem = new javax.swing.JMenuItem();
        windowMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        supportItem = new javax.swing.JMenuItem();
        developerItem = new javax.swing.JMenuItem();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.CardLayout());

        mainPanel.setLayout(new java.awt.CardLayout());

        addNewShopPanel.setLayout(new java.awt.GridBagLayout());

        shopTinField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        shopAddressField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        shopAddressField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopAddressFieldActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setText("Shop Name");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel24.setText("Address");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel23.setText("TIN Number");

        shopNameField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setText("RESET");

        createShopButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        createShopButton.setText("CREATE");
        createShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createShopButtonActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setText("Mobile Number");

        shopMobileField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        shopCreationLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("BACK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shopMobileField)
                    .addComponent(shopCreationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(shopNameField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(shopTinField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(shopAddressField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(createShopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shopNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(shopTinField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(shopAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(shopMobileField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(shopCreationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createShopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        addNewShopPanel.add(jPanel1, new java.awt.GridBagConstraints());

        mainPanel.add(addNewShopPanel, "card5");

        controllerPanel.setLayout(new java.awt.BorderLayout());

        signBoardPanel.setPreferredSize(new java.awt.Dimension(400, 60));

        shopNameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        shopNameLabel.setText("The New Chitra Boutiques");

        shopAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shopAddressLabel.setText("381, Al-Hamra Shopping City, 2nd floor");

        softUserNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        softUserNameLabel.setText("Sukomol Roy");

        softUserMobileLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        softUserMobileLabel.setText("01937595521");

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameLabel.setText("Name : ");

        mobileLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mobileLabel.setText("Mobile : ");

        roleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        roleLabel.setText("Role :");

        softUserRoleLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        printerCB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        printerCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CANON-L3300", "SEWOO" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Select Printer");

        javax.swing.GroupLayout signBoardPanelLayout = new javax.swing.GroupLayout(signBoardPanel);
        signBoardPanel.setLayout(signBoardPanelLayout);
        signBoardPanelLayout.setHorizontalGroup(
            signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signBoardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shopAddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shopNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(printerCB, 0, 169, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signBoardPanelLayout.createSequentialGroup()
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(softUserNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(signBoardPanelLayout.createSequentialGroup()
                        .addComponent(mobileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(softUserMobileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(roleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(softUserRoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        signBoardPanelLayout.setVerticalGroup(
            signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signBoardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shopNameLabel)
                    .addComponent(softUserNameLabel)
                    .addComponent(nameLabel)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(softUserRoleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(softUserMobileLabel)
                        .addComponent(shopAddressLabel)
                        .addComponent(mobileLabel)
                        .addComponent(roleLabel)
                        .addComponent(printerCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        controllerPanel.add(signBoardPanel, java.awt.BorderLayout.PAGE_START);

        viewPanel.setLayout(new java.awt.CardLayout());

        customerOrderPanel.setLayout(new java.awt.BorderLayout());

        productCodeLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        productCodeLabel.setText("Product Code");

        orderPProductBarcodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderPProductBarcodeFieldActionPerformed(evt);
            }
        });
        orderPProductBarcodeField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                orderPProductBarcodeFieldPropertyChange(evt);
            }
        });
        orderPProductBarcodeField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orderPProductBarcodeFieldKeyPressed(evt);
            }
        });

        addOItemButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addOItemButton.setText("Add In Order List");

        inStockLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        inStockLabel.setText("In Stock: ");

        inStockViewLabel.setText("100 units");

        newOrderCreateButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newOrderCreateButton.setText("New Order");
        newOrderCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderCreateButtonActionPerformed(evt);
            }
        });

        removeOtItemButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        removeOtItemButton.setText("Remove Item Order List");

        javax.swing.GroupLayout orderInputPanelLayout = new javax.swing.GroupLayout(orderInputPanel);
        orderInputPanel.setLayout(orderInputPanelLayout);
        orderInputPanelLayout.setHorizontalGroup(
            orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderPProductBarcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addComponent(inStockLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inStockViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(productInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeOtItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(addOItemButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(newOrderCreateButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        orderInputPanelLayout.setVerticalGroup(
            orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productCodeLabel)
                    .addComponent(newOrderCreateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orderPProductBarcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOItemButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(inStockLabel)
                        .addComponent(inStockViewLabel)
                        .addComponent(removeOtItemButton))
                    .addComponent(productInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customerOrderPanel.add(orderInputPanel, java.awt.BorderLayout.PAGE_START);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Customer Name");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Mobile");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Total Bill");

        totalBillViewLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Cash In");

        cashField.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Cash Out");

        cashBackViewLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        orderCompleteButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        orderCompleteButton.setText("Complete Order");
        orderCompleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderCompleteButtonActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Order ID");

        orderIDViewLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout orderFinalizePanelLayout = new javax.swing.GroupLayout(orderFinalizePanel);
        orderFinalizePanel.setLayout(orderFinalizePanelLayout);
        orderFinalizePanelLayout.setHorizontalGroup(
            orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderFinalizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orderIDViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(customerMobileField))
                .addGap(15, 15, 15)
                .addComponent(orderCompleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(totalBillViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cashField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(cashBackViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        orderFinalizePanelLayout.setVerticalGroup(
            orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                        .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(customerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(totalBillViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(customerMobileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cashField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cashBackViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addComponent(orderIDViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(orderCompleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        customerOrderPanel.add(orderFinalizePanel, java.awt.BorderLayout.PAGE_END);

        orderViewPanel.setLayout(new java.awt.CardLayout());

        orderViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Index", "Product Code", "Product Name", "Quantity", "Rate", "Discount %", "VAT %", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderViewScroll.setViewportView(orderViewTable);

        orderViewPanel.add(orderViewScroll, "card2");

        customerOrderPanel.add(orderViewPanel, java.awt.BorderLayout.CENTER);

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=3>Customer Order</body></html>", customerOrderPanel);

        searchProductPanel.setLayout(new java.awt.BorderLayout());

        searchInputPanel.setPreferredSize(new java.awt.Dimension(650, 60));

        jButton1.setText("Search");

        jButton2.setText("Refresh");

        javax.swing.GroupLayout searchInputPanelLayout = new javax.swing.GroupLayout(searchInputPanel);
        searchInputPanel.setLayout(searchInputPanelLayout);
        searchInputPanelLayout.setHorizontalGroup(
            searchInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchInputPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        searchInputPanelLayout.setVerticalGroup(
            searchInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        searchProductPanel.add(searchInputPanel, java.awt.BorderLayout.PAGE_START);

        searchViewPanel.setLayout(new java.awt.CardLayout());

        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Code", "Product Name", "Rate", "In Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchViewScroll.setViewportView(searchTable);

        searchViewPanel.add(searchViewScroll, "card2");

        searchProductPanel.add(searchViewPanel, java.awt.BorderLayout.CENTER);

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=3>Search Product</body></html>", searchProductPanel);

        barcodeGenerationPanel.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Previous Barcode");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Row Quantity");

        jLabel3.setText("KEEP BLANK FOR GENERATING NEW BARCODE");

        javax.swing.GroupLayout barcodeInputPanelLayout = new javax.swing.GroupLayout(barcodeInputPanel);
        barcodeInputPanel.setLayout(barcodeInputPanelLayout);
        barcodeInputPanelLayout.setHorizontalGroup(
            barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barcodeInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(previousCodeField)
                    .addComponent(quantityCodeField, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barcodeQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(226, Short.MAX_VALUE))
        );
        barcodeInputPanelLayout.setVerticalGroup(
            barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barcodeInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(previousCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(22, 22, 22)
                .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(barcodeInputPanelLayout.createSequentialGroup()
                        .addGroup(barcodeInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(quantityCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(barcodeQuantityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        barcodeGenerationPanel.add(barcodeInputPanel, java.awt.BorderLayout.PAGE_START);

        bcodeGenButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bcodeGenButton.setText("Generate Barcode");
        bcodeGenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcodeGenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout barcodeSimulatePanelLayout = new javax.swing.GroupLayout(barcodeSimulatePanel);
        barcodeSimulatePanel.setLayout(barcodeSimulatePanelLayout);
        barcodeSimulatePanelLayout.setHorizontalGroup(
            barcodeSimulatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bcodeGenButton, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        barcodeSimulatePanelLayout.setVerticalGroup(
            barcodeSimulatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barcodeSimulatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bcodeGenButton, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        barcodeGenerationPanel.add(barcodeSimulatePanel, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout barcodeViewPanelLayout = new javax.swing.GroupLayout(barcodeViewPanel);
        barcodeViewPanel.setLayout(barcodeViewPanelLayout);
        barcodeViewPanelLayout.setHorizontalGroup(
            barcodeViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        barcodeViewPanelLayout.setVerticalGroup(
            barcodeViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        barcodeGenerationPanel.add(barcodeViewPanel, java.awt.BorderLayout.CENTER);

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Barcode Generation</body></html>", barcodeGenerationPanel);

        managerPanel.setLayout(new java.awt.CardLayout());

        managerShopStockPanel.setLayout(new java.awt.BorderLayout());

        stockInputPanel.setPreferredSize(new java.awt.Dimension(758, 80));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Product Code");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Product Name");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Quantity");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Rate");

        jButton9.setText("Update Stock");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Delete Product");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        refreshStocksButton.setText("Refresh Stock Items");
        refreshStocksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshStocksButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stockInputPanelLayout = new javax.swing.GroupLayout(stockInputPanel);
        stockInputPanel.setLayout(stockInputPanelLayout);
        stockInputPanelLayout.setHorizontalGroup(
            stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(psNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stockInputPanelLayout.createSequentialGroup()
                        .addComponent(psRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshStocksButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(stockInputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        stockInputPanelLayout.setVerticalGroup(
            stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(psCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(refreshStocksButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        managerShopStockPanel.add(stockInputPanel, java.awt.BorderLayout.PAGE_START);

        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Code", "Product Name", "Quantity", "Rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stockScroll.setViewportView(stockTable);

        managerShopStockPanel.add(stockScroll, java.awt.BorderLayout.CENTER);

        managerTab.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=3>Shop Stock</body></html>", managerShopStockPanel);

        shopExpenditurePanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(768, 100));

        saveExpenseButton.setText("Save");
        saveExpenseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveExpenseButtonActionPerformed(evt);
            }
        });

        expenditureDeleteButton.setText("Delete");
        expenditureDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenditureDeleteButtonActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Expense Category");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Description");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Amount");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Date");

        expCategoryCB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        expCategoryCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BANK", "SUPPLIER" }));
        expCategoryCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expCategoryCBActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Add New Category");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(expCategoryCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expDescField)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(expDate, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expenditureDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(expAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveExpenseButton)
                        .addComponent(expenditureDeleteButton)
                        .addComponent(expDescField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(expCategoryCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        shopExpenditurePanel.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.CardLayout());

        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Amount", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(expenseTable);

        jPanel3.add(jScrollPane1, "card2");

        shopExpenditurePanel.add(jPanel3, java.awt.BorderLayout.CENTER);

        managerTab.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Shop Expenditure</body></html>", shopExpenditurePanel);

        reportPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(768, 60));

        jButton17.setText("Show");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Print Report");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Shop", "The New Chitra Boutiques" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("From");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("To");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Select Report Pattern");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17)
                        .addComponent(jButton18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reportPanel.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setLayout(new java.awt.CardLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Shop Name", "Date", "Total Sell", "Total Paid", "Total Due"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2, "card2");

        reportPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        managerTab.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Sell Report</body></html>", reportPanel);

        managerPanel.add(managerTab, "card2");

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Manager</body></html>", managerPanel);

        viewPanel.add(userTabPanel, "card2");

        controllerPanel.add(viewPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(controllerPanel, "card2");

        selectionPanel.setLayout(new java.awt.GridBagLayout());

        shopSelectionEnclosedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Shop", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 22))); // NOI18N

        openShopButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        openShopButton.setText("Open");
        openShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openShopButtonActionPerformed(evt);
            }
        });

        createNewShopButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        createNewShopButton.setText("New Shop");
        createNewShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewShopButtonActionPerformed(evt);
            }
        });

        shopSelectCB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        shopSelectCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "The New Chitra Boutiques", "Chitra Boutiques" }));

        shopSelectErrorLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shopSelectErrorLabel.setForeground(new java.awt.Color(255, 0, 0));

        addNewStaffButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addNewStaffButton.setText("Add New Staff");
        addNewStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStaffButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout shopSelectionEnclosedPanelLayout = new javax.swing.GroupLayout(shopSelectionEnclosedPanel);
        shopSelectionEnclosedPanel.setLayout(shopSelectionEnclosedPanelLayout);
        shopSelectionEnclosedPanelLayout.setHorizontalGroup(
            shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopSelectionEnclosedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shopSelectCB, 0, 987, Short.MAX_VALUE)
                    .addComponent(shopSelectErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(shopSelectionEnclosedPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addNewStaffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createNewShopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openShopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        shopSelectionEnclosedPanelLayout.setVerticalGroup(
            shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopSelectionEnclosedPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(shopSelectCB, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shopSelectErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(openShopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(createNewShopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addNewStaffButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 84;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(166, 125, 201, 161);
        selectionPanel.add(shopSelectionEnclosedPanel, gridBagConstraints);

        mainPanel.add(selectionPanel, "card2");

        loginPanel.setLayout(new java.awt.GridBagLayout());

        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usernameLabel.setText("Username / Mobile Number");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        passwordLabel.setText("Password");

        reset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        logIn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        logIn.setText("Log In");
        logIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInActionPerformed(evt);
            }
        });

        logInError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        logInError.setForeground(new java.awt.Color(255, 0, 0));

        forgotLabel.setText("<html><u>Forgot Password?</u><html>");
        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forgotLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                forgotLabelMouseExited(evt);
            }
        });

        jLabel1.setText("<html><u>New Account</u><html>");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout logPanelEnclosedLayout = new javax.swing.GroupLayout(logPanelEnclosed);
        logPanelEnclosed.setLayout(logPanelEnclosedLayout);
        logPanelEnclosedLayout.setHorizontalGroup(
            logPanelEnclosedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logPanelEnclosedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(logPanelEnclosedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logInError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(logPanelEnclosedLayout.createSequentialGroup()
                        .addGroup(logPanelEnclosedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(forgotLabel)
                            .addComponent(jLabel1))
                        .addGap(24, 24, 24)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logIn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(passwordField)
                    .addComponent(usernameField)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        logPanelEnclosedLayout.setVerticalGroup(
            logPanelEnclosedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPanelEnclosedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logInError, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(logPanelEnclosedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logPanelEnclosedLayout.createSequentialGroup()
                        .addComponent(forgotLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(logIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(105, 182, 134, 177);
        loginPanel.add(logPanelEnclosed, gridBagConstraints);

        mainPanel.add(loginPanel, "card4");

        acpEnclosedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Account Creation Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Confirm Password");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Name");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Password");

        backButtonACP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        backButtonACP.setText("BACK");
        backButtonACP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonACPActionPerformed(evt);
            }
        });

        authorityComboACP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        authorityComboACP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DEVELOPER", "MANAGER", "STAFF" }));

        addUserButtonACP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addUserButtonACP.setText("Add User");
        addUserButtonACP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonACPActionPerformed(evt);
            }
        });

        userCreationErrorLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Authority");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("Mobile");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Username");

        javax.swing.GroupLayout acpEnclosedPanelLayout = new javax.swing.GroupLayout(acpEnclosedPanel);
        acpEnclosedPanel.setLayout(acpEnclosedPanelLayout);
        acpEnclosedPanelLayout.setHorizontalGroup(
            acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acpEnclosedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(acpEnclosedPanelLayout.createSequentialGroup()
                        .addGroup(acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(acpEnclosedPanelLayout.createSequentialGroup()
                                .addGroup(acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acpEnclosedPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(backButtonACP, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addUserButtonACP, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(userFieldACP)
                    .addComponent(passField1ACP)
                    .addComponent(passField2ACP)
                    .addComponent(nameFieldACP)
                    .addComponent(mobileFieldACP)
                    .addComponent(authorityComboACP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userCreationErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        acpEnclosedPanelLayout.setVerticalGroup(
            acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acpEnclosedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userFieldACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passField1ACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passField2ACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameFieldACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobileFieldACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorityComboACP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userCreationErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(acpEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButtonACP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButtonACP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout accountCreatePanelLayout = new javax.swing.GroupLayout(accountCreatePanel);
        accountCreatePanel.setLayout(accountCreatePanelLayout);
        accountCreatePanelLayout.setHorizontalGroup(
            accountCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountCreatePanelLayout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(acpEnclosedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(334, Short.MAX_VALUE))
        );
        accountCreatePanelLayout.setVerticalGroup(
            accountCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountCreatePanelLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(acpEnclosedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        mainPanel.add(accountCreatePanel, "card5");

        staffAddPanel.setLayout(new java.awt.GridBagLayout());

        staffAddEncloed.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Staff Add ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("Select User");

        addUserStatusLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        addStafFinalButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addStafFinalButton.setText("ADD STAFF");
        addStafFinalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStafFinalButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Shop Name");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("BACK");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout staffAddEncloedLayout = new javax.swing.GroupLayout(staffAddEncloed);
        staffAddEncloed.setLayout(staffAddEncloedLayout);
        staffAddEncloedLayout.setHorizontalGroup(
            staffAddEncloedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffAddEncloedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(staffAddEncloedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(staffAddEncloedLayout.createSequentialGroup()
                        .addGroup(staffAddEncloedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addUserStatusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(shopCB, 0, 331, Short.MAX_VALUE)
                            .addGroup(staffAddEncloedLayout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addStafFinalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(userCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        staffAddEncloedLayout.setVerticalGroup(
            staffAddEncloedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffAddEncloedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shopCB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userCB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addUserStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(staffAddEncloedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStafFinalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(140, 239, 191, 283);
        staffAddPanel.add(staffAddEncloed, gridBagConstraints);

        mainPanel.add(staffAddPanel, "card7");

        getContentPane().add(mainPanel, "card2");

        fileMenu.setText("File");

        logoutItem.setText("Logout");
        logoutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutItemActionPerformed(evt);
            }
        });
        fileMenu.add(logoutItem);

        exitItem.setText("Exit");
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        backupDateItem.setText("Backup Data");
        editMenu.add(backupDateItem);

        menuBar.add(editMenu);

        windowMenu.setText("Window");
        menuBar.add(windowMenu);

        helpMenu.setText("Help");

        supportItem.setText("Get Support");
        helpMenu.add(supportItem);

        developerItem.setText("Developer");
        developerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                developerItemActionPerformed(evt);
            }
        });
        helpMenu.add(developerItem);

        aboutItem.setText("About");
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void developerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_developerItemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_developerItemActionPerformed

    private void openShopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openShopButtonActionPerformed
        // TODO add your handling code here:
        int index = shopSelectCB.getSelectedIndex();
        shop = index != -1 ? shops.get(index) : null;
        
        if (shop != null) {
            
            shopNameLabel.setText(shop.getShopName());
            shopAddressLabel.setText(shop.getShopAddress());
            softUserNameLabel.setText(user.getName());
            softUserMobileLabel.setText(user.getMobile());
            softUserRoleLabel.setText(user.getAuthority());
            panelSlider.changeThePanel(mainPanel, controllerPanel);

            /*Expense Category Loading*/
            shopExpenses = shopExpenseService.getShopExpenses(shop);
            expenseAdder.addCategoriesInComboBox(shopExpenses, expCategoryCB);
            
        } else {
            shopSelectErrorLabel.setText(Message.ERROR_ROLE_NOT_ASSIGNED);
        }
        

    }//GEN-LAST:event_openShopButtonActionPerformed

    private void forgotLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotLabelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotLabelMouseExited

    private void forgotLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotLabelMouseEntered

    }//GEN-LAST:event_forgotLabelMouseEntered

    private void forgotLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotLabelMouseClicked
        // TODO add your handling code here:
        System.out.println("Mouse Clicked!");
        panelSlider.changeThePanel(mainPanel, accountCreatePanel);
    }//GEN-LAST:event_forgotLabelMouseClicked

    private void logInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInActionPerformed
        // TODO add your handling code here:
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (loginValidation.loginValidation(username, password)) {
            user = userService.getUser(username, password);
            System.out.println(user);
            if (user != null) {
                if (user.getAuthority().equals(Role.USER_DEVELOPER)) {
                    
                    devHelper.addRole(authorityComboACP);
                    panelSlider.changeThePanel(mainPanel, accountCreatePanel);
                    
                } else if (user.getAuthority().equals(Role.USER_MANAGER) || user.getAuthority().equals(Role.USER_STAFF)) {
                    shops = shopService.getShops(user);
                    
                    System.out.println(shops);
                    shopSelectCB.removeAllItems();
                    for (Shop shop : shops) {
                        shopSelectCB.addItem(shop.getShopName());
                    }
                    
                    if (user.getAuthority().equals(Role.USER_STAFF)) {
                        createNewShopButton.setEnabled(false);
                        addNewStaffButton.setEnabled(false);
                    } else {
                        addNewStaffButton.setEnabled(true);
                        createNewShopButton.setEnabled(true);
                    }
                    
                    panelSlider.changeThePanel(mainPanel, selectionPanel);
                }
                logoutItem.setEnabled(true);
            } else {
                logInError.setText(Message.ERROR_INVALID_USRER_INFO);
            }
        } else {
            logInError.setText(Message.ERROR_INVALID_USRER_INFO);
        }
    }//GEN-LAST:event_logInActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        usernameField.setText("");
        passwordField.setText("");
    }//GEN-LAST:event_resetActionPerformed

    private void createNewShopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewShopButtonActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, addNewShopPanel);
    }//GEN-LAST:event_createNewShopButtonActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void logoutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutItemActionPerformed
        // TODO add your handling code here:
        usernameField.setText("");
        passwordField.setText("");
        panelSlider.changeThePanel(mainPanel, loginPanel);
        logoutItem.setEnabled(false);

        /*Reset Item*/
        resetter.resetTable(stockTable);
    }//GEN-LAST:event_logoutItemActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        userFieldACP.setText("");
        passField1ACP.setText("");
        passField2ACP.setText("");
        nameFieldACP.setText("");
        mobileFieldACP.setText("");
        staffHelper.addRole(authorityComboACP);
        panelSlider.changeThePanel(mainPanel, accountCreatePanel);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void addNewStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStaffButtonActionPerformed
        // TODO add your handling code here:
        users = userService.getUsers(Role.USER_STAFF);
        shopCB.removeAllItems();
        userCB.removeAllItems();
        for (Shop shop : shops) {
            shopCB.addItem(shop.getShopName());
        }
        
        for (User user : users) {
            userCB.addItem(user.getName() + " " + user.getMobile());
        }
        
        panelSlider.changeThePanel(mainPanel, staffAddPanel);

    }//GEN-LAST:event_addNewStaffButtonActionPerformed

    private void addStafFinalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStafFinalButtonActionPerformed
        // TODO add your handling code here:
        int userIndex = userCB.getSelectedIndex();
        int shopIndex = shopCB.getSelectedIndex();
        
        if (userIndex != -1 && shopIndex != -1) {
            int userId = users.get(userIndex).getUserId();
            int shopId = shops.get(shopIndex).getShopId();
            boolean isStaffAdded = shopOwnerService.createShopOwner(userId, shopId);
            if (isStaffAdded) {
                addUserStatusLabel.setForeground(Color.GREEN);
                addUserStatusLabel.setText(Message.SUCCESS_STAFF_ADDED);
            } else {
                addUserStatusLabel.setForeground(Color.RED);
                addUserStatusLabel.setText(Message.ERROR_ROLE_MAYBE_ASSIGNED);
            }
        } else {
            addUserStatusLabel.setForeground(Color.RED);
            addUserStatusLabel.setText(Message.ERROR_NOT_HAVE_STAFF_OR_SHOP);
        }

    }//GEN-LAST:event_addStafFinalButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, selectionPanel);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void addUserButtonACPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonACPActionPerformed
        // TODO add your handling code here:
        User user = new User(userFieldACP.getText(),
                passField1ACP.getText(),
                nameFieldACP.getText().toUpperCase(),
                mobileFieldACP.getText(),
                (String) authorityComboACP.getItemAt(authorityComboACP.getSelectedIndex()),
                true);
        
        if (user.getName().equals("")
                || user.getMobile().equals("")
                || user.getUsername().equals("")
                || user.getPassword().equals("")) {
            userCreationErrorLabel.setForeground(Color.red);
            userCreationErrorLabel.setText(Message.ERROR_INVALID_INPUT);
        } else {
            if (passField1ACP.getText().equals(passField2ACP.getText())) {
                System.out.println(user);
                if (userService.getUser(user.getUsername()) == null && userService.getUser(user.getMobile()) == null) {
                    boolean isUserSaved = userService.createUser(user);
                    userCreationErrorLabel.setForeground(Color.green);
                    userCreationErrorLabel.setText(Message.SUCCESS_USER_CREATED);
                    userFieldACP.setText("");
                    passField1ACP.setText("");
                    passField2ACP.setText("");
                    nameFieldACP.setText("");
                    mobileFieldACP.setText("");
                } else {
                    userCreationErrorLabel.setForeground(Color.red);
                    userCreationErrorLabel.setText(Message.ERROR_SAME_USERNAME_OR_MOBILE);
                }
            } else {
                passField1ACP.setText("");
                passField2ACP.setText("");
                userCreationErrorLabel.setForeground(Color.red);
                userCreationErrorLabel.setText(Message.ERROR_PASSWORD_NOT_MATCHED);
            }
        }
        

    }//GEN-LAST:event_addUserButtonACPActionPerformed

    private void backButtonACPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonACPActionPerformed
        // TODO add your handling code here:
        usernameField.setText("");
        passwordField.setText("");
        panelSlider.changeThePanel(mainPanel, loginPanel);
    }//GEN-LAST:event_backButtonACPActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, selectionPanel);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void createShopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createShopButtonActionPerformed
        // TODO add your handling code here:
        Shop shop = new Shop(shopNameField.getText().toUpperCase(),
                shopTinField.getText(),
                shopAddressField.getText().toUpperCase(),
                shopMobileField.getText());
        System.out.println(user);
        System.out.println(shop);
        
        if (shop.getShopAddress().replace(" ", "").equals("")
                || shop.getShopName().replace(" ", "").equals("")
                || shop.getShopTin().replace(" ", "").equals("")
                || shop.getShopMobile().replace(" ", "").equals("")) {
            shopCreationLabel.setForeground(Color.red);
            shopCreationLabel.setText(Message.ERROR_INVALID_INPUT);
        } else {
            boolean isShopSaved = shopService.createShop(user, shop);
            if (isShopSaved) {
                shopNameField.setText("");
                shopTinField.setText("");
                shopAddressField.setText("");
                shopMobileField.setText("");
                shops = shopService.getShops(user);
                shopSelectCB.removeAllItems();
                for (Shop shopObject : shops) {
                    shopSelectCB.addItem(shopObject.getShopName());
                }
                shopCreationLabel.setForeground(Color.green);
                shopCreationLabel.setText(Message.SUCCESS_SHOP_CREATED);
            } else {
                shopCreationLabel.setForeground(Color.red);
                shopCreationLabel.setText(Message.ERROR_DUPLICATE_DATA);
            }
        }
    }//GEN-LAST:event_createShopButtonActionPerformed

    private void orderCompleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderCompleteButtonActionPerformed
        // TODO add your handling code here:
        int orderComplete = JOptionPane.showConfirmDialog(this, "Confirm Order?");
        
        if (JOptionPane.YES_OPTION == orderComplete) {
            int printBill = JOptionPane.showConfirmDialog(this, "Print Bill?");
            initOrder();
        }
    }//GEN-LAST:event_orderCompleteButtonActionPerformed

    private void newOrderCreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOrderCreateButtonActionPerformed
        // TODO add your handling code here:
        boolean newOrderValid = false;
        
        if (shopOrder != null && shopOrder.getOrderProducts().isEmpty()) {
            newOrderValid = true;
        } else {
            int previousOrderValid = JOptionPane.showConfirmDialog(this, "Sure to continue without completing current order?");
            if (previousOrderValid == JOptionPane.YES_OPTION) {
                newOrderValid = true;
            }
        }
        
        if (newOrderValid) {
            initOrder();
        }

    }//GEN-LAST:event_newOrderCreateButtonActionPerformed

    private void shopAddressFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopAddressFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shopAddressFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String categoryName = JOptionPane.showInputDialog(this, "Category Name", "Add New Category", JOptionPane.INFORMATION_MESSAGE);
        
        if (categoryName != null) {
            categoryName = categoryName.toUpperCase();
            ExpenseCategory expCategory = shopExpenseService.getExpenseCategory(shop.getShopId(), categoryName);
            if (expCategory == null) {
                expCategory = new ExpenseCategory(shop.getShopId(), categoryName);
                shopExpenseService.createExpenseCategory(expCategory);
                shopExpenses = shopExpenseService.getShopExpenses(shop);
                expenseAdder.addCategoriesInComboBox(shopExpenses, expCategoryCB);
                expenseAdder.addExpendituresInTable(shopExpenses.get(Enum.firstIndex).getExpenditures(), expenseTable);
            } else {
                JOptionPane.showMessageDialog(null, "Duplicate Category!");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bcodeGenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcodeGenButtonActionPerformed
        // TODO add your handling code here:
//        BarCodeGenerate barcode = new BarCodeGenerate();
        boolean barcodeGenerate = true;
        try {
            int productBarcodeQuantity = Integer.parseInt(quantityCodeField.getText());
            productBarcodeQuantity = Math.abs(productBarcodeQuantity);
            String productBarcode = previousCodeField.getText();
            
            if (previousCodeField.getText().replace(" ", "").equals("")) {
                productBarcode = idBuilder.geProductUniqueID();
                barcodeQuantityLabel.setText("");
            } else {
                if (productBarcode.length() == 13) {
                    
                    if (productBarcode.charAt(0) != '1') {
                        barcodeQuantityLabel.setForeground(Color.RED);
                        barcodeQuantityLabel.setText(Message.ERROR_INVALID_BARCODE_ID);
                        previousCodeField.setText("");
                        barcodeGenerate = false;
                    }
                } else {
                    barcodeQuantityLabel.setText(Message.ERROR_INVALID_BARCODE_SIZE);
                    previousCodeField.setText("");
                    barcodeGenerate = false;
                }
            }
            
            if (barcodeGenerate) {
                
                barcodeViewPanel.removeAll();
                barcodeViewPanel.repaint();
                barcodeViewPanel.revalidate();
                
                JLabel label = new JLabel(productBarcode);
                label.setForeground(Color.red);
                label.setSize(new Dimension(300, 80));
                label.setLocation((barcodeViewPanel.getWidth() / 2) - 25, (barcodeViewPanel.getHeight() / 2));
                label.setSize(300, 30);
                
                barcodeViewPanel.add(label);
                barcodeViewPanel.repaint();
                barcodeViewPanel.revalidate();

                BarcodePdf barcodePdf = new BarcodePdf(productBarcodeQuantity, productBarcode);
                try {
                    barcodePdf.generateBarcodePdf();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(PosScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (NumberFormatException nfex) {
            
            System.out.println(nfex.getMessage());
            barcodeQuantityLabel.setForeground(Color.RED);
            barcodeQuantityLabel.setText(Message.ERROR_INVALID_BARCODE_ID);
        }

    }//GEN-LAST:event_bcodeGenButtonActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try {
            Product product = new Product(shop.getShopId(),
                    psCodeField.getText(),
                    psNameField.getText(),
                    Integer.parseInt(psQuantityField.getText()),
                    Double.parseDouble(psRateField.getText()));
            
            System.out.println(product);
            Product demoProduct = productService.getProduct(shop, product.getProductBarcode());
            
            System.out.println(product);
            if (demoProduct == null) {
                productService.createProduct(product);
            } else {
                productService.updateProduct(product);
            }
            
            products = productService.getProducts(shop);
            productAdder.addProductsInStock(products, stockTable);
            
            psCodeField.setText("");
            psNameField.setText("");
            psQuantityField.setText("");
            psRateField.setText("");
            
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void refreshStocksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshStocksButtonActionPerformed
        // TODO add your handling code here:
        if (shop != null) {
            products = productService.getProducts(shop);
            productAdder.addProductsInStock(products, stockTable);
        }
    }//GEN-LAST:event_refreshStocksButtonActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        int stockTableSelectedRow = stockTable.getSelectedRow();
        if (stockTableSelectedRow != -1) {
            Product product = products.get(stockTableSelectedRow);
            boolean isDeleted = productService.deleteProductById(product.getProductId());
            if (isDeleted) {
                products.remove(stockTableSelectedRow);
                productAdder.addProductsInStock(products, stockTable);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void saveExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExpenseButtonActionPerformed
        // TODO add your handling code here:
        int categoryIndex = expCategoryCB.getSelectedIndex();
        ExpenseCategory expCategory = shopExpenses.get(categoryIndex).getExpenseCategory();
        Date date = expDate.getDate();
        Timestamp timestamp;
        if (date != null) {
            timestamp = convert.convertDateToTimestamp(date);
        } else {
            timestamp = convert.convertDateToTimestamp(new Date());
        }
        try {
            Expenditure expenditure = new Expenditure(expCategory.getExpCategoryId(), Double.parseDouble(expAmountField.getText()), timestamp, expDescField.getText());
            System.out.println(expenditure);
            boolean status = shopExpenseService.saveExpenditure(expenditure);
            if (status) {
                List<Expenditure> expenditures = shopExpenseService.getExpenditures(expCategory);
                shopExpenses.get(categoryIndex).setExpenditures(expenditures);
                expenseAdder.addExpendituresInTable(expenditures, expenseTable);
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
        
        expAmountField.setText("");
        expDescField.setText("");
    }//GEN-LAST:event_saveExpenseButtonActionPerformed

    private void expCategoryCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expCategoryCBActionPerformed
        // TODO add your handling code here:
        int index = expCategoryCB.getSelectedIndex();
        if (index != Enum.invalidIndex) {
            System.out.println("Selected Category:" + index);
            expenseAdder.addExpendituresInTable(shopExpenses.get(index).getExpenditures(), expenseTable);
        }
    }//GEN-LAST:event_expCategoryCBActionPerformed

    private void expenditureDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenditureDeleteButtonActionPerformed
        // TODO add your handling code here:
        int expCategoryIndex = expCategoryCB.getSelectedIndex();
        int expenseIndex = expenseTable.getSelectedRow();
        if (expCategoryIndex != Enum.invalidIndex && expenseIndex != Enum.invalidIndex) {
            ShopExpense shopExpense = shopExpenses.get(expCategoryIndex);
            Expenditure expenditure = shopExpense.getExpenditures().get(expenseIndex);
            System.out.println(expenditure);
            boolean status = shopExpenseService.deleteExpenditure(expenditure);
            if (status) {
                List<Expenditure> expenditures = shopExpenses.get(expCategoryIndex).getExpenditures();
                expenditures.remove(expenseIndex);
                expenseAdder.addExpendituresInTable(expenditures, expenseTable);
            } else {
                
            }
        }
    }//GEN-LAST:event_expenditureDeleteButtonActionPerformed

    private void orderPProductBarcodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderPProductBarcodeFieldActionPerformed

    private void orderPProductBarcodeFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldPropertyChange

    }//GEN-LAST:event_orderPProductBarcodeFieldPropertyChange

    private void orderPProductBarcodeFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldKeyPressed
        // TODO add your handling code here:
        String productCode = orderPProductBarcodeField.getText();
        if (productCode != null && productCode.length() == 13) {
        }
    }//GEN-LAST:event_orderPProductBarcodeFieldKeyPressed

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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PosScreen().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JPanel accountCreatePanel;
    private javax.swing.JPanel acpEnclosedPanel;
    private javax.swing.JPanel addNewShopPanel;
    private javax.swing.JButton addNewStaffButton;
    private javax.swing.JButton addOItemButton;
    private javax.swing.JButton addStafFinalButton;
    private javax.swing.JButton addUserButtonACP;
    private javax.swing.JLabel addUserStatusLabel;
    private javax.swing.JComboBox authorityComboACP;
    private javax.swing.JButton backButtonACP;
    private javax.swing.JMenuItem backupDateItem;
    private javax.swing.JPanel barcodeGenerationPanel;
    private javax.swing.JPanel barcodeInputPanel;
    private javax.swing.JLabel barcodeQuantityLabel;
    private javax.swing.JPanel barcodeSimulatePanel;
    private javax.swing.JPanel barcodeViewPanel;
    private javax.swing.JButton bcodeGenButton;
    private javax.swing.JLabel cashBackViewLabel;
    private javax.swing.JTextField cashField;
    private javax.swing.JPanel controllerPanel;
    private javax.swing.JButton createNewShopButton;
    private javax.swing.JButton createShopButton;
    private javax.swing.JTextField customerMobileField;
    private javax.swing.JTextField customerNameField;
    private javax.swing.JPanel customerOrderPanel;
    private javax.swing.JMenuItem developerItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JTextField expAmountField;
    private javax.swing.JComboBox expCategoryCB;
    private com.toedter.calendar.JDateChooser expDate;
    private javax.swing.JTextField expDescField;
    private javax.swing.JButton expenditureDeleteButton;
    private javax.swing.JTable expenseTable;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel forgotLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel inStockLabel;
    private javax.swing.JLabel inStockViewLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton logIn;
    private javax.swing.JLabel logInError;
    private javax.swing.JPanel logPanelEnclosed;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JMenuItem logoutItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel managerPanel;
    private javax.swing.JPanel managerShopStockPanel;
    private javax.swing.JTabbedPane managerTab;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField mobileFieldACP;
    private javax.swing.JLabel mobileLabel;
    private javax.swing.JTextField nameFieldACP;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton newOrderCreateButton;
    private javax.swing.JButton openShopButton;
    private javax.swing.JButton orderCompleteButton;
    private javax.swing.JPanel orderFinalizePanel;
    private javax.swing.JLabel orderIDViewLabel;
    private javax.swing.JPanel orderInputPanel;
    private javax.swing.JTextField orderPProductBarcodeField;
    private javax.swing.JPanel orderViewPanel;
    private javax.swing.JScrollPane orderViewScroll;
    private javax.swing.JTable orderViewTable;
    private javax.swing.JPasswordField passField1ACP;
    private javax.swing.JPasswordField passField2ACP;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField previousCodeField;
    private javax.swing.JComboBox printerCB;
    private javax.swing.JLabel productCodeLabel;
    private javax.swing.JLabel productInfoLabel;
    private javax.swing.JTextField psCodeField;
    private javax.swing.JTextField psNameField;
    private javax.swing.JTextField psQuantityField;
    private javax.swing.JTextField psRateField;
    private javax.swing.JTextField quantityCodeField;
    private javax.swing.JButton refreshStocksButton;
    private javax.swing.JButton removeOtItemButton;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JButton reset;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JButton saveExpenseButton;
    private javax.swing.JPanel searchInputPanel;
    private javax.swing.JPanel searchProductPanel;
    private javax.swing.JTable searchTable;
    private javax.swing.JPanel searchViewPanel;
    private javax.swing.JScrollPane searchViewScroll;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.JTextField shopAddressField;
    private javax.swing.JLabel shopAddressLabel;
    private javax.swing.JComboBox shopCB;
    private javax.swing.JLabel shopCreationLabel;
    private javax.swing.JPanel shopExpenditurePanel;
    private javax.swing.JTextField shopMobileField;
    private javax.swing.JTextField shopNameField;
    private javax.swing.JLabel shopNameLabel;
    private javax.swing.JComboBox shopSelectCB;
    private javax.swing.JLabel shopSelectErrorLabel;
    private javax.swing.JPanel shopSelectionEnclosedPanel;
    private javax.swing.JTextField shopTinField;
    private javax.swing.JPanel signBoardPanel;
    private javax.swing.JLabel softUserMobileLabel;
    private javax.swing.JLabel softUserNameLabel;
    private javax.swing.JLabel softUserRoleLabel;
    private javax.swing.JPanel staffAddEncloed;
    private javax.swing.JPanel staffAddPanel;
    private javax.swing.JPanel stockInputPanel;
    private javax.swing.JScrollPane stockScroll;
    private javax.swing.JTable stockTable;
    private javax.swing.JMenuItem supportItem;
    private javax.swing.JLabel totalBillViewLabel;
    private javax.swing.JComboBox userCB;
    private javax.swing.JLabel userCreationErrorLabel;
    private javax.swing.JTextField userFieldACP;
    private javax.swing.JTabbedPane userTabPanel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JPanel viewPanel;
    private javax.swing.JMenu windowMenu;
    // End of variables declaration//GEN-END:variables
}
