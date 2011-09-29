package dbunit;

import org.junit.runner.RunWith;
//import org.testng.annotations.Test;
import org.junit.Test;
import junit.framework.AssertionFailedError;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.DatabaseUnitils;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;
import org.unitils.dbunit.datasetloadstrategy.impl.InsertLoadStrategy;

import javax.sql.DataSource;

import static org.unitils.reflectionassert.ReflectionComparatorMode.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.sql.*;

/**
 * @author Sergey Ponomarev
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@DataSet
//@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
public class SalaryTest {
    @TestDataSource
    private DataSource dataSource;

    @Test
    @ExpectedDataSet
    public void testName() throws Exception {
        /**
         * It's important!
         * When the first database test in your test suite is run, Unitils will create a DataSource instance that will
         * connect to your unit test database using the settings defined in the properties. Subsequent database tests
         * will then reuse this same datasource instance.
         *
         * Before a test is set up, the DataSource instance will be injected into the test instance: if a field or
         * setter method is annotated with @TestDataSource is found, it will be set to or called with this instance
         * value.
         * You still have to provide some project specific code that configures your code to use this datasource.
         * Typically all this is implemented once in a project-specific superclass for all your database tests.
         *
         * Another way of making your code use the Unitils DataSource is by calling DatabaseUnitils.getDataSource().
         * <code>
         * DataSource ds = DatabaseUnitils.getDataSource();
         * </code>
         */


        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            int updatedRowsCount = stmt.executeUpdate("UPDATE employee SET salary = salary * 10");
            System.out.println(updatedRowsCount);
            conn.commit();
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }
}
