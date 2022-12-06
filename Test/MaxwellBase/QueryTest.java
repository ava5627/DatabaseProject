package MaxwellBase;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class QueryTest {
    @Before
    public void setUp() throws IOException {
        var catalogDir = new File("data");
        if (catalogDir.exists()) {
            for (var file : Objects.requireNonNull(catalogDir.listFiles())) {
                for (var file2 : Objects.requireNonNull(file.listFiles())) {
                    if (!file2.delete()) {
                        System.out.println("Why not?");
                    }
                }
                if (!file.delete()) {
                    System.out.println("Why not?");
                }
            }
            if(!catalogDir.delete()){
                System.out.println("Why not?");
            }
        }
        Initialization.initialize(new File("data"));
    }

    @Test
    public void testInitialize() throws IOException {
        Initialization.initialize(new File("data"));
    }

    @Test
    public void testCreateTable() throws IOException {
        Commands.parseUserCommand("CREATE TABLE test (col1 TEXT,col2 INT,col3 TEXT)");
    }

    @Test
    public void testInsert() throws IOException {
        testCreateTable();
        Commands.parseUserCommand("INSERT INTO test VALUES (\"test1\", 1, \"test2\")");
        Commands.parseUserCommand("INSERT INTO test VALUES (\"test3\", 2, \"test4\")");
        Commands.parseUserCommand("INSERT INTO test VALUES (\"test5\", 1, \"test6\")");
    }

    @Test
    public void testDeleteFromTable() throws IOException {
        testInsert();
        Commands.parseUserCommand("SELECT * FROM test");
        Commands.parseUserCommand("DELETE FROM test WHERE col2 = 1");
        Commands.parseUserCommand("SELECT * FROM test");
        Commands.parseUserCommand("DROP TABLE test");
    }

    @Test
    public void testIndexSearch() throws IOException {
        testInsert();
        Commands.parseUserCommand("CREATE INDEX test_index ON test (col2)");
        Commands.parseUserCommand("SELECT * FROM test WHERE col2 = 1");
    }
}