/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.Random;
import model.Business.Business;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.MarketingManagement.MarketingPersonDirectory;
import model.MarketingManagement.MarketingPersonProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.EmployeeProfile;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.SalesManagement.SalesPersonProfile;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccount;
import model.UserAccountManagement.UserAccountDirectory;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {

    public static Business initialize() {
        Business business = new Business("Xerox");

        // Initialize directories if needed
        if (business.getSupplierDirectory() == null) {
            System.out.println("Supplier Directory is null!");
            return business;
        }

        // Add sample data
        loadSuppliers(business, 5);  // 5 suppliers
        loadProducts(business, 10);   // 10 products per supplier
        loadCustomers(business, 10);  // 10 customers
        loadOrders(business, 10, 5);  // 10 orders with up to 5 items each

        return business;
    }
    private static final int UPPER_PRICE_LIMIT = 100;
    private static final int LOWER_PRICE_LIMIT = 20;
    private static final int PRICE_RANGE = 10;
    private static final int MAX_QUANTITY = 5;
    
    private static int getRandom(int lower, int upper) {
        Random r = new Random();
        return lower + r.nextInt(upper - lower);
    }

    private static void loadSuppliers(Business b, int count) {
        SupplierDirectory sd = b.getSupplierDirectory();
        for (int i = 1; i <= count; i++) {
            sd.newSupplier("Supplier " + i);
        }
        System.out.println("Added " + count + " suppliers");
    }

    private static void loadProducts(Business b, int productsPerSupplier) {
        SupplierDirectory sd = b.getSupplierDirectory();
        for (Supplier supplier : sd.getSuplierList()) {
            ProductCatalog pc = supplier.getProductCatalog();
            for (int i = 1; i <= productsPerSupplier; i++) {
                String name = "Product " + i + " from " + supplier.getName();
                int floor = getRandom(LOWER_PRICE_LIMIT, LOWER_PRICE_LIMIT + PRICE_RANGE);
                int ceiling = getRandom(UPPER_PRICE_LIMIT - PRICE_RANGE, UPPER_PRICE_LIMIT);
                int target = getRandom(floor, ceiling);
                pc.newProduct(name, floor, ceiling, target);
            }
            System.out.println("Added " + productsPerSupplier + " products to " + supplier.getName());
        }
    }
}
