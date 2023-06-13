package org.example;

import org.testng.annotations.DataProvider;

public class CrudTestDataProvider {

  @DataProvider(name = "generate person")
  public Object[][] generatePerson(){
    return new Object[][] {
        { "Robert", "Plant", 64, "John"},
        { "Paul", "McCartney", 70, "Ringo"}
    };
  }


}
