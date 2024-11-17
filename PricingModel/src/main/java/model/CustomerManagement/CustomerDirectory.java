/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.CustomerManagement;

import java.util.ArrayList;
import java.util.Random;

import model.Business.Business;
import model.Personnel.Person;

/**
 *
 * @author kal bugrara
 */
public class CustomerDirectory {

    Business business;
    ArrayList<CustomerProfile> customerlist;

    public CustomerDirectory(Business d) {

        business = d;
        customerlist = new ArrayList();

    }
    public CustomerDirectory() {
        customerlist = new ArrayList<>();
    }
    
    public CustomerProfile newCustomerProfile(Person p) {

        CustomerProfile cp = new CustomerProfile(p);
        customerlist.add(cp);
        return cp;
    }
    
     public ArrayList<CustomerProfile> getCustomerlist() {
        return customerlist;
    }

    public CustomerProfile findCustomer(String id) {

        for (CustomerProfile sp : customerlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }
        public CustomersReport generatCustomerPerformanceReport(){
        CustomersReport customersreport = new CustomersReport();
    
        for(CustomerProfile cp: customerlist){
            
            CustomerSummary cs = new CustomerSummary(cp);
            customersreport.addCustomerSummary(cs);
        }
        return customersreport; 
    } 
    public CustomerProfile pickRandomCustomer() {
        if (customerlist.isEmpty()) return null;
        int randomIndex = new Random().nextInt(customerlist.size());
        return customerlist.get(randomIndex);
    }
}
