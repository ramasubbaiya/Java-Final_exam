/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abcindustries.controllers;

import com.abcindustries.entities.Product;
import com.abcindustries.utilities.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@ApplicationScoped
public class ProductsController {

    List<Product> productList;
    //Constructor ProductsController that selects all the products
    public ProductsController() {
        productList = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM Products";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("name"),
                        resultSet.getInt("vendorId")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //List that selects all the products
    public List<Product> getAll() {
        return productList;
    }
    
    // Creates a JSON 
    public JsonArray toJson() {
        // TODO: Build a JsonArray object from the List
        JsonArrayBuilder json = Json.createArrayBuilder();
        productList.stream().forEach((p) -> {
            json.add(p.toJson());
        });
        return json.build();
    }
    
    //Results by product Id
    public Product getById(int id) {
        Product result = null;
        for (int i = 0; i < productList.size() && result == null; i++) {
            if (productList.get(i).getProductId() == id) {
                result = productList.get(i);
            }
        }
        return result;
    }

    //To insert a product
    public void add(Product p) throws SQLException {
        Connection conn = Database.getConnection();
        String query = "INSERT INTO Products (Name, VendorId) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, p.getName());
        pstmt.setInt(2, p.getVendorId());
        int result = pstmt.executeUpdate();

        // Name is Unique and ProductId is auto-incremented, so we can find
        // the actual ProductId based on the Name
        // -- Vendors does not have this issue --
        query = "SELECT ProductId FROM Products WHERE Name = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, p.getName());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        p.setProductId(rs.getInt("ProductId"));

        if (result == 1) {
            productList.add(p);
        }
    }
    
    //To update(edit) a product(existing)
    public void set(int id, Product p) throws SQLException {
        // TODO: Similar to the add() method
        Connection conn = Database.getConnection();
        String query = "UPDATE Products SET name = ?, vendorId = ? WHERE productId =  ";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, p.getName());
        pstmt.setInt(2, p.getVendorId());
        int result = pstmt.executeUpdate();

        // Name is Unique and ProductId is auto-incremented, so we can find
        // the actual ProductId based on the Name
        // -- Vendors does not have this issue --
        query = "SELECT ProductId FROM Products WHERE Name = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, p.getName());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        p.setProductId(rs.getInt("ProductId"));

        if (result == 1) {
            Product original = getById(id);
            original.setName(p.getName());
            original.setVendorId(p.getVendorId());
        }
    }
    
    //To delete a record(row) from product
    public void delete(int id) throws SQLException {
        // TODO: Remember to delete from both the List and the DB
        Connection conn = Database.getConnection();
        String query = "DELETE FROM Products WHERE productId =  ";
        PreparedStatement pstmt = conn.prepareStatement(query);
        int result = pstmt.executeUpdate();

        if (result == 1) {
            Product original = getById(id);
            productList.remove(original);
        }
    }
}
