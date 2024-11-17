/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
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

    public static Business createABusinessAndLoadALotOfData(String name, int supplierCount, int productCount,
            int customerCount, int orderCount, int itemCount) {
        Business business = new Business(name);

        // Add Suppliers +
        loadSuppliers(business, supplierCount);

        // Add Products +
        loadProducts(business, productCount);

        // Add Customers
        loadCustomers(business, customerCount);

        // Add Order
        loadOrders(business, orderCount, itemCount);

        // Pick any 10 Suppliers and add 20 Products to each
        addProducts(business, 10, 20);

        // Pick any 25 Customers and add 1-3 Orders with 1-10 Items to each
        addOrders(business, 25, 3, 10);

        // Pick three random Customers and print out their Sales orders
        getCustomerSalesOrders(business, 3);

        // Find a Supplier with most sales
        getBestSupplier(business);

        // Find a Supplier with least sales (do not include Supplier with zero sales)
        getWorstSupplier(business);

        return business;
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

    static void addProducts(Business b, int supplierCount, int productCount) {
        SupplierDirectory supplierDirectory = b.getSupplierDirectory();
        Set<Supplier> uniqueSuppliers = new HashSet<>(); // hashset: an implementation of set interface

        // use a while loop to obtain 'supplierCount' suppliers, i.e. 10
        while (uniqueSuppliers.size() < supplierCount) {
            Supplier randomSupplier = supplierDirectory.pickRandomSupplier();
            uniqueSuppliers.add(randomSupplier);
        }

        for (Supplier supplier : uniqueSuppliers) {
            // get the product catalog for the current supplier
            ProductCatalog currentSupplierProductCatalog = supplier.getProductCatalog();

            // before added
            System.out.println("Previous number of products for supplier " + supplier.getName() + " is: "
                    + currentSupplierProductCatalog.getProductList().size());

            // add 'productCount' number of products, i.e. 20 to this supplier
            for (int i = 1; i <= productCount; i++) {
                String productName = "New Product #" + i + " from " + supplier.getName();
                int randomFloor = getRandom(LOWER_PRICE_LIMIT, LOWER_PRICE_LIMIT + PRICE_RANGE);
                int randomCeiling = getRandom(UPPER_PRICE_LIMIT - PRICE_RANGE, UPPER_PRICE_LIMIT);
                int randomTarget = getRandom(randomFloor, randomCeiling);
                currentSupplierProductCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
            }

            System.out.println("Current number of products for supplier " + supplier.getName() + " is: "
                    + currentSupplierProductCatalog.getProductList().size());
        }
    }

    private static void loadCustomers(Business b, int count) {
        CustomerDirectory cd = b.getCustomerDirectory();
        PersonDirectory pd = b.getPersonDirectory();

        for (int i = 1; i <= count; i++) {
            Person person = pd.newPerson("" + i);  // Create person with ID
            CustomerProfile customerProfile = new CustomerProfile(person);
            cd.getCustomerlist().add(customerProfile);
            System.out.println("Added customer: " + person.getPersonId());
        }
        System.out.println("Added " + count + " customers");
    }

    static void addOrders(Business b, int customerCount, int maxOrderCount, int maxItemCount) {
        // choose a number of customers
        CustomerDirectory customerDirectory = b.getCustomerDirectory();
        SupplierDirectory sd = b.getSupplierDirectory();
        Set<CustomerProfile> uniqueCustomerProfiles = new HashSet<>();

        while (uniqueCustomerProfiles.size() < customerCount) {
            CustomerProfile customerProfile = customerDirectory.pickRandomCustomer();
            uniqueCustomerProfiles.add(customerProfile);
        }

        // add orders (with maximum number) for each selected customers
        MasterOrderList orderList = b.getMasterOrderList();

        for (CustomerProfile customerProfile : uniqueCustomerProfiles) {
            // choose order count from 1-3
            int randomNumberOfOrders = getRandom(1, maxOrderCount + 1);
            // add orders to this customer
            for (int i = 0; i < randomNumberOfOrders; i++) {
                Order newOrder = orderList.newOrder(customerProfile);
                // choose item count from 1-10
                int randomNumberOfItems = getRandom(1, maxItemCount + 1);
                for (int j = 0; j < randomNumberOfItems; j++) {
                    // initialize an order item
                    Supplier randomSupplier = sd.pickRandomSupplier();
                    if (randomSupplier == null) {
                        System.out.println("Cannot generate orders. No supplier in the supplier directory.");
                        return;
                    }
                    ProductCatalog pc = randomSupplier.getProductCatalog();
                    Product randomProduct = pc.pickRandomProduct();
                    if (randomProduct == null) {
                        System.out.println("Cannot generate orders. No products in the product catalog.");
                        return;
                    }
                    int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
                    int randomQuantity = getRandom(1, MAX_QUANTITY);

                    OrderItem oi = newOrder.newOrderItem(randomProduct, randomPrice, randomQuantity);
                }
                System.out.println("Add one order to masterOrderList for customer "
                        + customerProfile.getCustomerId() + " with " + randomNumberOfItems + " items.");
            }
        }

    }

    private static void loadOrders(Business b, int ordersPerSupplier, int maxItemsPerOrder) {
        MasterOrderList mol = b.getMasterOrderList();
        CustomerDirectory cd = b.getCustomerDirectory();
        Random r = new Random();

        if (cd.getCustomerlist().isEmpty()) {
            System.out.println("No customers available to create orders!");
            return;
        }

        for (Supplier supplier : b.getSupplierDirectory().getSuplierList()) {
            for (int i = 0; i < ordersPerSupplier; i++) {
                CustomerProfile customer = cd.pickRandomCustomer();
                if (customer == null) {
                    System.out.println("Warning: No customer selected for order");
                    continue;
                }

                Order order = mol.newOrder(customer);

                // Add random number of items to order
                int itemCount = 1 + r.nextInt(maxItemsPerOrder);
                ProductCatalog pc = supplier.getProductCatalog();

                for (int j = 0; j < itemCount; j++) {
                    Product product = pc.pickRandomProduct();
                    if (product == null) {
                        continue;
                    }

                    int price = getRandom(product.getFloorPrice(), product.getCeilingPrice());
                    int quantity = getRandom(1, MAX_QUANTITY);
                    order.newOrderItem(product, price, quantity);
                }
            }
        }
        System.out.println("Added orders with random items");

    }

    static void getCustomerSalesOrders(Business b, int customerCount) {
        // choose a number of customers
        CustomerDirectory customerDirectory = b.getCustomerDirectory();
        Set<CustomerProfile> uniqueCustomerProfiles = new HashSet<>();

        while (uniqueCustomerProfiles.size() < customerCount) {
            CustomerProfile customerProfile = customerDirectory.pickRandomCustomer();
            uniqueCustomerProfiles.add(customerProfile);
        }

//        for (CustomerProfile customerProfile : uniqueCustomerProfiles) {
//            ArrayList<Order> orders = customerProfile.getOrders();
//            for (Order order : orders) {
//                order.printInfo();
//            }
//        }
    }

    static void getBestSupplier(Business b) {
        SupplierDirectory supplierDirectory = b.getSupplierDirectory();
        ArrayList<Supplier> suppliers = supplierDirectory.getSuplierList();

        int maximumSales = Integer.MIN_VALUE;
        Supplier bestSupplier = new Supplier("default");

        for (Supplier supplier : suppliers) {
            ProductsReport productsReport = supplier.prepareProductsReport();
            ArrayList<ProductSummary> productSummaries = productsReport.getProductSummaries();

            int salesSum = 0;

            for (ProductSummary productSummary : productSummaries) {
                salesSum += productSummary.getSalesRevenues();
            }

            if (salesSum > maximumSales) {
                maximumSales = salesSum;
                bestSupplier = supplier;
            }
        }

        System.out.println("The supplier with most sales is " + bestSupplier.getName() + " with total sales volume "
                + maximumSales);
    }

    static void getWorstSupplier(Business b) {
        SupplierDirectory supplierDirectory = b.getSupplierDirectory();
        ArrayList<Supplier> suppliers = supplierDirectory.getSuplierList();

        int minimumSales = Integer.MAX_VALUE;
        Supplier worstSupplier = new Supplier("default");

        for (Supplier supplier : suppliers) {
            ProductsReport productsReport = supplier.prepareProductsReport();
            ArrayList<ProductSummary> productSummaries = productsReport.getProductSummaries();

            int salesSum = 0;

            for (ProductSummary productSummary : productSummaries) {
                salesSum += productSummary.getSalesRevenues();
            }

            if (salesSum != 0 && salesSum < minimumSales) {
                minimumSales = salesSum;
                worstSupplier = supplier;
            }
        }

        System.out.println("The supplier with least sales is " + worstSupplier.getName() + " with total sales volume "
                + minimumSales);
    }

    private static void generateSuppliersAndProducts(Business business, int supplierCount, int productsPerSupplier) {
        SupplierDirectory supplierDirectory = business.getSupplierDirectory();
        Random random = new Random();

        // Create suppliers
        for (int i = 1; i <= supplierCount; i++) {
            Supplier supplier = supplierDirectory.newSupplier("Supplier " + i);
            ProductCatalog productCatalog = supplier.getProductCatalog();

            // Create products for each supplier
            for (int j = 1; j <= productsPerSupplier; j++) {
                // Generate random prices within reasonable ranges
                int floorPrice = LOWER_PRICE_LIMIT + random.nextInt(PRICE_RANGE);
                int ceilingPrice = UPPER_PRICE_LIMIT - random.nextInt(PRICE_RANGE);
                int targetPrice = floorPrice + random.nextInt(ceilingPrice - floorPrice);

                String productName = "Product " + j + " (Supplier " + i + ")";
                productCatalog.newProduct(productName, floorPrice, ceilingPrice, targetPrice);
            }
        }
    }

    private static void generateCustomers(Business business, int customerCount) {
        CustomerDirectory customerDirectory = business.getCustomerDirectory();

        for (int i = 1; i <= customerCount; i++) {
            CustomerProfile customer = customerDirectory.newCustomerProfile("Customer " + i);
        }
    }

    private static void generateOrders(Business business, int ordersPerProduct) {
        Random random = new Random();
        SupplierDirectory supplierDirectory = business.getSupplierDirectory();
        CustomerDirectory customerDirectory = business.getCustomerDirectory();

        // For each supplier
        for (Supplier supplier : supplierDirectory.getSuplierList()) {
            ProductCatalog catalog = supplier.getProductCatalog();

            // For each product
            for (Product product : catalog.getProductList()) {
                // Generate orders
                for (int i = 0; i < ordersPerProduct; i++) {
                    // Randomly select a customer
                    CustomerProfile customer = customerDirectory.pickRandomCustomer();
                    if (customer == null) {
                        continue;
                    }

                    // Create new order
                    Order order = business.getMasterOrderList().newOrder(customer);

                    // Generate random actual price between floor and ceiling
                    int actualPrice = product.getFloorPrice()
                            + random.nextInt(product.getCeilingPrice() - product.getFloorPrice());

                    // Generate random quantity between 1 and 5
                    int quantity = 1 + random.nextInt(5);

                    // Add order item
                    OrderItem orderItem = order.newOrderItem(product, actualPrice, quantity);
                }
            }
        }
    }
}
