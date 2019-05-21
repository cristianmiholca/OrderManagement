package presentation;

import businessLayer.AbstractDAO;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    private AbstractDAO dao;
    private View view;

    public Controller(View view){
        this.view = view;

        view.addAddClientBtnListener(new AddClientListener());
        view.addEditClientBtnListener(new EditClientListener());
        view.addDeleteClientBtnListener(new DeleteClientListener());
        view.addViewAllClientsBtnListener(new ViewClientsListener());

        view.addAddProductBtnListener(new AddProductListener());
        view.addEditProductBtnListener(new EditProductListener());
        view.addDeleteProductBtnListener(new DeleteProductListener());
        view.addViewAllProductsBtnListener(new ViewProductsListener());

        view.addAddOrderBtnListener(new AddOrderListener());
    }

    /*Client Action Listeners*/
    class AddClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Client>(Client.class);

            String firstName = view.firstNameTf.getText();
            String lastName = view.lastNameTf.getText();
            int age = Integer.parseInt(view.ageTf.getText());
            String address = view.addressTf.getText();
            String email = view.emailTf.getText();
            String phoneNo = view.phoneNoTf.getText();

            Client client = new Client(firstName, lastName, age, address, email, phoneNo);
            dao.insert(client);
        }
    }

    class EditClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Client>(Client.class);

            String firstName = view.firstNameTf.getText();
            String lastName = view.lastNameTf.getText();
            int age = Integer.parseInt(view.ageTf.getText());
            String address = view.addressTf.getText();
            String email = view.emailTf.getText();
            String phoneNo = view.phoneNoTf.getText();

            /*To be updated*/
            Client client = new Client(firstName, lastName, age, address, email, phoneNo);
            Client c = (Client) dao.findByUniqueField("email", email);
            client.setIdClient(c.getIdClient());

            dao.update(client, "email", email);
        }
    }

    class DeleteClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Client>(Client.class);
            String email = view.emailTf.getText();
            dao.delete("email", email);
        }
    }

    class ViewClientsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Client>(Client.class);
            view.tableFrame.setVisible(true);
            List<Client> clientList = dao.selectAll();
            dao.populateTable(clientList, view.tableContents);
        }
    }

    /*Product Action Listeners*/
    class AddProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Product>(Product.class);

            String productName = view.productNameTf.getText();
            int quantity = Integer.parseInt(view.productQuantityTf.getText());
            float price = Float.parseFloat(view.priceTf.getText());

            Product product = new Product(productName, quantity, price);
            dao.insert(product);
        }
    }

    class EditProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Product>(Product.class);

            String productName = view.productNameTf.getText();
            int quantity = Integer.parseInt(view.productQuantityTf.getText());
            float price = Float.parseFloat(view.priceTf.getText());

            Product product = new Product(productName, quantity, price);
            Product p = (Product) dao.findByUniqueField("name",productName);
            product.setIdProduct(p.getIdProduct());

            dao.update(product, "name", productName);
        }
    }

    class DeleteProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Product>(Product.class);
            String productName = view.productNameTf.getText();
            dao.delete("name", productName);
        }
    }

    class ViewProductsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Product>(Product.class);
            view.tableFrame.setVisible(true);
            List<Product> productList = dao.selectAll();
            dao.populateTable(productList, view.tableContents);
        }
    }

    /*Order Action Listener*/

    class AddOrderListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new AbstractDAO<Orders>(Orders.class);

            int idClient = Integer.parseInt(view.idClientTf.getText());
            int idProduct = Integer.parseInt(view.idProductTf.getText());
            int quantity = Integer.parseInt(view.orderQuantityTf.getText());
            String dateTime = null;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            dateTime = dateFormat.format(date);

            Orders orders = new Orders(idClient, idProduct, quantity, dateTime);
            System.out.println(orders.getDateTime());
            dao.insert(orders);
        }
    }
}
