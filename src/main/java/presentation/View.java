package presentation;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.color.CMMException;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {

    JTabbedPane tabbedPane = new JTabbedPane();

    /*Panels*/
    JPanel clientMenuPanel = new JPanel();
    JPanel productMenuPanel = new JPanel();
    JPanel orderMenuPanel = new JPanel();

    /*Components for clientMenuPanel*/
    JButton addClientBtn = new JButton("Add client");
    JButton editClientBtn = new JButton("Edit client");
    JButton deleteClientBtn = new JButton("Delete client");
    JButton viewAllClientsBtn = new JButton("View all clients");

    JTextField firstNameTf = new JTextField(15);
    JTextField lastNameTf = new JTextField(15);
    JTextField ageTf = new JTextField(15);
    JTextField addressTf = new JTextField(15);
    JTextField emailTf = new JTextField(15);
    JTextField phoneNoTf = new JTextField(15);

    JLabel firstNameLbl = new JLabel("First name");
    JLabel lastNameLbl = new JLabel("Last name");
    JLabel ageLbl = new JLabel("Age");
    JLabel addressLbl = new JLabel("Address");
    JLabel emailLbl = new JLabel("Email");
    JLabel phoneNoLbl = new JLabel("Phone");

    /*Components for product panel*/
    JButton addProductBtn = new JButton("Add product");
    JButton editProductBtn = new JButton("Edit product");
    JButton deleteProductBtn = new JButton("Delete product");
    JButton viewAllProductsBtn = new JButton("View all products");

    JTextField productNameTf = new JTextField(15);
    JTextField productQuantityTf = new JTextField(15);
    JTextField priceTf = new JTextField(15);

    JLabel productNameLbl = new JLabel("Name");
    JLabel productQuantityLbl = new JLabel("Quantity");
    JLabel priceLbl = new JLabel("Price");

    /*Components for order panel*/
    JButton addOrderBtn = new JButton("Add order");

    JTextField idClientTf = new JTextField(15);
    JTextField idProductTf = new JTextField(15);
    JTextField orderQuantityTf = new JTextField(15);

    JLabel idClientLbl = new JLabel("Id Client");
    JLabel idProductLbl = new JLabel("Id Product");
    JLabel orderQuantityLbl = new JLabel("Quantity");

    /*Frame for table*/
    JFrame tableFrame = new JFrame();
    JTable tableContents = new JTable();
    JScrollPane scrollPane = new JScrollPane();

    public View(){
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(100,100,600,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToClientMenuPanel();
        addComponentsToProductMenuPanel();
        addComponentsToOrderMenuPanel();

        tabbedPane.setBounds(0,0,590,500);
        tabbedPane.add("Client", clientMenuPanel);
        tabbedPane.add("Product",productMenuPanel);
        tabbedPane.add("Order",orderMenuPanel);

        tableFrame.setBounds(100,100,600,300);
        tableFrame.setVisible(false);
        addComponentsToTableFrame();

        this.add(tabbedPane);
    }

    private void addComponentsToClientMenuPanel(){
        clientMenuPanel.setLayout(new GridLayout(2,1));

        /*Data input panel*/
        JPanel inputPanel = new JPanel();
        GroupLayout gl = new GroupLayout(inputPanel);
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        inputPanel.setLayout(gl);
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(firstNameLbl)
                        .addComponent(lastNameLbl).addComponent(ageLbl).addComponent(addressLbl)
                        .addComponent(emailLbl).addComponent(phoneNoLbl))
                .addGroup(gl.createParallelGroup().addComponent(firstNameTf).addComponent(lastNameTf)
                        .addComponent(ageTf).addComponent(addressTf).addComponent(emailTf).addComponent(phoneNoTf)));

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(firstNameLbl)
                        .addComponent(firstNameTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lastNameLbl)
                        .addComponent(lastNameTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(ageLbl)
                        .addComponent(ageTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(addressLbl)
                        .addComponent(addressTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(emailLbl)
                        .addComponent(emailTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(phoneNoLbl)
                        .addComponent(phoneNoTf)));

        /*Buttons Panel*/
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(addClientBtn);
        buttonsPanel.add(editClientBtn);
        buttonsPanel.add(deleteClientBtn);
        buttonsPanel.add(viewAllClientsBtn);

        clientMenuPanel.add(inputPanel);
        clientMenuPanel.add(buttonsPanel);
    }

    private void addComponentsToProductMenuPanel(){
        productMenuPanel.setLayout(new GridLayout(2,1));

        /*Data input panel*/
        JPanel inputPanel = new JPanel();
        GroupLayout gl = new GroupLayout(inputPanel);
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        inputPanel.setLayout(gl);
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(productNameLbl)
                        .addComponent(productQuantityLbl).addComponent(priceLbl))
                .addGroup(gl.createParallelGroup().addComponent(productNameTf).addComponent(productQuantityTf)
                        .addComponent(priceTf)));

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(productNameLbl)
                        .addComponent(productNameTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(productQuantityLbl)
                        .addComponent(productQuantityTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(priceLbl)
                        .addComponent(priceTf)));

        /*Buttons Panel*/
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(addProductBtn);
        buttonsPanel.add(editProductBtn);
        buttonsPanel.add(deleteProductBtn);
        buttonsPanel.add(viewAllProductsBtn);

        productMenuPanel.add(inputPanel);
        productMenuPanel.add(buttonsPanel);
    }

    private void addComponentsToOrderMenuPanel(){
        orderMenuPanel.setLayout(new GridLayout(2,1));

        /*Data input panel*/
        JPanel inputPanel = new JPanel();
        GroupLayout gl = new GroupLayout(inputPanel);
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        inputPanel.setLayout(gl);
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(idClientLbl)
                        .addComponent(idProductLbl).addComponent(orderQuantityLbl))
                .addGroup(gl.createParallelGroup().addComponent(idClientTf)
                        .addComponent(idProductTf).addComponent(orderQuantityTf)));

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(idClientLbl)
                        .addComponent(idClientTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(idProductLbl)
                        .addComponent(idProductTf))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(orderQuantityLbl)
                        .addComponent(orderQuantityTf)));

        /*Buttons Panel*/
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(addOrderBtn);

        orderMenuPanel.add(inputPanel);
        orderMenuPanel.add(buttonsPanel);

    }

    private void addComponentsToTableFrame(){
        tableContents.setBounds(100,100,600,300);
        tableFrame.add(new JScrollPane(tableContents));
    }

    /*Client Listeners*/
    public void addAddClientBtnListener(ActionListener addAL){
        addClientBtn.addActionListener(addAL);
    }

    public void addEditClientBtnListener(ActionListener editAL){
        editClientBtn.addActionListener(editAL);
    }

    public void addDeleteClientBtnListener(ActionListener deleteAL){
        deleteClientBtn.addActionListener(deleteAL);
    }

    public void addViewAllClientsBtnListener(ActionListener viewClientsAL){
        viewAllClientsBtn.addActionListener(viewClientsAL);
    }

    /*Product Listeners*/
    public void addAddProductBtnListener(ActionListener addAL){
        addProductBtn.addActionListener(addAL);
    }

    public void addEditProductBtnListener(ActionListener editAL){
        editProductBtn.addActionListener(editAL);
    }

    public void addDeleteProductBtnListener(ActionListener deleteAL){
        deleteProductBtn.addActionListener(deleteAL);
    }

    public void addViewAllProductsBtnListener(ActionListener viewProductsAL){
        viewAllProductsBtn.addActionListener(viewProductsAL);
    }

    /*Order Listener*/
    public void addAddOrderBtnListener(ActionListener addAL){
        addOrderBtn.addActionListener(addAL);
    }

}
