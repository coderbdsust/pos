/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.backup.Backup;
import com.coderbd.pos.backup.CryptoUtils;
import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.constraints.Message;
import com.coderbd.pos.constraints.Role;
import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.entity.pojo.ShopBalance;
import com.coderbd.pos.entity.pojo.ShopExpense;
import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.entity.pojo.SupplierOrderProductReport;
import com.coderbd.pos.entity.pojo.SupplierOrderReport;
import com.coderbd.pos.pdf.BarcodePdf;
import com.coderbd.pos.pdf.CodePDF;
import com.coderbd.pos.pdf.OrderFileBuilder;
import com.coderbd.pos.print.Printer;
import com.coderbd.pos.print.PrinterLookUp;
import com.coderbd.pos.service.CustomerOrderService;
import com.coderbd.pos.service.ProductService;
import com.coderbd.pos.service.ShopExpenseService;
import com.coderbd.pos.service.ShopOwnerService;
import com.coderbd.pos.service.ShopService;
import com.coderbd.pos.service.SupplierOrderPaymentService;
import com.coderbd.pos.service.SupplierOrderService;
import com.coderbd.pos.service.SupplierProductService;
import com.coderbd.pos.service.SupplierReturnProductService;
import com.coderbd.pos.service.SupplierService;
import com.coderbd.pos.service.UserService;
import com.coderbd.pos.utils.DateUtil;
import com.coderbd.pos.utils.DeveloperHelper;
import com.coderbd.pos.utils.IDBuilder;
import com.coderbd.pos.utils.ReceiptIndent;
import com.coderbd.pos.utils.Reset;
import com.coderbd.pos.utils.Search;
import com.coderbd.pos.utils.StaffHelper;
import com.coderbd.pos.utils.adder.ExpenseAdder;
import com.coderbd.pos.utils.adder.OrderProductAdder;
import com.coderbd.pos.utils.adder.ProductAdder;
import com.coderbd.pos.utils.adder.SellReportAdder;
import com.coderbd.pos.utils.adder.ShopOrderAdder;
import com.coderbd.pos.utils.adder.SupplierAdder;
import com.coderbd.pos.utils.adder.SupplierOrderProductReportAdder;
import com.coderbd.pos.utils.index.SOPIndex;
import com.coderbd.pos.validation.Valid;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
    private CustomerOrderService customerOrderService;

    private SupplierService supplierService;
    private SupplierOrderService supplierOrderService;
    private SupplierProductService supplierProductService;
    private SupplierOrderPaymentService supplierOrderPaymentService;
    private SupplierReturnProductService supplierReturnProductService;

    private DeveloperHelper devHelper;

    private StaffHelper staffHelper;
    private Reset resetTable;

    private PanelSlider panelSlider;
    private Valid loginValidation;
    private ProductAdder productAdder;
    private OrderProductAdder orderProductAdder;
    private SellReportAdder sellReportAdder;
    private SupplierAdder supplierAdder;
    private ExpenseAdder expenseAdder;
    private ShopOrderAdder shopOrderAdder;
    private IDBuilder idBuilder;
    private ReceiptIndent reciptIndent;

    /*User authorized shop list*/
    private List<Shop> shops;
    private List<User> users;
    private List<ShopBalance> shopBalances;
    /*Selected Shop's Products*/
    private List<Product> products;
    /*Selected Shop's Category wise expenditures*/
    private List<ShopExpense> shopExpenses;
    private List<ShopOrder> shopOrders;
    /*All available suppliers*/
    private List<Supplier> suppliers;
    private List<SOPIndex> sopIndexs;
    /*Login User Info*/
    private User user;
    /*User selected shop*/
    private Shop shop;
    /*Customer Shop Order Object*/
    private ShopOrder shopOrder;

    private Search search;
    private PrinterLookUp printerLookUp;

    public PosScreen() {
        initComponents();
        /**
         *
         * Below method are added for object initialize
         */
        initInstance();
        initUI();
        initAppContext();
    }

    private void initInstance() {
        panelSlider = new PanelSlider();
        loginValidation = new Valid();
        devHelper = new DeveloperHelper();
        staffHelper = new StaffHelper();
        productAdder = new ProductAdder();
        expenseAdder = new ExpenseAdder();
        resetTable = new Reset();
        idBuilder = new IDBuilder();
        orderProductAdder = new OrderProductAdder();
        search = new Search();
        printerLookUp = new PrinterLookUp();
        shopOrderAdder = new ShopOrderAdder();
        reciptIndent = new ReceiptIndent();
        sellReportAdder = new SellReportAdder();
        supplierAdder = new SupplierAdder();
    }

    public void initUI() {
        logoutItem.setEnabled(false);
        optionItem.setEnabled(false);
        backupDateItem.setEnabled(false);
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
            customerOrderService = (CustomerOrderService) appContext.getBean("customerOrderService");

            supplierService = (SupplierService) appContext.getBean("supplierService");
            supplierOrderService = (SupplierOrderService) appContext.getBean("supplierOrderService");
            supplierProductService = (SupplierProductService) appContext.getBean("supplierProductService");
            supplierOrderPaymentService = (SupplierOrderPaymentService) appContext.getBean("supplierOrderPaymentService");
            supplierReturnProductService = (SupplierReturnProductService) appContext.getBean("supplierReturnProductService");
        }
    }

    private void initOrder() {
        productInfoLabel.setText("");
        resetTable.resetTable(orderViewTable);
        totalBillViewLabel.setText("");
        cashBackViewLabel.setText("");
        cashField.setText("");
        customerMobileField.setText("");
        customerNameField.setText("");

        shopOrder = new ShopOrder();
        String orderId = idBuilder.geOrderUniqueID();
        orderIDViewLabel.setText(orderId);
        Timestamp orderTime = DateUtil.convertDateToTimestamp(new Date());
        shopOrder.getCustomerOrder().setOrderBarcode(orderId);
        shopOrder.getCustomerOrder().setOrderTime(orderTime);
        shopOrder.getCustomerOrder().setShop(shop);
        shopOrder.getCustomerOrder().setUser(user);

        System.out.println("Order: " + shopOrder);
    }

    private void updateStockView() {
        if (shop != null) {
            System.out.println("Stock View Updated");
//            products = productService.getProducts(shop);
            productAdder.addProductsInStockView(products, stockTable);
        }
    }

    private void updateOrderView() {

        shopOrders = customerOrderService.getShopOrders(shop, user);

        if (shopOrders != null) {
            System.out.println("Order View Updated");
            shopOrderAdder.addCustomerOrderInOrderView(shopOrders, completeOrderTable);
        } else {
            System.out.println("Not Have Enough Customer Orders");
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
        viewPanel = new javax.swing.JPanel();
        userTabPanel = new javax.swing.JTabbedPane();
        customerOrderPanel = new javax.swing.JPanel();
        orderInputPanel = new javax.swing.JPanel();
        productCodeLabel = new javax.swing.JLabel();
        orderPProductBarcodeField = new javax.swing.JTextField();
        newOrderCreateButton = new javax.swing.JButton();
        removeOtItemButton = new javax.swing.JButton();
        productInfoLabel = new javax.swing.JLabel();
        discountField = new javax.swing.JTextField();
        vatField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        defaultSellRateChoose = new javax.swing.JCheckBox();
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
        psBuyRateField = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        psSellRateField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        showProductButton = new javax.swing.JButton();
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
        jLabel5 = new javax.swing.JLabel();
        categoryExpenseTotalLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        oldOrderViewPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        OrderIDCodeField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        completeOrderTable = new javax.swing.JTable();
        selectionPanel = new javax.swing.JPanel();
        shopSelectionEnclosedPanel = new javax.swing.JPanel();
        openShopButton = new javax.swing.JButton();
        shopSelectCB = new javax.swing.JComboBox();
        shopSelectErrorLabel = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
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
        optionPanel = new javax.swing.JPanel();
        productSellButton = new javax.swing.JButton();
        supplierButton = new javax.swing.JButton();
        addNewStaffButton = new javax.swing.JButton();
        createNewShopButton = new javax.swing.JButton();
        reportButton = new javax.swing.JButton();
        supplierDetailsPanel = new javax.swing.JPanel();
        supplyViewPanel = new javax.swing.JPanel();
        supplierTabPanned = new javax.swing.JTabbedPane();
        supplyOrderEntryPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        supplierCB = new javax.swing.JComboBox();
        jButton12 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        supplierOrderDate = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        supplierTotalBillLabel = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        supplierTotalPaidLabel = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        supplierTotalDueLabel = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        orderProductSellButton = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        supplierTable = new javax.swing.JTable();
        supplyProductDistPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        distSupplierNameCB = new javax.swing.JComboBox();
        distProductNameCB = new javax.swing.JComboBox();
        distQuantityCB = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        distShopCB = new javax.swing.JComboBox();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        distSellRateField = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        supplyBarcodeLabel = new javax.swing.JButton();
        fixedRateCkB = new javax.swing.JCheckBox();
        supplierBuyRateField = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        productSuppliedTable = new javax.swing.JTable();
        annualReportPanel = new javax.swing.JPanel();
        reportToolBarPanel = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        reportViewPanel = new javax.swing.JPanel();
        reportTabbedPane = new javax.swing.JTabbedPane();
        reportPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        sellReportAllShopCB = new javax.swing.JComboBox();
        sellDateFrom = new com.toedter.calendar.JDateChooser();
        sellDateTo = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalSellLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        totalPaidLabel = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        totalDueLabel = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        totalProfitLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sellReportTable = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        logoutItem = new javax.swing.JMenuItem();
        optionItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        backupDateItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        supportItem = new javax.swing.JMenuItem();
        developerItem = new javax.swing.JMenuItem();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(950, 650));
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

        javax.swing.GroupLayout signBoardPanelLayout = new javax.swing.GroupLayout(signBoardPanel);
        signBoardPanel.setLayout(signBoardPanelLayout);
        signBoardPanelLayout.setHorizontalGroup(
            signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signBoardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(shopNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(shopAddressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
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
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(softUserRoleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(signBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(softUserMobileLabel)
                        .addComponent(shopAddressLabel)
                        .addComponent(mobileLabel)
                        .addComponent(roleLabel)))
                .addContainerGap())
        );

        controllerPanel.add(signBoardPanel, java.awt.BorderLayout.PAGE_START);

        viewPanel.setLayout(new java.awt.CardLayout());

        userTabPanel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                userTabPanelStateChanged(evt);
            }
        });

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

        newOrderCreateButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newOrderCreateButton.setText("New Order");
        newOrderCreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderCreateButtonActionPerformed(evt);
            }
        });

        removeOtItemButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        removeOtItemButton.setText("Remove Item");
        removeOtItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOtItemButtonActionPerformed(evt);
            }
        });

        productInfoLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Discount %");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("VAT %");

        defaultSellRateChoose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        defaultSellRateChoose.setSelected(true);
        defaultSellRateChoose.setText("Use Default Sell Rate");

        javax.swing.GroupLayout orderInputPanelLayout = new javax.swing.GroupLayout(orderInputPanel);
        orderInputPanel.setLayout(orderInputPanelLayout);
        orderInputPanelLayout.setHorizontalGroup(
            orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orderPProductBarcodeField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultSellRateChoose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(discountField)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vatField)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
                    .addComponent(productInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeOtItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newOrderCreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        orderInputPanelLayout.setVerticalGroup(
            orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productCodeLabel)
                            .addComponent(jLabel9)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vatField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(orderPProductBarcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(discountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(newOrderCreateButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(orderInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(removeOtItemButton))
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(productInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(orderInputPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(defaultSellRateChoose)))
                .addContainerGap(18, Short.MAX_VALUE))
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
        cashField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cashFieldPropertyChange(evt);
            }
        });
        cashField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cashFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cashFieldKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Due");

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
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(customerMobileField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(customerNameField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orderIDViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderCompleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(orderIDViewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(orderFinalizePanelLayout.createSequentialGroup()
                                .addGroup(orderFinalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cashBackViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel15))
                                .addGap(0, 8, Short.MAX_VALUE))))
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
                "Index", "Product Code", "Product Name", "Quantity", "Sell Rate (Unit)", "Discount %", "VAT %", "Amount"
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
        orderViewTable.getTableHeader().setReorderingAllowed(false);
        orderViewTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                orderViewTablePropertyChange(evt);
            }
        });
        orderViewScroll.setViewportView(orderViewTable);

        orderViewPanel.add(orderViewScroll, "card2");

        customerOrderPanel.add(orderViewPanel, java.awt.BorderLayout.CENTER);

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=3>Customer Order</body></html>", customerOrderPanel);

        managerPanel.setLayout(new java.awt.CardLayout());

        managerTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                managerTabStateChanged(evt);
            }
        });

        managerShopStockPanel.setLayout(new java.awt.BorderLayout());

        stockInputPanel.setPreferredSize(new java.awt.Dimension(758, 90));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Product Code");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Product Name");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Quantity");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Buy Rate (Unit)");

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

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Sell Rate (Unit)");

        showProductButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        showProductButton.setText("Show Details");
        showProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showProductButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stockInputPanelLayout = new javax.swing.GroupLayout(stockInputPanel);
        stockInputPanel.setLayout(stockInputPanelLayout);
        stockInputPanelLayout.setHorizontalGroup(
            stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showProductButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(stockInputPanelLayout.createSequentialGroup()
                        .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jLabel21)
                            .addComponent(psBuyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(stockInputPanelLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(109, 274, Short.MAX_VALUE))
                            .addGroup(stockInputPanelLayout.createSequentialGroup()
                                .addComponent(psSellRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        stockInputPanelLayout.setVerticalGroup(
            stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockInputPanelLayout.createSequentialGroup()
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(psCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psBuyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(psSellRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        managerShopStockPanel.add(stockInputPanel, java.awt.BorderLayout.PAGE_START);

        stockTable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product Code", "Product Name", "Quantity", "Buy Rate", "Sell Rate", "Last Updated"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stockTable.getTableHeader().setReorderingAllowed(false);
        stockTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                stockTablePropertyChange(evt);
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
        jLabel28.setText("Date");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Amount");

        expDescField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expDescFieldActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Description");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Total :");

        categoryExpenseTotalLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        categoryExpenseTotalLabel.setText("0.0");

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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(expDate, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(expAmountField, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 168, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryExpenseTotalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(406, 406, 406))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(expDescField, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expenditureDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(170, 170, 170))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
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
                        .addComponent(expCategoryCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(expDescField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryExpenseTotalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        expenseTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(expenseTable);

        jPanel3.add(jScrollPane1, "card2");

        shopExpenditurePanel.add(jPanel3, java.awt.BorderLayout.CENTER);

        managerTab.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Shop Expenditure</body></html>", shopExpenditurePanel);

        oldOrderViewPanel.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(879, 110));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Order ID :");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Cancel Order");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Print Order Receipt");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setText("Order Details");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OrderIDCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OrderIDCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        oldOrderViewPanel.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new java.awt.CardLayout());

        completeOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Order Barcode", "Date", "Customer Name", "Mobile", "Total Bill", "Paid", "Due"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        completeOrderTable.getTableHeader().setReorderingAllowed(false);
        completeOrderTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                completeOrderTablePropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(completeOrderTable);

        jPanel8.add(jScrollPane3, "card2");

        oldOrderViewPanel.add(jPanel8, java.awt.BorderLayout.CENTER);

        managerTab.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Order View</body></html>", oldOrderViewPanel);

        managerPanel.add(managerTab, "card2");

        userTabPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Maintenance</body></html>", managerPanel);

        viewPanel.add(userTabPanel, "card2");

        controllerPanel.add(viewPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(controllerPanel, "card2");

        selectionPanel.setLayout(new java.awt.GridBagLayout());

        shopSelectionEnclosedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Shop", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 22))); // NOI18N
        shopSelectionEnclosedPanel.setMinimumSize(new java.awt.Dimension(500, 200));
        shopSelectionEnclosedPanel.setPreferredSize(new java.awt.Dimension(550, 200));

        openShopButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        openShopButton.setText("Open");
        openShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openShopButtonActionPerformed(evt);
            }
        });

        shopSelectCB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        shopSelectCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "The New Chitra Boutiques", "Chitra Boutiques" }));

        shopSelectErrorLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shopSelectErrorLabel.setForeground(new java.awt.Color(255, 0, 0));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("Back");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout shopSelectionEnclosedPanelLayout = new javax.swing.GroupLayout(shopSelectionEnclosedPanel);
        shopSelectionEnclosedPanel.setLayout(shopSelectionEnclosedPanelLayout);
        shopSelectionEnclosedPanelLayout.setHorizontalGroup(
            shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shopSelectionEnclosedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shopSelectionEnclosedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shopSelectCB, 0, 602, Short.MAX_VALUE)
                    .addComponent(shopSelectErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(shopSelectionEnclosedPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(openShopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
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
        addUserButtonACP.setText("Submit");
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
                .addGap(258, 258, 258)
                .addComponent(acpEnclosedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(419, Short.MAX_VALUE))
        );
        accountCreatePanelLayout.setVerticalGroup(
            accountCreatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountCreatePanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(acpEnclosedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
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

        optionPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        optionPanel.setPreferredSize(new java.awt.Dimension(900, 650));
        optionPanel.setLayout(new java.awt.GridBagLayout());

        productSellButton.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        productSellButton.setText("Shop Order");
        productSellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productSellButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 135;
        gridBagConstraints.ipady = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        optionPanel.add(productSellButton, gridBagConstraints);

        supplierButton.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        supplierButton.setText("Supplier");
        supplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.ipady = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
        optionPanel.add(supplierButton, gridBagConstraints);

        addNewStaffButton.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        addNewStaffButton.setText("New Staff");
        addNewStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStaffButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 169;
        gridBagConstraints.ipady = 93;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 38, 0);
        optionPanel.add(addNewStaffButton, gridBagConstraints);

        createNewShopButton.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        createNewShopButton.setText("New Shop");
        createNewShopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewShopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 163;
        gridBagConstraints.ipady = 93;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 38, 10);
        optionPanel.add(createNewShopButton, gridBagConstraints);

        reportButton.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        reportButton.setText("Report");
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 403;
        gridBagConstraints.ipady = 93;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 148, 0, 0);
        optionPanel.add(reportButton, gridBagConstraints);

        mainPanel.add(optionPanel, "card8");

        supplierDetailsPanel.setPreferredSize(new java.awt.Dimension(960, 650));
        supplierDetailsPanel.setLayout(new java.awt.CardLayout());

        supplyViewPanel.setPreferredSize(new java.awt.Dimension(950, 587));
        supplyViewPanel.setLayout(new java.awt.CardLayout());

        supplierTabPanned.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                supplierTabPannedStateChanged(evt);
            }
        });

        supplyOrderEntryPanel.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(950, 125));

        supplierCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supplierCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fashion Park", "Rahim Garments", "Harami Garments" }));
        supplierCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierCBActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton12.setText("Add New Supplier");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton14.setText("New Order");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton15.setText("Update Order");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("New Order Date");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("Total Bill:");

        supplierTotalBillLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supplierTotalBillLabel.setText("0.0");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Total Paid :");

        supplierTotalPaidLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supplierTotalPaidLabel.setText("0.0");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel48.setText("Total Due :");

        supplierTotalDueLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supplierTotalDueLabel.setText("0.0");

        jButton18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton18.setText("Cancel Order");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        orderProductSellButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        orderProductSellButton.setText("Product Sell Report");
        orderProductSellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderProductSellButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierTotalBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(supplierCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierTotalPaidLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(supplierOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderProductSellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(supplierTotalDueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(112, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(supplierCB)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(supplierOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(supplierTotalPaidLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(orderProductSellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierTotalBillLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(supplierTotalDueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        supplyOrderEntryPanel.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.CardLayout(5, 2));

        supplierTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Order ID", "Bill", "Paid", "Due"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(supplierTable);

        jPanel9.add(jScrollPane4, "card2");

        supplyOrderEntryPanel.add(jPanel9, java.awt.BorderLayout.CENTER);

        supplierTabPanned.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Supply Order Entry</body></html>", supplyOrderEntryPanel);

        supplyProductDistPanel.setPreferredSize(new java.awt.Dimension(950, 553));
        supplyProductDistPanel.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(930, 103));

        distSupplierNameCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fashion Park", "Fashion House" }));
        distSupplierNameCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distSupplierNameCBActionPerformed(evt);
            }
        });

        distProductNameCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FashionPark_O1_P1", "FashionPark_O1_P2", "FashionPark_O1_P3", "FashionPark_O1_P4", "FashionPark_O2_P1" }));
        distProductNameCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distProductNameCBActionPerformed(evt);
            }
        });

        distQuantityCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", " " }));
        distQuantityCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distQuantityCBActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Supplier Name");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Product Name");

        distShopCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "The New Chitra Boutiques", "The Fashion Ark" }));
        distShopCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distShopCBActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setText("Quantity");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("Shop Name");

        distSellRateField.setText("0");

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton16.setText("Distribute To Shop");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("Shop Sell Rate");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Buy Rate :");

        supplyBarcodeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supplyBarcodeLabel.setText("Generate Barcode");
        supplyBarcodeLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplyBarcodeLabelActionPerformed(evt);
            }
        });

        fixedRateCkB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fixedRateCkB.setText("Fixed Rate Label");
        fixedRateCkB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixedRateCkBActionPerformed(evt);
            }
        });

        supplierBuyRateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierBuyRateFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(distSupplierNameCB, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(212, 212, 212)
                        .addComponent(jLabel50))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel49)
                                .addComponent(distProductNameCB, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(supplyBarcodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fixedRateCkB, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(distQuantityCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel51)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(distShopCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierBuyRateField))
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(distSellRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(distProductNameCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(distQuantityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(distShopCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(distSellRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jLabel49))
                        .addGap(8, 8, 8)
                        .addComponent(distSupplierNameCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplyBarcodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fixedRateCkB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel47)
                        .addComponent(supplierBuyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        supplyProductDistPanel.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel11.setLayout(new java.awt.CardLayout(5, 2));

        productSuppliedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product Barcode", "Shop Name", "Buy Rate", "Sell Rate", "Shop Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(productSuppliedTable);

        jPanel11.add(jScrollPane5, "card2");

        supplyProductDistPanel.add(jPanel11, java.awt.BorderLayout.CENTER);

        supplierTabPanned.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=3>Supply Product Distribution</body></html>", supplyProductDistPanel);

        supplyViewPanel.add(supplierTabPanned, "card2");

        supplierDetailsPanel.add(supplyViewPanel, "card2");

        mainPanel.add(supplierDetailsPanel, "card9");

        annualReportPanel.setLayout(new java.awt.BorderLayout());

        reportToolBarPanel.setPreferredSize(new java.awt.Dimension(1062, 65));

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton11.setText("Back");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reportToolBarPanelLayout = new javax.swing.GroupLayout(reportToolBarPanel);
        reportToolBarPanel.setLayout(reportToolBarPanelLayout);
        reportToolBarPanelLayout.setHorizontalGroup(
            reportToolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportToolBarPanelLayout.createSequentialGroup()
                .addContainerGap(911, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        reportToolBarPanelLayout.setVerticalGroup(
            reportToolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportToolBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        annualReportPanel.add(reportToolBarPanel, java.awt.BorderLayout.PAGE_START);

        reportViewPanel.setLayout(new java.awt.CardLayout());

        reportPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(768, 90));

        jButton17.setText("Show");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        sellReportAllShopCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Shop", "The New Chitra Boutiques" }));
        sellReportAllShopCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellReportAllShopCBActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("From");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("To");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Select Report Pattern");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Total Sell");

        totalSellLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalSellLabel.setText("0.0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Total Paid");

        totalPaidLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalPaidLabel.setText("0.0");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Total Due");

        totalDueLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalDueLabel.setText("0.0");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Total Profit");

        totalProfitLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalProfitLabel.setText("0.0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sellReportAllShopCB, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(totalPaidLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalDueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sellDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sellDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalProfitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(totalSellLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(533, 533, 533)))
                .addContainerGap(208, Short.MAX_VALUE))
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
                    .addComponent(sellDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17)
                        .addComponent(sellReportAllShopCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sellDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(totalSellLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalPaidLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalDueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        reportPanel.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setLayout(new java.awt.CardLayout());

        sellReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Shop Name", "Date", "Daily Sell", "Daily Paid", "Daily Due", "Profit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        sellReportTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(sellReportTable);

        jPanel5.add(jScrollPane2, "card2");

        reportPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        reportTabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=2>Sell Report</body></html>", reportPanel);

        reportViewPanel.add(reportTabbedPane, "card2");

        annualReportPanel.add(reportViewPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(annualReportPanel, "card10");

        getContentPane().add(mainPanel, "card2");

        fileMenu.setText("File");

        logoutItem.setText("Logout");
        logoutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutItemActionPerformed(evt);
            }
        });
        fileMenu.add(logoutItem);

        optionItem.setText("Option");
        optionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionItemActionPerformed(evt);
            }
        });
        fileMenu.add(optionItem);

        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        backupDateItem.setText("Backup Data");
        backupDateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupDateItemActionPerformed(evt);
            }
        });
        editMenu.add(backupDateItem);

        menuBar.add(editMenu);

        helpMenu.setText("Help");

        supportItem.setText("Get Support");
        supportItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supportItemActionPerformed(evt);
            }
        });
        helpMenu.add(supportItem);

        developerItem.setText("Developer");
        developerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                developerItemActionPerformed(evt);
            }
        });
        helpMenu.add(developerItem);

        aboutItem.setText("About");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void developerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_developerItemActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "                              Biswajit Debnath\n            Computer Science and Engineering\nShahjalal University Of Science and Technology");

    }//GEN-LAST:event_developerItemActionPerformed

    private void openShopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openShopButtonActionPerformed
        // TODO add your handling code here:
        Reset.resetTable(expenseTable);
        userTabPanel.setSelectedIndex(Enum.firstIndex);
        if (user.getAuthority().equals(Role.USER_STAFF)) {
            managerTab.setEnabledAt(Enum.MANAGER_TAB_ORDER_VIEW, false);
        } else if (user.getAuthority().equals(Role.USER_MANAGER)) {
            managerTab.setEnabledAt(Enum.MANAGER_TAB_ORDER_VIEW, true);
        }

        resetTable.resetTable(orderViewTable);

        int index = shopSelectCB.getSelectedIndex();
        shop = index != -1 ? shops.get(index) : null;

        if (shop != null) {

            shopNameLabel.setText(shop.getShopName());
            shopAddressLabel.setText(shop.getShopAddress());
            softUserNameLabel.setText(user.getName());
            softUserMobileLabel.setText(user.getMobile());
            softUserRoleLabel.setText(user.getAuthority());
            panelSlider.changeThePanel(mainPanel, controllerPanel);
            /*Initialize the customer order*/
            initOrder();
            /*Expense Category Loading*/
            shopExpenses = shopExpenseService.getShopExpenses(shop);
            expenseAdder.addCategoriesInComboBox(shopExpenses, expCategoryCB);
            /*Initalize the shop products*/
            products = productService.getProducts(shop);
            System.out.println(products);

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

        if (Valid.vUsername(username) && Valid.vPassword(password)) {
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

                        supplierButton.setEnabled(false);
                        reportButton.setEnabled(false);
                        addNewStaffButton.setEnabled(false);
                        createNewShopButton.setEnabled(false);
                    } else {
                        supplierButton.setEnabled(true);
                        reportButton.setEnabled(true);
                        addNewStaffButton.setEnabled(true);
                        createNewShopButton.setEnabled(true);
                    }

                    panelSlider.changeThePanel(mainPanel, optionPanel);
                }
                optionItem.setEnabled(true);
                logoutItem.setEnabled(true);
                backupDateItem.setEnabled(true);
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

    private void logoutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutItemActionPerformed
        // TODO add your handling code here:
        usernameField.setText("");
        passwordField.setText("");

        panelSlider.changeThePanel(mainPanel, loginPanel);
        logoutItem.setEnabled(false);
        optionItem.setEnabled(false);

        /*Reset Item*/
        logoutReset();
    }//GEN-LAST:event_logoutItemActionPerformed

    private void logoutReset() {
        resetTable.resetTable(stockTable);
        resetTable.resetTable(expenseTable);
        resetTable.resetTable(orderViewTable);
        resetTable.resetTable(completeOrderTable);
        resetTable.resetTable(sellReportTable);
        shops = null;
        users = null;
        shopBalances = null;
        products = null;
        shopExpenses = null;
        shopOrders = null;
        user = null;
        shop = null;
        shopOrder = null;
        backupDateItem.setEnabled(false);
    }

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
        panelSlider.changeThePanel(mainPanel, optionPanel);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void addUserButtonACPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonACPActionPerformed
        // TODO add your handling code here:
        User user = new User(userFieldACP.getText(),
                passField1ACP.getText(),
                nameFieldACP.getText().toUpperCase(),
                mobileFieldACP.getText(),
                (String) authorityComboACP.getItemAt(authorityComboACP.getSelectedIndex()),
                true);

        if (!Valid.vName(user.getName())
                || !Valid.vMobile(user.getMobile())
                || !Valid.vPassword(user.getPassword())
                || !Valid.vUsername(user.getUsername())) {

            userCreationErrorLabel.setForeground(Color.red);
            userCreationErrorLabel.setText(Message.ERROR_INVALID_INPUT);
        } else {
            if (passField1ACP.getText().equals(passField2ACP.getText())) {
                System.out.println(user);
                if (userService.getUser(user.getUsername()) == null && userService.getUser(user.getMobile()) == null) {
                    boolean isUserSaved = userService.createUser(user);

                    if (isUserSaved) {
                        userCreationErrorLabel.setForeground(Color.green);
                        userCreationErrorLabel.setText(Message.SUCCESS_USER_CREATED);
                        userFieldACP.setText("");
                        passField1ACP.setText("");
                        passField2ACP.setText("");
                        nameFieldACP.setText("");
                        mobileFieldACP.setText("");
                    } else {
                        userCreationErrorLabel.setForeground(Color.red);
                        userCreationErrorLabel.setText(Message.ERROR_SAME_USER_OR_CONNECTION_ERROR);
                    }
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
        panelSlider.changeThePanel(mainPanel, optionPanel);
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
        shopOrder.getCustomerOrder().setBuyerMobile(customerMobileField.getText());
        shopOrder.getCustomerOrder().setBuyerName(customerNameField.getText());
        System.out.println(shopOrder);

        if (shopOrder.getOrderProducts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add minimum item(s)");
        } else {
            int orderComplete = JOptionPane.showConfirmDialog(this, "Confirm Order?");
            if (JOptionPane.YES_OPTION == orderComplete) {
                if (shopOrder.getCustomerOrder().getTotalDue() != 0) {
                    int due = JOptionPane.showConfirmDialog(this, "Keep Due?");
                    if (due == JOptionPane.NO_OPTION) {
                        shopOrder.getCustomerOrder().setTotalPaid(
                                shopOrder.getCustomerOrder().getTotalAmount()
                        );
                        if (shopOrder.getCustomerOrder().getTotalDue() < 0) {
                            JOptionPane.showMessageDialog(this, "Cash Back Amount : " + -1 * shopOrder.getCustomerOrder().getTotalDue());
                        } else {
                            JOptionPane.showMessageDialog(this, "Get Money : " + shopOrder.getCustomerOrder().getTotalDue());
                        }
                        shopOrder.getCustomerOrder().setTotalDue(0.0);
                    }
                }
                OrderFileBuilder orderFileBuilder = new OrderFileBuilder(shop);
                String pdfFileName = orderFileBuilder.makePdf(shopOrder);
                String inputData = reciptIndent.getIndentedOrder(shopOrder);

                System.out.println("Shop Order: " + shopOrder);

                customerOrderService.completeShopOrder(shopOrder);
                products = productService.getProducts(shop);

                String[] printerNames = printerLookUp.getPrinterNames();
                String printerName = (String) JOptionPane.showInputDialog(this,
                        "Which Printer?",
                        "Printer Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null, printerNames, null);

                System.out.println(printerName);

                PrintService ps = printerLookUp.getPrintService(printerName);
                if (ps != null) {
                    System.out.println("Printing Command Sends!");
                    Printer printer = new Printer();
                    System.out.println(ps);
                    printer.doPrintRawData(ps, inputData);
                }
                initOrder();
            }
        }

    }//GEN-LAST:event_orderCompleteButtonActionPerformed

    private void shopAddressFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopAddressFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shopAddressFieldActionPerformed

    private void userTabPanelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_userTabPanelStateChanged
        // TODO add your handling code here:
        jButton9.setEnabled(false);
        jButton10.setEnabled(false);
        

        JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
        int index = tabbedPane.getSelectedIndex();
        System.out.println("User Tab: " + index);
        if (index == Enum.USER_TAB_MANAGER) {
            updateStockView();
            updateOrderView();
//            updateSellReportView();

        }
    }//GEN-LAST:event_userTabPanelStateChanged

    private void orderViewTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_orderViewTablePropertyChange
        // TODO add your handling code here:
        int rowProductIndex = orderViewTable.getSelectedRow();
        System.out.println("Order Row:" + rowProductIndex);

        if (rowProductIndex != Enum.invalidIndex) {

            OrderProduct orderProduct = shopOrder.getOrderProducts().get(rowProductIndex);
            Integer newQuantity = (Integer) orderViewTable.getValueAt(rowProductIndex, 3);
            Double newSellRate = (Double) orderViewTable.getValueAt(rowProductIndex, 4);
            Double newDiscount = (Double) orderViewTable.getValueAt(rowProductIndex, 5);
            Double newVAT = (Double) orderViewTable.getValueAt(rowProductIndex, 6);

            boolean isDataUpdated = false;
            if (newQuantity == null) {
                newQuantity = 0;
            }
            if (newSellRate == null) {
                newSellRate = 0.0;
            }
            if (newVAT == null) {
                newVAT = 0.0;
            }
            if (newDiscount == null) {
                newDiscount = 0.0;
            }

            if (orderProduct.getOrderProductQuantity() != newQuantity) {

                if (orderProduct.getProductStock() > newQuantity) {
                    orderProduct.setOrderProductQuantity(newQuantity);

                } else {
                    JOptionPane.showMessageDialog(this, "Only " + orderProduct.getProductStock() + " unit(s) available! Please update stock");
                }
                isDataUpdated = true;
            }

            if (orderProduct.getOrderProductSellRate() != newSellRate) {
                orderProduct.setOrderProductSellRate(newSellRate);
                isDataUpdated = true;
            }

            if (orderProduct.getOrderProductVat() != newVAT) {
                orderProduct.setOrderProductVat(newVAT);
                isDataUpdated = true;
            }

            if (orderProduct.getOrderProductDiscount() != newDiscount) {
                orderProduct.setOrderProductDiscount(newDiscount);
                isDataUpdated = true;
            }

            if (isDataUpdated) {
                Double productTotalBill = orderProductAdder.updateOrderView(orderViewTable, shopOrder.getOrderProducts());
                totalBillViewLabel.setText(productTotalBill.toString());
                orderProductAdder.updateCash(totalBillViewLabel, cashField, cashBackViewLabel, shopOrder);
            }

        }
    }//GEN-LAST:event_orderViewTablePropertyChange

    private void cashFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cashFieldPropertyChange
        // TODO add your handling code here:
        System.out.println("Key Field Property Changed!");

    }//GEN-LAST:event_cashFieldPropertyChange

    private void cashFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashFieldKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_cashFieldKeyPressed

    private void cashFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashFieldKeyReleased
        // TODO add your handling code here:
        orderProductAdder.updateCash(totalBillViewLabel, cashField, cashBackViewLabel, shopOrder);
    }//GEN-LAST:event_cashFieldKeyReleased

    private void removeOtItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOtItemButtonActionPerformed
        // TODO add your handling code here:
        int rowIndex = orderViewTable.getSelectedRow();
        System.out.println("Order Item Index: " + rowIndex);
        if (rowIndex != Enum.invalidIndex) {
            shopOrder.getOrderProducts().remove(rowIndex);
            resetTable.resetTable(orderViewTable);
            orderProductAdder.updateOrderView(orderViewTable, shopOrder.getOrderProducts());

        }
    }//GEN-LAST:event_removeOtItemButtonActionPerformed

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
            resetTable.resetTable(orderViewTable);
            totalBillViewLabel.setText("");
            cashBackViewLabel.setText("");
            cashField.setText("");

        }
    }//GEN-LAST:event_newOrderCreateButtonActionPerformed

    private void orderPProductBarcodeFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldKeyPressed
        // TODO add your handling code here:
        String productBarcode = orderPProductBarcodeField.getText();
        Double vat = 0.0;
        Double discount = 0.0;

        try {
            vat = Double.parseDouble(vatField.getText());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }

        try {
            discount = Double.parseDouble(discountField.getText());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }

        //        System.out.println("Product Barcode: " + productBarcode);
        if (productBarcode != null && productBarcode.length() == 13) {

            orderProductAdder.addProductInOrderView(products,
                    shopOrder,
                    productBarcode,
                    discount,
                    vat,
                    defaultSellRateChoose.isSelected(),
                    orderViewTable,
                    productInfoLabel,
                    totalBillViewLabel,
                    cashField,
                    cashBackViewLabel
            );

            System.out.println(productBarcode);
            productInfoLabel.setForeground(Color.RED);
            orderPProductBarcodeField.setText("");
        }
    }//GEN-LAST:event_orderPProductBarcodeFieldKeyPressed

    private void orderPProductBarcodeFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldPropertyChange

    }//GEN-LAST:event_orderPProductBarcodeFieldPropertyChange

    private void orderPProductBarcodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderPProductBarcodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderPProductBarcodeFieldActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitItemActionPerformed

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed

    }//GEN-LAST:event_aboutItemActionPerformed

    private void supportItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supportItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Please Contact \n Email: biswajit.sust@gmail.com");
    }//GEN-LAST:event_supportItemActionPerformed

    private void managerTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_managerTabStateChanged
//         TODO add your handling code here:


    }//GEN-LAST:event_managerTabStateChanged

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        updateSellReportView();

    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String orderCode = OrderIDCodeField.getText();
        int orderSelected = Enum.invalidIndex;

        if (orderCode != null && orderCode.length() == 13) {
            orderSelected = search.searchCustomerOrderFromShopOrder(shopOrders, orderCode);
        } else {
            orderSelected = completeOrderTable.getSelectedRow();
        }

        if (orderSelected != Enum.invalidIndex) {
            ShopOrder cancelShopOrder = shopOrders.get(orderSelected);
            int orderDeleteConfirm = JOptionPane.showConfirmDialog(this, "Order ID: " + cancelShopOrder.getCustomerOrder().getOrderBarcode() + " , Total Amount : " + cancelShopOrder.getCustomerOrder().getTotalPaid() + " \n Sure to cancel Order?");
            if (orderDeleteConfirm == JOptionPane.YES_OPTION) {
                System.out.println(cancelShopOrder);
                customerOrderService.cancelShopOrder(cancelShopOrder);
                shopOrders = customerOrderService.getShopOrders(shop, user);
                products = productService.getProducts(shop);
                updateStockView();
                updateOrderView();

            }

        } else {
            JOptionPane.showMessageDialog(this, "No Order Found With Code ID: " + orderCode);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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

    private void expCategoryCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expCategoryCBActionPerformed
        // TODO add your handling code here:
        int index = expCategoryCB.getSelectedIndex();
        if (index != Enum.invalidIndex) {
            System.out.println("Selected Category:" + index);
            List<Expenditure> expenditures = shopExpenses.get(index).getExpenditures();
            Double totalExpense = expenseAdder.getTotalExpense(expenditures);
            categoryExpenseTotalLabel.setText(totalExpense.toString());
            expenseAdder.addExpendituresInTable(expenditures, expenseTable);
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
                Double totalExpense = expenseAdder.getTotalExpense(expenditures);
                categoryExpenseTotalLabel.setText(totalExpense.toString());
                expenseAdder.addExpendituresInTable(expenditures, expenseTable);
            } else {
                System.out.println("Expense coundn't deleted!");
            }
        }
    }//GEN-LAST:event_expenditureDeleteButtonActionPerformed

    private void saveExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExpenseButtonActionPerformed
        // TODO add your handling code here:
        int categoryIndex = expCategoryCB.getSelectedIndex();
        ExpenseCategory expCategory = shopExpenses.get(categoryIndex).getExpenseCategory();
        Date date = expDate.getDate();
        Timestamp timestamp;
        if (date != null) {
            timestamp = DateUtil.convertDateToTimestamp(date);
        } else {
            timestamp = DateUtil.convertDateToTimestamp(new Date());
        }
        try {
            Expenditure expenditure = new Expenditure(expCategory.getExpCategoryId(), Double.parseDouble(expAmountField.getText()), timestamp, expDescField.getText().toUpperCase());
            System.out.println(expenditure);
            boolean status = shopExpenseService.saveExpenditure(expenditure);
            if (status) {
                List<Expenditure> expenditures = shopExpenseService.getExpenditures(expCategory);
                shopExpenses.get(categoryIndex).setExpenditures(expenditures);
                Double totalExpense = expenseAdder.getTotalExpense(expenditures);
                categoryExpenseTotalLabel.setText(totalExpense.toString());
                expenseAdder.addExpendituresInTable(expenditures, expenseTable);
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }

        expAmountField.setText("");
        expDescField.setText("");
    }//GEN-LAST:event_saveExpenseButtonActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        int stockTableSelectedRow = stockTable.getSelectedRow();
        if (stockTableSelectedRow != -1) {
            Product product = products.get(stockTableSelectedRow);
            boolean isDeleted = productService.deleteProductById(product.getProductId());
            if (isDeleted) {
                products.remove(stockTableSelectedRow);
                productAdder.addProductsInStockView(products, stockTable);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try {
            Product product = new Product(shop.getShopId(),
                    psCodeField.getText(),
                    psNameField.getText().toUpperCase(),
                    Double.parseDouble(psBuyRateField.getText()),
                    Double.parseDouble(psSellRateField.getText()),
                    Integer.parseInt(psQuantityField.getText()));

            System.out.println(product);
            Product demoProduct = productService.getProduct(shop, product.getProductBarcode());

            System.out.println(product);

            if (demoProduct == null) {
                boolean status = productService.createProduct(product);
                if (status == false) {
                    JOptionPane.showMessageDialog(selectionPanel, Message.ERROR_DUPLICATE_BARCODE, null, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                productService.updateProduct(product);
            }

            products = productService.getProducts(shop);
            productAdder.addProductsInStockView(products, stockTable);

            psCodeField.setText("");
            psNameField.setText("");
            psQuantityField.setText("");
            psBuyRateField.setText("");
            psSellRateField.setText("");

        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void sellReportAllShopCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellReportAllShopCBActionPerformed
        // TODO add your handling code here:
        updateSellReportView();

    }//GEN-LAST:event_sellReportAllShopCBActionPerformed

    private void completeOrderTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_completeOrderTablePropertyChange
        // TODO add your handling code here:
        int selectedRow = completeOrderTable.getSelectedRow();

        if (selectedRow != Enum.invalidIndex) {
            CustomerOrder customerOrder = shopOrders.get(selectedRow).getCustomerOrder();
            Double paidAmount = (Double) completeOrderTable.getValueAt(selectedRow, 5);

            if (paidAmount != customerOrder.getTotalPaid()) {
                int updateConfirm = JOptionPane.showConfirmDialog(this, "Order Id: " + customerOrder.getOrderBarcode() + " Paid Amount: " + paidAmount + " Update Confirm?");
                if (updateConfirm == JOptionPane.YES_OPTION) {
                    CustomerOrder dummyOrder = CustomerOrder.copy(customerOrder);
                    dummyOrder.setTotalPaid(paidAmount);
                    dummyOrder.setTotalDue(
                            dummyOrder.getTotalAmount() - dummyOrder.getTotalPaid()
                    );

                    System.out.println(dummyOrder);

                    boolean isUpdated = customerOrderService.updateCustomerOrder(dummyOrder);
//
                    if (isUpdated == true) {
                        customerOrder.setTotalDue(dummyOrder.getTotalDue());
                        customerOrder.setTotalPaid(dummyOrder.getTotalPaid());
                        shopOrderAdder.addCustomerOrderInOrderView(shopOrders, completeOrderTable);
                    } else {
                        shopOrderAdder.addCustomerOrderInOrderView(shopOrders, completeOrderTable);
                    }

                } else {
                    shopOrderAdder.addCustomerOrderInOrderView(shopOrders, completeOrderTable);
                }
            }

        }
    }//GEN-LAST:event_completeOrderTablePropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String orderCode = OrderIDCodeField.getText();
        int shopOrderIndex = -1;
        ShopOrder shopOrder = null;
        if (orderCode != null && orderCode.length() == 13) {
            shopOrderIndex = search.searchCustomerOrderFromShopOrder(shopOrders, orderCode);

        } else {
            shopOrderIndex = completeOrderTable.getSelectedRow();
        }

        if (shopOrderIndex != Enum.invalidIndex) {
            shopOrder = shopOrders.get(shopOrderIndex);
            System.out.println(shopOrder);
            String orderReceipt = reciptIndent.getIndentedOrder(shopOrder);
            System.out.println(orderReceipt);

            String[] printerNames = printerLookUp.getPrinterNames();

            String printerName = (String) JOptionPane.showInputDialog(this,
                    "Which Printer?",
                    "Printer Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null, printerNames, null);

            System.out.println(printerName);

            PrintService ps = printerLookUp.getPrintService(printerName);

            if (ps != null) {
                System.out.println("Printing Command Sends!");
                Printer printer = new Printer();
                System.out.println(ps);
                printer.doPrintRawData(ps, orderReceipt);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Order Record Found with this code : " + orderCode);
            OrderIDCodeField.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void stockTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_stockTablePropertyChange
        // TODO add your handling code here:
        /**
         * Product product = new Product();
         * product.setProductBarcode(idBuilder.geProductUniqueID());
         * product.setProductBuyRate(buyRate);
         * product.setProductSellRate(sellRate);
         * product.setProductStock(quantity);
         * product.setProductName(sop.getSupplierProductName());
         * product.setShopId(shop.getShopId());
         * product.setProductInfoUpdated(DateUtil.convertDateToTimestamp(new
         * Date())); product.setSupplierProductId(sop.getSupplierProductId());
         */
        int productIndex = stockTable.getSelectedRow();
        if (productIndex != Enum.invalidIndex) {

            Product product = products.get(productIndex);
            Product dummyProduct = new Product();

            dummyProduct.setProductId(product.getProductId());
            dummyProduct.setProductBarcode(product.getProductBarcode());
            dummyProduct.setSupplierProductId(product.getSupplierProductId());
            dummyProduct.setShopId(shop.getShopId());
            dummyProduct.setShop(shop);
            dummyProduct.setProductName(((String) stockTable.getValueAt(productIndex, 1)).toUpperCase());
            dummyProduct.setProductStock((int) stockTable.getValueAt(productIndex, 2));
            dummyProduct.setProductBuyRate((double) stockTable.getValueAt(productIndex, 3));
            dummyProduct.setProductSellRate((double) stockTable.getValueAt(productIndex, 4));

            System.out.println("Dummy Product: " + dummyProduct);

            boolean productUpdated = false;

            if (!dummyProduct.getProductName().equals(product.getProductName())) {
                productUpdated = true;
            }

            if (dummyProduct.getProductBuyRate() != product.getProductBuyRate()) {
                productUpdated = true;
            }

            if (dummyProduct.getProductStock() != product.getProductStock()) {
                productUpdated = true;
            }

            if (dummyProduct.getProductSellRate() != product.getProductSellRate()) {
                productUpdated = true;
            }

            if (productUpdated == true) {

                System.out.println("Product: " + dummyProduct);
                int option = JOptionPane.showConfirmDialog(this,
                        "Product Code: " + dummyProduct.getProductBarcode()
                        + "\nProduct Name: " + dummyProduct.getProductName()
                        + "\nQuantity: " + dummyProduct.getProductStock()
                        + "\nBuy Rate: " + dummyProduct.getProductBuyRate()
                        + "\nSell Rate: " + dummyProduct.getProductSellRate(),
                        "Product Update Confirmation", JOptionPane.INFORMATION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    boolean status = productService.updateProduct(dummyProduct);
                    if (status == true) {
                        products = productService.getProducts(shop);
                    }
                }
                updateStockView();
            }

        }
    }//GEN-LAST:event_stockTablePropertyChange

    private void showProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProductButtonActionPerformed
        // TODO add your handling code here:
        int productIndex = Enum.invalidIndex;
        String productCode = psCodeField.getText();
        int selected = stockTable.getSelectedRow();
        String rowCode = "";

        if (selected != Enum.invalidIndex) {
            rowCode = (String) stockTable.getValueAt(selected, 0);
        }

        if (productCode != null && productCode.length() == 13 && productCode.charAt(0) == '1') {
            productIndex = search.searchProduct(products, productCode);
        } else {
            if (!rowCode.equals("")) {
                productIndex = search.searchProduct(products, rowCode);
            }
        }

        if (productIndex != Enum.invalidIndex) {
            Product p = products.get(productIndex);
            JOptionPane.showMessageDialog(this,
                    "Product Code: " + p.getProductBarcode()
                    + "\nProduct Name: " + p.getProductName()
                    + "\nProduct Stock: " + p.getProductStock()
                    + "\nProduct Buy Rate: " + p.getProductBuyRate()
                    + "\nProduct Sell Rate: " + p.getProductSellRate(), "Product Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No Product Found With Product Code: " + productCode, "Product Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_showProductButtonActionPerformed

    private void backupDateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupDateItemActionPerformed
        // TODO add your handling code here:
        Backup backup = new Backup();
        String fileName = backup.backUpData();

        if (fileName == null) {
            JOptionPane.showMessageDialog(selectionPanel, "DB_SERVER_PATH: VARIABLE MAY NOT SET!");
        } else {
            JOptionPane.showMessageDialog(selectionPanel, "DB BACKUP CREATED!");
        }

    }//GEN-LAST:event_backupDateItemActionPerformed

    private void productSellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productSellButtonActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, selectionPanel);
    }//GEN-LAST:event_productSellButtonActionPerformed

    private void optionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionItemActionPerformed
        // TODO add your handling code here:

        panelSlider.changeThePanel(mainPanel, optionPanel);
    }//GEN-LAST:event_optionItemActionPerformed

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        // TODO add your handling code here:
        sellReportAdder.refreshSellReportShops(sellReportAllShopCB, shops);
        updateSellReportView();
        panelSlider.changeThePanel(mainPanel, annualReportPanel);
    }//GEN-LAST:event_reportButtonActionPerformed

    private void supplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierButtonActionPerformed
        // TODO add your handling code here:
        Reset.resetTable(supplierTable);
        Reset.resetTable(productSuppliedTable);
        userTabPanel.setSelectedIndex(Enum.firstIndex);
        this.suppliers = supplierService.getSuppliers(user);

        supplierAdder.refreshSupplierLists(supplierCB, suppliers);
        if (suppliers != null && suppliers.size() > 0) {
            Supplier supplier = suppliers.get(0);
            supplierAdder.refreshSupplierTable(supplier,
                    supplierTable,
                    supplierTotalBillLabel,
                    supplierTotalPaidLabel,
                    supplierTotalDueLabel);
        }

//        supplierAdder.refreshSupplierLists(distSupplierNameCB, suppliers);
        supplierOrderDate.setDate(new Date());
        panelSlider.changeThePanel(mainPanel, supplierDetailsPanel);
    }//GEN-LAST:event_supplierButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, optionPanel);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String orderCode = OrderIDCodeField.getText();
        int shopOrderIndex = -1;
        ShopOrder shopOrder = null;
        if (orderCode != null && orderCode.length() == 13) {
            shopOrderIndex = search.searchCustomerOrderFromShopOrder(shopOrders, orderCode);

        } else {
            shopOrderIndex = completeOrderTable.getSelectedRow();
        }

        if (shopOrderIndex != Enum.invalidIndex) {
            shopOrder = shopOrders.get(shopOrderIndex);
            String viewString = reciptIndent.getIndentedOrder(shopOrder);
            System.out.println(viewString);
            JOptionPane.showMessageDialog(this, viewString, "Order Details", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "No Order Record Found with this code : " + orderCode);
            OrderIDCodeField.setText("");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        panelSlider.changeThePanel(mainPanel, optionPanel);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void openSupplierOrder(Supplier supplier, SupplierOrder supplierOrder) {
        SupplierOrderFormPanel panel = new SupplierOrderFormPanel(supplierOrder,
                supplierOrderService,
                supplierProductService,
                supplierOrderPaymentService);
        JDialog dialogView = new JDialog();
        dialogView.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialogView.setContentPane(panel);
        dialogView.pack();
        dialogView.setVisible(true);
    }

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:

        int supplierIndex = supplierCB.getSelectedIndex();
        if (supplierIndex != Enum.invalidIndex) {
            Supplier supplier = suppliers.get(supplierIndex);
            Timestamp timestamp = DateUtil.convertDateToTimestamp(supplierOrderDate.getDate());
            SupplierOrder supplierOrder = new SupplierOrder(supplier, timestamp);
            int supplierOrderId = supplierOrderService.createSupplierOrder(supplierOrder);
            if (supplierOrderId != Enum.invalidIndex) {
                supplierOrder.setSupplierOrderId(supplierOrderId);
                openSupplierOrder(supplier, supplierOrder);
                supplier.getSupplierOrders().add(supplierOrder);
                supplierAdder.refreshSupplierTable(supplier, supplierTable, supplierTotalBillLabel, supplierTotalPaidLabel, supplierTotalDueLabel);
            } else {
                JOptionPane.showMessageDialog(null, "New Order Couldn't Created!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please, select a supplier!");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        CreateSupplierPanel panel = new CreateSupplierPanel(user, suppliers, supplierService);
        JDialog dialogView = new JDialog();
        dialogView.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialogView.setContentPane(panel);
        dialogView.pack();
        dialogView.setVisible(true);

        supplierAdder.refreshSupplierLists(supplierCB, suppliers);

    }//GEN-LAST:event_jButton12ActionPerformed

    private void supplierCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierCBActionPerformed
        // TODO add your handling code here:
        int supplierSelected = supplierCB.getSelectedIndex();
        if (supplierSelected != Enum.invalidIndex) {
            Supplier supplier = suppliers.get(supplierSelected);
            supplierAdder.refreshSupplierTable(supplier, supplierTable, supplierTotalBillLabel, supplierTotalPaidLabel, supplierTotalDueLabel);
        }
    }//GEN-LAST:event_supplierCBActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        int supplierIndex = supplierCB.getSelectedIndex();
        if (supplierIndex != Enum.invalidIndex) {
            Supplier supplier = suppliers.get(supplierIndex);
            int supplierOrderIndex = supplierTable.getSelectedRow();
            if (supplierOrderIndex != Enum.invalidIndex) {
                SupplierOrder supplierOrder = supplier.getSupplierOrders().get(supplierOrderIndex);
                openSupplierOrder(supplier, supplierOrder);
                supplierAdder.refreshSupplierTable(supplier, supplierTable, supplierTotalBillLabel, supplierTotalPaidLabel, supplierTotalDueLabel);
            } else {
                System.out.println("Invalid Supplier Order!");
            }
        } else {
            System.out.println("Invalid Supplier!");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void supplierTabPannedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_supplierTabPannedStateChanged
        // TODO add your handling code here:
        JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
        int index = tabbedPane.getSelectedIndex();

        Reset.resetComboBox(distProductNameCB);
        Reset.resetComboBox(distSupplierNameCB);
        Reset.resetComboBox(distQuantityCB);

        if (index == Enum.SUPPLIER_TAB_PRODUCT_DISTRIBUTION_VIEW) {
//            System.out.println("second tab selected!");
            supplierAdder.refreshSupplierLists(distSupplierNameCB, suppliers);

            if (!suppliers.isEmpty()) {
                Supplier supplier = suppliers.get(0);
                sopIndexs = supplierAdder.refreshSupplierProductList(distProductNameCB, supplier, 0);
                if (!supplier.getSupplierOrders().isEmpty()) {
                    SupplierOrder so = supplier.getSupplierOrders().get(0);
                    if (!so.getSupplierProducts().isEmpty()) {
                        SupplierOrderProduct sop = so.getSupplierProducts().get(0);

                        supplierBuyRateField.setText(String.format("%.2f", sop.getSupplierRate()));
                        int quantity = currentSupplierOrderProductQuantity(sop);
                        supplierAdder.refreshQuantityList(distQuantityCB, quantity);
                    }
                }
            }
            supplierAdder.refreshShopsList(distShopCB, shops);

        }
    }//GEN-LAST:event_supplierTabPannedStateChanged

    private void expDescFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expDescFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expDescFieldActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        int supplierIndex = supplierCB.getSelectedIndex();
        if (supplierIndex != Enum.invalidIndex) {
            Supplier supplier = suppliers.get(supplierIndex);
            int orderIndex = supplierTable.getSelectedRow();
            if (orderIndex != Enum.invalidIndex) {
                SupplierOrder so = supplier.getSupplierOrders().get(orderIndex);

                int option = JOptionPane.showConfirmDialog(null, "Are you sure to cancel the order?");

                if (option == JOptionPane.YES_OPTION) {
                    boolean orderDeleted = supplierService.deleteSupplierOrder(so);
                    if (orderDeleted == true) {
                        supplier.getSupplierOrders().remove(orderIndex);
                        supplierAdder.refreshSupplierTable(supplier, supplierTable, supplierTotalBillLabel, supplierTotalPaidLabel, supplierTotalDueLabel);
                    }
                }
            }
        }

    }//GEN-LAST:event_jButton18ActionPerformed

    private void supplyBarcodeLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplyBarcodeLabelActionPerformed
        // TODO add your handling code here:
        int rowIndex = productSuppliedTable.getSelectedRow();
        boolean fixedRateTrue = fixedRateCkB.isSelected();
        if (rowIndex != Enum.invalidIndex) {

            Product product = shopDistributedProducts.get(rowIndex);
//            System.out.println("Fixed Rate: " + fixedRateTrue);
//            System.out.println(product);
            CodePDF codePDF = new CodePDF(fixedRateTrue, product);

            try {

                boolean status = codePDF.genCodeVer2Pdf();
                if (status == true) {
                    JOptionPane.showMessageDialog(null, "Barcode PDF generated in software directory");
                } else {
                    JOptionPane.showMessageDialog(null, "Same file named already in used!");
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PosScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_supplyBarcodeLabelActionPerformed

    List<Product> shopDistributedProducts;

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        int indx = distProductNameCB.getSelectedIndex();
        Double sellRate = 0.0;
        Double buyRate = 0.0;

        if (indx != Enum.invalidIndex) {
            SOPIndex sopIndex = sopIndexs.get(indx);
            SupplierOrderProduct sop = search.searchSupplierOrderProduct(suppliers, sopIndex);
            try {
                sellRate = Double.parseDouble(distSellRateField.getText());
                buyRate = Double.parseDouble(supplierBuyRateField.getText());
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getMessage());

            }

            if (sellRate < sop.getSupplierRate()) {
                JOptionPane.showMessageDialog(null, "Please set the sell rate");
            } else {

                int shopIndex = distShopCB.getSelectedIndex();
                Shop shop = shops.get(shopIndex);

                int quantity = Integer.parseInt(distQuantityCB.getSelectedItem().toString());
                if (quantity > 0) {

                    Product prevProduct = productService.previousProductQuantityInShop(sop.getSupplierProductId(), shop.getShopId());
                    System.out.println("Previous Quantity:" + prevProduct);

                    if (prevProduct == null) {
                        Product product = new Product();
                        product.setProductBarcode(idBuilder.geProductUniqueID());
                        product.setProductBuyRate(buyRate);
                        product.setProductSellRate(sellRate);
                        product.setProductStock(quantity);
                        product.setProductName(sop.getSupplierProductName());
                        product.setShopId(shop.getShopId());
                        product.setProductInfoUpdated(DateUtil.convertDateToTimestamp(new Date()));
                        product.setSupplierProductId(sop.getSupplierProductId());
                        System.out.println(product);

                        int savedId = productService.saveProduct(product);
                        if (savedId != Enum.invalidIndex) {
                            quantity = currentSupplierOrderProductQuantity(sop);
                            supplierAdder.refreshQuantityList(distQuantityCB, quantity);

                            shopDistributedProducts = productService.getDistributedProducts(sop.getSupplierProductId());
                            productAdder.addDistributedProductsInStockView(shopDistributedProducts, productSuppliedTable);

                            System.out.println("Product Distributed!");
                        }

                    } else {
                        int newStock = quantity + prevProduct.getProductStock();
                        prevProduct.setProductStock(newStock);
                        boolean status = productService.updateProduct(prevProduct);
                        if (status) {
                            quantity = currentSupplierOrderProductQuantity(sop);
                            supplierAdder.refreshQuantityList(distQuantityCB, quantity);

                            shopDistributedProducts = productService.getDistributedProducts(sop.getSupplierProductId());
                            productAdder.addDistributedProductsInStockView(shopDistributedProducts, productSuppliedTable);

                            System.out.println("Product Updated!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select minimum stock!");
                }
            }
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void distShopCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distShopCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_distShopCBActionPerformed

    private void distQuantityCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distQuantityCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_distQuantityCBActionPerformed

    private void distProductNameCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distProductNameCBActionPerformed
        // TODO add your handling code here:
        int productIndex = distProductNameCB.getSelectedIndex();
        if (productIndex != Enum.invalidIndex) {
            if (sopIndexs != null) {
                SOPIndex sopIndex = sopIndexs.get(productIndex);
                SupplierOrderProduct sop = search.searchSupplierOrderProduct(suppliers, sopIndex);

                supplierBuyRateField.setText(String.format("%.2f", sop.getSupplierRate()));
                distSellRateField.setText(String.format("%.2f", sop.getSupplierRate()));
                int soldQuantity = customerOrderService.countSupplierProductSoldQuantity(sop.getSupplierProductId());
                int distributedQuantity = productService.countDistributedProduct(sop.getSupplierProductId());
                supplierAdder.refreshQuantityList(distQuantityCB, sop.getSupplierProductQuantity() - soldQuantity - distributedQuantity);

                shopDistributedProducts = productService.getDistributedProducts(sop.getSupplierProductId());
                productAdder.addDistributedProductsInStockView(shopDistributedProducts, productSuppliedTable);
            }
        }
    }//GEN-LAST:event_distProductNameCBActionPerformed

    private void distSupplierNameCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distSupplierNameCBActionPerformed
        // TODO add your handling code here:

        int supplierIndex = distSupplierNameCB.getSelectedIndex();
        if (supplierIndex != Enum.invalidIndex) {
            Supplier supplier = suppliers.get(supplierIndex);
            sopIndexs = supplierAdder.refreshSupplierProductList(distProductNameCB, supplier, supplierIndex);
            if (!supplier.getSupplierOrders().isEmpty()) {
                SupplierOrder so = supplier.getSupplierOrders().get(0);
                if (!so.getSupplierProducts().isEmpty()) {
                    SupplierOrderProduct sop = so.getSupplierProducts().get(0);
                    supplierBuyRateField.setText(String.format("%.2f", sop.getSupplierRate()));
                    supplierAdder.refreshQuantityList(distQuantityCB, sop.getSupplierProductQuantity());
                }
            }
        }
    }//GEN-LAST:event_distSupplierNameCBActionPerformed

    private void fixedRateCkBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixedRateCkBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fixedRateCkBActionPerformed

    private void supplierBuyRateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierBuyRateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supplierBuyRateFieldActionPerformed

    private void orderProductSellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderProductSellButtonActionPerformed
        // TODO add your handling code here:
        int supplierIndex = supplierCB.getSelectedIndex();
        if (supplierIndex != Enum.invalidIndex) {
            int orderRowIndex = supplierTable.getSelectedRow();
            if (orderRowIndex != Enum.invalidIndex) {
                Supplier supplier = suppliers.get(supplierIndex);
                SupplierOrder so = supplier.getSupplierOrders().get(orderRowIndex);
                List<SupplierOrderProduct> supplierOrderProducts = so.getSupplierProducts();
                SupplierOrderProductReportAdder sopra = new SupplierOrderProductReportAdder(customerOrderService, supplierReturnProductService);
                List<SupplierOrderProductReport> soprs = sopra.getSupplierOrderProductReports(supplierOrderProducts);
                SupplierOrderReport sor = new SupplierOrderReport(soprs, so);
                OrderProductReportPanel panel = new OrderProductReportPanel(sor);
                JDialog dialogView = new JDialog();
                dialogView.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialogView.setContentPane(panel);
                dialogView.pack();
                dialogView.setVisible(true);
            }

        }
    }//GEN-LAST:event_orderProductSellButtonActionPerformed

    private int currentSupplierOrderProductQuantity(SupplierOrderProduct sop) {
        int soldQuantity = customerOrderService.countSupplierProductSoldQuantity(sop.getSupplierProductId());
        int distributedQuantity = productService.countDistributedProduct(sop.getSupplierProductId());
        return sop.getSupplierProductQuantity() - soldQuantity - distributedQuantity;
    }

    private void updateSellReportView() {

        shopBalances = shopService.getShopBalances(shops);

        int selectedShopIndex = sellReportAllShopCB.getSelectedIndex();
        Date dateFrom = sellDateFrom.getDate();
        Date dateTo = sellDateTo.getDate();

        if (dateFrom == null) {
            dateFrom = new Date();
        }

        if (dateTo == null) {
            dateTo = new Date();
        }

        System.out.println(dateFrom + " " + dateTo);

        if (selectedShopIndex != Enum.invalidIndex) {
            SellReportAdder sellReportAdder = new SellReportAdder();

            if (selectedShopIndex == Enum.firstIndex) {

                if (shopBalances != null) {
                    sellReportAdder.updateSellReportTable(shopBalances, sellReportTable,
                            totalSellLabel, totalPaidLabel, totalDueLabel, totalProfitLabel, dateFrom, dateTo);
                }

            } else {
                selectedShopIndex--;
                if (shopBalances != null) {
                    ShopBalance shopBalance = shopBalances.get(selectedShopIndex);
                    System.out.println(shopBalance);
                    sellReportAdder.updateSellReportTable(shopBalance, sellReportTable,
                            totalSellLabel, totalPaidLabel, totalDueLabel, totalProfitLabel, dateFrom, dateTo);
                }
            }
        }
    }

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
//            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
//            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosScreen.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JTextField OrderIDCodeField;
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JPanel accountCreatePanel;
    private javax.swing.JPanel acpEnclosedPanel;
    private javax.swing.JPanel addNewShopPanel;
    private javax.swing.JButton addNewStaffButton;
    private javax.swing.JButton addStafFinalButton;
    private javax.swing.JButton addUserButtonACP;
    private javax.swing.JLabel addUserStatusLabel;
    private javax.swing.JPanel annualReportPanel;
    private javax.swing.JComboBox authorityComboACP;
    private javax.swing.JButton backButtonACP;
    private javax.swing.JMenuItem backupDateItem;
    private javax.swing.JLabel cashBackViewLabel;
    private javax.swing.JTextField cashField;
    private javax.swing.JLabel categoryExpenseTotalLabel;
    private javax.swing.JTable completeOrderTable;
    private javax.swing.JPanel controllerPanel;
    private javax.swing.JButton createNewShopButton;
    private javax.swing.JButton createShopButton;
    private javax.swing.JTextField customerMobileField;
    private javax.swing.JTextField customerNameField;
    private javax.swing.JPanel customerOrderPanel;
    private javax.swing.JCheckBox defaultSellRateChoose;
    private javax.swing.JMenuItem developerItem;
    private javax.swing.JTextField discountField;
    private javax.swing.JComboBox distProductNameCB;
    private javax.swing.JComboBox distQuantityCB;
    private javax.swing.JTextField distSellRateField;
    private javax.swing.JComboBox distShopCB;
    private javax.swing.JComboBox distSupplierNameCB;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JTextField expAmountField;
    private javax.swing.JComboBox expCategoryCB;
    private com.toedter.calendar.JDateChooser expDate;
    private javax.swing.JTextField expDescField;
    private javax.swing.JButton expenditureDeleteButton;
    private javax.swing.JTable expenseTable;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBox fixedRateCkB;
    private javax.swing.JLabel forgotLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JPanel oldOrderViewPanel;
    private javax.swing.JButton openShopButton;
    private javax.swing.JMenuItem optionItem;
    private javax.swing.JPanel optionPanel;
    private javax.swing.JButton orderCompleteButton;
    private javax.swing.JPanel orderFinalizePanel;
    private javax.swing.JLabel orderIDViewLabel;
    private javax.swing.JPanel orderInputPanel;
    private javax.swing.JTextField orderPProductBarcodeField;
    private javax.swing.JButton orderProductSellButton;
    private javax.swing.JPanel orderViewPanel;
    private javax.swing.JScrollPane orderViewScroll;
    private javax.swing.JTable orderViewTable;
    private javax.swing.JPasswordField passField1ACP;
    private javax.swing.JPasswordField passField2ACP;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel productCodeLabel;
    private javax.swing.JLabel productInfoLabel;
    private javax.swing.JButton productSellButton;
    private javax.swing.JTable productSuppliedTable;
    private javax.swing.JTextField psBuyRateField;
    private javax.swing.JTextField psCodeField;
    private javax.swing.JTextField psNameField;
    private javax.swing.JTextField psQuantityField;
    private javax.swing.JTextField psSellRateField;
    private javax.swing.JButton removeOtItemButton;
    private javax.swing.JButton reportButton;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JTabbedPane reportTabbedPane;
    private javax.swing.JPanel reportToolBarPanel;
    private javax.swing.JPanel reportViewPanel;
    private javax.swing.JButton reset;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JButton saveExpenseButton;
    private javax.swing.JPanel selectionPanel;
    private com.toedter.calendar.JDateChooser sellDateFrom;
    private com.toedter.calendar.JDateChooser sellDateTo;
    private javax.swing.JComboBox sellReportAllShopCB;
    private javax.swing.JTable sellReportTable;
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
    private javax.swing.JButton showProductButton;
    private javax.swing.JPanel signBoardPanel;
    private javax.swing.JLabel softUserMobileLabel;
    private javax.swing.JLabel softUserNameLabel;
    private javax.swing.JLabel softUserRoleLabel;
    private javax.swing.JPanel staffAddEncloed;
    private javax.swing.JPanel staffAddPanel;
    private javax.swing.JPanel stockInputPanel;
    private javax.swing.JScrollPane stockScroll;
    private javax.swing.JTable stockTable;
    private javax.swing.JButton supplierButton;
    private javax.swing.JTextField supplierBuyRateField;
    private javax.swing.JComboBox supplierCB;
    private javax.swing.JPanel supplierDetailsPanel;
    private com.toedter.calendar.JDateChooser supplierOrderDate;
    private javax.swing.JTabbedPane supplierTabPanned;
    private javax.swing.JTable supplierTable;
    private javax.swing.JLabel supplierTotalBillLabel;
    private javax.swing.JLabel supplierTotalDueLabel;
    private javax.swing.JLabel supplierTotalPaidLabel;
    private javax.swing.JButton supplyBarcodeLabel;
    private javax.swing.JPanel supplyOrderEntryPanel;
    private javax.swing.JPanel supplyProductDistPanel;
    private javax.swing.JPanel supplyViewPanel;
    private javax.swing.JMenuItem supportItem;
    private javax.swing.JLabel totalBillViewLabel;
    private javax.swing.JLabel totalDueLabel;
    private javax.swing.JLabel totalPaidLabel;
    private javax.swing.JLabel totalProfitLabel;
    private javax.swing.JLabel totalSellLabel;
    private javax.swing.JComboBox userCB;
    private javax.swing.JLabel userCreationErrorLabel;
    private javax.swing.JTextField userFieldACP;
    private javax.swing.JTabbedPane userTabPanel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField vatField;
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables
}
