/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sportstatsveiretspark;

import static spark.Spark.get;

/**
 *
 * @author Veiret
 */
public class Main {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}

        